package com.microwise.smartservices.moroesst;

import com.microwise.smartservices.netconn.Messenger;
import com.microwise.smartservices.netconn.form.MessageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by lee on 7/26/2017.
 */
@Component("reliableSender")
public class ReliableSender {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource(name = "udpMessenger")
    private Messenger messenger;
    @Resource(name = "statusCenter")
    private StatusCenter statusCenter;

    public void sendMessage(MessageBean mb) {
        String messagekey = messenger.calcTokenByMessage(mb);
        statusCenter.setUnfinishedTask(messagekey);
        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                if (statusCenter.checkUnfinishedTask(messagekey)) {
                    logger.debug("send message --> " + mb);
                    messenger.sendMessage(mb);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                } else {
                    break;
                }
            }
        }).start();
    }
}
