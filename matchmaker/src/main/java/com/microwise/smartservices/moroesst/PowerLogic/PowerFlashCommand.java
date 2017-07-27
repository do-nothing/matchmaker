package com.microwise.smartservices.moroesst.PowerLogic;

import com.microwise.smartservices.moroesst.MoroesSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by lee on 7/26/2017.
 */
@Component("powerFlashCommand")
public class PowerFlashCommand {
    @Resource(name = "moroesSender")
    private MoroesSender moroesSender;
    public void flashPowerOn(String pid, int port){
        moroesSender.sendFlashCommand(pid, port, 1);
    }
    public void flashPowerOff(String pid, int port){
        moroesSender.sendFlashCommand(pid, port, 0);
    }
}
