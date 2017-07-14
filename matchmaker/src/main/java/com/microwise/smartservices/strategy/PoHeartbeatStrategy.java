package com.microwise.smartservices.strategy;

import com.microwise.smartservices.netconn.form.MessageBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by lee on 5/23/2017.
 */
@Component("po_heartbeat")
public class PoHeartbeatStrategy implements IStrategy {
    @Resource(name="heartbeat")
    private IStrategy heartbeatStrategy;

    @Override
    public MessageBean processMessage(MessageBean old) {
        return heartbeatStrategy.processMessage(old);
    }
}
