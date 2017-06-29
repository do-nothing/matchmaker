package com.microwise.smartservices.pomanager;

import com.microwise.smartservices.netconn.Messenger;
import com.microwise.smartservices.netconn.form.MessageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
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
                        if ("setStatus".equals(mb.getContentBean().getCommand())) {
                            PoController poController = poMap.get(mb.getTarget());
                            if (poController != null) {
                                Object[] args = mb.getContentBean().getArgs();
                                if(args.length == 1){
                                    poController.setDeviceStatus(args[0].toString());
                                }else if(args.length == 2){
                                    poController.setDeviceStatus(args[0].toString(), args[1].toString());
                                }
                            }else{
                                logger.warn(mb.getTarget() + "is not online!");
                            }
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
                        new PoController(poMap, socket);
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
        MessageBean mb = new MessageBean();
        mb.setTarget("server");
        mb.setLogType("nolog");
        mb.setQuality(0);
        mb.setStrategy("po_heartbeat");
        mb.getContentBean().setCommand("recordStatus");

        new Thread() {
            public void run() {
                while (true) {
                    try {
                        mb.setTimestamp(System.currentTimeMillis());
                        if (poMap.isEmpty()) {
                            mb.setId(id);
                            mb.getContentBean().setArgs(null);
                            messenger.sendMessage(mb);
                        } else {
                            for (Map.Entry<String, PoController> entry : poMap.entrySet()) {
                                mb.setId(entry.getKey());
                                mb.getContentBean().setArgs(new String[]{entry.getValue().getDeviceStatus()});
                                messenger.sendMessage(mb);
                            }
                        }
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        logger.warn("Heartbeat sending failed!");
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
