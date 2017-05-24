package com.microwise.smartservices.strategy;

import com.microwise.smartservices.netconn.form.MessageBean;
import org.springframework.stereotype.Component;

/**
 * Created by lee on 5/23/2017.
 */
@Component("relay")
public class RelayStrategy implements IStrategy {
    @Override
    public MessageBean processMessage(MessageBean old) {
        return old;
    }
}
