package com.microwise.smartservices.moroesst.PowerLogic;

import com.microwise.smartservices.moroesst.MoroesSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by lee on 7/26/2017.
 */
@Component("powerSwitchCommand")
public class PowerSwitchCommand {
    @Resource(name = "moroesSender")
    private MoroesSender moroesSender;
    public void switchPowerOn(String pid, int port){
        moroesSender.sendSwitchCommand(pid, port, 1);
    }
    public void switchPowerOff(String pid, int port){
        moroesSender.sendSwitchCommand(pid, port, 0);
    }
}
