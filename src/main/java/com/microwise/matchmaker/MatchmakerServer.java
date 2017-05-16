package com.microwise.matchmaker;

import com.microwise.matchmaker.netconn.MessageBean;
import com.microwise.matchmaker.netconn.Messenger;
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
    private boolean isStart = false;

    public void startServer() {
        if (isStart)
            return;

        isStart = true;
        new Thread() {
            public void run() {
                while (isStart) {
                    MessageBean mb = messenger.getMessage();
                    processMessage(mb);
                }
            }
        }.start();
    }

    private void processMessage(MessageBean mb){
        logger.info("" + mb);
        messenger.sendMessage(mb);
    }
}
