package com.microwise.smartservices.strategy;

import com.microwise.smartservices.netconn.form.MessageBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by lee on 5/24/2017.
 */
@Component("shutdown")
public class ShutdownCommand implements IStrategy {
    @Override
    public MessageBean processMessage(MessageBean old) {
        String info = null;
        if (old.getContentBean().getArgs() != null && old.getContentBean().getArgs().length > 0)
            info = "" + old.getContentBean().getArgs()[0];
        try {
            Runtime.getRuntime().exec("cmd /c shutdown /s /c " + info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
