package com.microwise.smartservices;

import com.microwise.smartservices.netconn.form.MessageBean;
import com.microwise.smartservices.netconn.Messenger;
import com.microwise.smartservices.strategy.MessageProcesser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.UnknownHostException;

/**
 * Created by lee on 5/16/2017.
 */

@PropertySource("application.properties")
@Component("exmanagerServer")
public class ExmanagerServer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name="udpMessenger")
    private Messenger messenger ;
    @Resource(name = "messageProcesser")
    private MessageProcesser messageProcesser;
    private boolean isStart = false;
    @Value("${ip}")
    private String ip;
    @Value("${port}")
    private int port;
    @Value("${id}")
    private String id;

    public void startServer() {
        if (isStart)
            return;

        isStart = true;
        startReceiver();
        startHeartbeat();
    }

    private void startReceiver() {
        new Thread() {
            public void run() {
                while (isStart) {
                    try {
                        MessageBean oldMb = messenger.getMessage();
                        logger.debug("" + oldMb);
                        MessageBean newMb = messageProcesser.precessMassage(oldMb);
                        messenger.sendMessage(newMb);
                    } catch (Exception e) {
                        logger.warn("Message processing failed!");
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void startHeartbeat(){
        logger.info(id + " start heartbeat. -->" + ip + ":" + port);
        try {
            messenger.setServerAddress(ip, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }
        MessageBean mb = new MessageBean();
        mb.setId(id);
        mb.setTarget("server");
        mb.setLogType("nolog");
        mb.setQuality(0);
        mb.setStrategy("heartbeat");
        new Thread() {
            public void run() {
                while (isStart) {
                    try {
                        mb.setTimestamp(System.currentTimeMillis());
                        messenger.sendMessage(mb);
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
