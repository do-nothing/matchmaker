package com.microwise.matchmaker.netconn;

import com.microwise.matchmaker.form.MessageBean;

/**
 * Created by John on 2017/5/14.
 */
public interface Messenger {
    void sendMessage(MessageBean messageBean);
    MessageBean getMessage();
}
