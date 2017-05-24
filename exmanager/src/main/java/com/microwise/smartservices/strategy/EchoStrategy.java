package com.microwise.smartservices.strategy;

import com.microwise.smartservices.netconn.form.MessageBean;
import org.springframework.stereotype.Component;

/**
 * Created by lee on 5/22/2017.
 */
@Component("echoStrategy")
public class EchoStrategy implements IStrategy {
    @Override
    public MessageBean processMessage(MessageBean old) {
        String id = old.getId();
        old.setId(old.getTarget());
        old.setTarget(id);
        return old;
    }
}
