package com.microwise.smartservices.moroesst.PowerLogic;

import org.junit.Test;

import java.util.List;

/**
 * Created by lee on 7/26/2017.
 */
public class CommandConverterTest {
    private CommandConverter commandConverter = new CommandConverter();
    String str = "[{\"command\":\"power_on\",\"power_id\":\"JY05mmBEm0G73G8d\",\"port\":1},{\"command\":\"sleep\",\"second\":2},{\"command\":\"flash_off\",\"power_id\":\"JY05mmBEm0G73G8d\",\"port\":3}]";
    @Test
    public void getMessageBean(){
        List<CommandBean> list = commandConverter.getCommandList(str);
        System.out.println(list);
    }
}