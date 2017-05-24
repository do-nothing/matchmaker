package com.microwise.smartservices.strategy;

import com.microwise.smartservices.netconn.form.MessageBean;

/**
 * Created by lee on 5/22/2017.
 */
public interface IStrategy {
    MessageBean processMessage(MessageBean old);
}
