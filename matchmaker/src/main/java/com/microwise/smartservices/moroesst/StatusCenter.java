package com.microwise.smartservices.moroesst;

import org.springframework.stereotype.Component;

/**
 * Created by lee on 7/25/2017.
 */
@Component("statusCenter")
public class StatusCenter {
    int expire = 600000;

    public boolean commandsIsLoak(String commandsKey){
        return false;
    }
}
