package com.microwise.smartservices.strategy;

import com.microwise.smartservices.netconn.form.MessageBean;
import org.springframework.stereotype.Component;

/**
 * Created by lee on 5/24/2017.
 */
@Component("install")
public class InstallCommand implements IStrategy {
    @Override
    public MessageBean processMessage(MessageBean old) {
        if (old.getContentBean().getArgs() == null)
            return null;

        String cmd = "";
        for(Object o : old.getContentBean().getArgs()){
            cmd += " " + o;
        }

        try {
            Runtime.getRuntime().exec("cmd /c start install" + cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
