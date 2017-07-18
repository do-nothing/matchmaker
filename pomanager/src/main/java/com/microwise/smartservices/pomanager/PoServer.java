package com.microwise.smartservices.pomanager;

import com.microwise.smartservices.netconn.Messenger;
import com.microwise.smartservices.netconn.form.MessageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lee on 6/23/2017.
 */
@Component
public class PoServer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Map<String, PoController> poMap = new HashMap<String, PoController>();
    MessageBean mb = new MessageBean();

    @Value("${tcpServer_port}")
    private int tcpServer_port;
    @Value("${ip}")
    private String ip;
    @Value("${port}")
    private int port;
    @Value("${id}")
    private String id;
    @Resource(name = "udpMessenger")
    private Messenger messenger;

    public void startServers() {
        startReceiver();
        startHeartbeat();
        startTcpServer();
    }

    private void startReceiver() {
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        MessageBean mb = messenger.getMessage();
                        //logger.debug(mb.toString());
                        PoController poController = poMap.get(mb.getTarget());
                        if (poController != null) {
                            if ("setStatus".equals(mb.getContentBean().getCommand())) {
                                Object[] args = mb.getContentBean().getArgs();
                                if (args.length == 1) {
                                    poController.setDeviceStatus(args[0].toString());
                                } else if (args.length == 2) {
                                    poController.setDeviceStatus(args[0].toString(), args[1].toString());
                                }
                            } else if ("setFlashTimes".equals(mb.getContentBean().getCommand())) {
                                Object[] args = mb.getContentBean().getArgs();
                                if (args.length == 2) {
                                    poController.setFlashTimes(args[0].toString(), args[1].toString());
                                }
                            } else if ("flash".equals(mb.getContentBean().getCommand())) {
                                Object[] args = mb.getContentBean().getArgs();
                                if (args.length == 2) {
                                    poController.flashOneTime(args[0].toString(), args[1].toString());
                                }
                            }
                        } else {
                            logger.warn(mb.getTarget() + "is not online!");
                        }
                    } catch (Exception e) {
                        logger.warn("Message processing failed!");
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void startTcpServer() {
        new Thread() {
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(tcpServer_port);
                    logger.info(id + " start tcp ServerSocket at 0.0.0.0:" + tcpServer_port);
                    while (true) {
                        Socket socket = serverSocket.accept();
                        PoController p = new PoController(poMap, socket);
                        p.setHeatbeatSender((String id, String status) -> sendHeartbeat(id, status));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void startHeartbeat() {
        try {
            messenger.setServerAddress(ip, port);
            logger.info(id + " start heartbeat to " + ip + ":" + port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }
        mb.setTarget("server");
        mb.setLogType("nolog");
        mb.setQuality(0);
        mb.setStrategy("po_heartbeat");
        mb.getContentBean().setCommand("recordStatus");

        new Thread(() ->
        {
            while (true) {
                try {
                    if (poMap.isEmpty()) {
                        sendHeartbeat(id, null);
                    } else {
                        for (Map.Entry<String, PoController> entry : poMap.entrySet()) {
                            sendHeartbeat(entry.getKey(), entry.getValue().getDeviceStatus());
                        }
                    }
                    Thread.sleep(5000);
                } catch (Exception e) {
                    logger.warn("Heartbeat sending failed!");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendHeartbeat(String id, String status) {
        if (id == null || id.isEmpty()) {
            return;
        }
        String[] args;
        if (status == null || status.isEmpty()) {
            args = null;
        } else {
            args = new String[]{status};
        }
        mb.setTimestamp(System.currentTimeMillis());
        mb.setId(id);
        mb.getContentBean().setArgs(args);
        messenger.sendMessage(mb);
    }
}
