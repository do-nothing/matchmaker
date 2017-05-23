package com.microwise.matchmaker.strategy;

import com.microwise.matchmaker.form.MessageBean;

/**
 * Created by lee on 5/23/2017.
 */
public class RelayStrategy implements IStrategy {
    @Override
    public MessageBean processMessage(MessageBean old) {
        return old;
    }
}
