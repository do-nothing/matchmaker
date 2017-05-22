package com.microwise.matchmaker;

import com.microwise.matchmaker.form.MessageBean;
import com.microwise.matchmaker.netconn.Messenger;
import com.microwise.matchmaker.strategy.MessageProcesser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by lee on 5/16/2017.
 */

@Component("matchmakerServer")
public class MatchmakerServer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name="udpMessenger")
    private Messenger messenger ;
    @Resource(name = "messageProcesser")
    private MessageProcesser messageProcesser;
    private boolean isStart = false;

    public void startServer() {
        if (isStart)
            return;

        isStart = true;
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
}
