package com.microwise.smartservices.strategy;

import com.microwise.smartservices.netconn.form.MessageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by lee on 5/23/2017.
 */
@Component("relay")
public class RelayStrategy implements IStrategy {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public MessageBean processMessage(MessageBean old) {
        logger.debug("" + old);
        return old;
    }
}
