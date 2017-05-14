package com.microwise.matchmaker.netconn;

/**
 * Created by John on 2017/5/14.
 */
public interface Messenger {
    void sendMessage(Object o);
    Object getMessage();
}
