package com.microwise.smartservices.netconn;

import com.microwise.smartservices.netconn.form.MessageBean;

/**
 * Created by John on 2017/5/14.
 */
public interface Messenger {
    void sendMessage(MessageBean messageBean);
    MessageBean getMessage();
}
