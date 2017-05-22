package com.microwise.matchmaker.strategy;

import com.microwise.matchmaker.form.MessageBean;
import org.springframework.stereotype.Component;

/**
 * Created by lee on 5/22/2017.
 */
@Component("echoStrategy")
public class EchoStrategy implements IStrategy {
    @Override
    public MessageBean processMessage(MessageBean old) {
        return null;
    }
}
