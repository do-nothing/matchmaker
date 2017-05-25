package com.microwise.smartservices.strategy;

import com.microwise.smartservices.netconn.form.MessageBean;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lee on 5/24/2017.
 */
@Component("restart")
public class RestartCommand implements IStrategy {
    @Override
    public MessageBean processMessage(MessageBean old) {
        if (old.getContentBean().getArgs() == null)
            return null;

        String cmd = "";
        for(Object o : old.getContentBean().getArgs()){
            cmd += " " + o;
        }

        try {
            Runtime.getRuntime().exec("cmd /c start restart" + cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
