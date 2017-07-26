package com.microwise.smartservices.strategy;

import com.microwise.smartservices.netconn.form.MessageBean;
import org.springframework.stereotype.Component;

/**
 * Created by lee on 5/24/2017.
 */
@Component("showBulletin")
public class ShowBulletinCommand implements IStrategy {
    @Override
    public MessageBean processMessage(MessageBean old) {
        old.setTarget("appSelf");
        old.setQuality(0);
        return old;
    }
}
