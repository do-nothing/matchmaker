package com.microwise.matchmaker.strategy;

import com.microwise.matchmaker.form.MessageBean;

/**
 * Created by lee on 5/22/2017.
 */
public interface IStrategy {
    MessageBean processMessage(MessageBean old);
}
