package com.microwise.smartservices.netconn;

import com.microwise.smartservices.netconn.form.MessageBean;

import java.net.UnknownHostException;

/**
 * Created by John on 2017/5/14.
 */
public interface Messenger {
    void sendMessage(MessageBean messageBean);
    MessageBean getMessage();
    void setServerAddress(String ip, int port) throws UnknownHostException;
    String calcTokenByMessage(MessageBean messageBean);
}
