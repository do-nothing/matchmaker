package com.microwise.smartservices.moroesst.PowerLogic;

import com.microwise.smartservices.moroesst.MoroesSender;
import com.microwise.smartservices.moroesst.StatusCenter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by lee on 7/26/2017.
 */
@Component("powerSwitchCommand")
public class PowerSwitchCommand {
    @Resource(name = "moroesSender")
    private MoroesSender moroesSender;
    @Resource(name = "statusCenter")
    private StatusCenter statusCenter;

    public void switchPowerOn(String pid, int port) {
        switchPower(pid, port, 1);
    }

    public void switchPowerOff(String pid, int port) {
        switchPower(pid, port, 0);
    }

    private void switchPower(String pid, int port, int flag) {
        String key = pid + "_" + port;
        boolean intents = flag == 1 ? true : false;
        System.out.println("switchPower : " + key + " " + intents);
        for (int i = 0; i < 60; i++) {
            if (statusCenter.getPortStatus(key) == intents)
                return;
            moroesSender.sendSwitchCommand(pid, port, flag);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
