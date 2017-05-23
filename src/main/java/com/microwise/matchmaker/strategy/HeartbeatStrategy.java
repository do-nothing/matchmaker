package com.microwise.matchmaker.strategy;

import com.microwise.matchmaker.form.MessageBean;
import org.springframework.stereotype.Component;

/**
 * Created by lee on 5/23/2017.
 */
@Component("heartbeatStrategy")
public class HeartbeatStrategy implements IStrategy {
    @Override
    public MessageBean processMessage(MessageBean old) {
        return null;
    }
}
