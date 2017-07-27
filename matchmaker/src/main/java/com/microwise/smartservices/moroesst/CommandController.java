package com.microwise.smartservices.moroesst;

import com.microwise.smartservices.moroesst.PowerLogic.CommandBean;
import com.microwise.smartservices.moroesst.PowerLogic.CommandConverter;
import com.microwise.smartservices.moroesst.PowerLogic.PowerFlashCommand;
import com.microwise.smartservices.moroesst.PowerLogic.PowerSwitchCommand;
import com.microwise.smartservices.netconn.JsonConverter;
import com.microwise.smartservices.netconn.Messenger;
import com.microwise.smartservices.netconn.form.MessageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lee on 7/25/2017.
 */
@Component("commandController")
public class CommandController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String messageDemo;
    private MessageBean mb;
    @Resource(name = "commandConverter")
    private CommandConverter commandConverter;
    @Resource(name = "statusCenter")
    private StatusCenter statusCenter;
    @Resource(name = "powerFlashCommand")
    private PowerFlashCommand powerFlashCommand;
    @Resource(name = "powerSwitchCommand")
    private PowerSwitchCommand powerSwitchCommand;
    @Resource(name = "moroesSender")
    private MoroesSender moroesSender;

    public CommandController() {
        messageDemo = "{\"id\":\"web\",\"target\":\"2\",\"monitorId\":\"\",\"logType\":\"power_control\",\"strategy\":\"relay\",\"quality\":1,\"timestamp\":1494825498577," +
                "\"contentBean\":{\"command\":\"showBulletin\",\"args\":[2, \"离闭馆时间还有30分钟，请抓紧时间游览。\"]}}";
    }

    public String getCommandsKey(String commands) {
        Integer deviceId = null;
        String powerId = "";
        List<CommandBean> list = commandConverter.getCommandList(commands);
        for (CommandBean cb : list) {
            if (cb.getDevice_id() != null)
                deviceId = cb.getDevice_id();
            if (cb.getPower_id() != null)
                powerId = cb.getPower_id();
        }
        String rt = deviceId + "_" + powerId;
        return rt;
    }

    public void execCommands(String commands) {
        String commandsKey = getCommandsKey(commands);
        new Thread(() -> {
            statusCenter.startTask(commandsKey);

            List<CommandBean> list = commandConverter.getCommandList(commands);
            for (CommandBean cb : list) {
                if ("sleep".equalsIgnoreCase(cb.getCommand())) {
                    try {
                        Thread.sleep(cb.getSecond() * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else if("shutdown".equalsIgnoreCase(cb.getCommand())){
                    moroesSender.sendShutdownCommand(cb.getDevice_id());
                }else if("power_on".equalsIgnoreCase(cb.getCommand())){
                    powerSwitchCommand.switchPowerOn(cb.getPower_id(), cb.getPort());
                }else if("power_off".equalsIgnoreCase(cb.getCommand())){
                    powerSwitchCommand.switchPowerOff(cb.getPower_id(), cb.getPort());
                }else if("flash_on".equalsIgnoreCase(cb.getCommand())){
                    powerFlashCommand.flashPowerOn(cb.getPower_id(), cb.getPort());
                }else if("flash_off".equalsIgnoreCase(cb.getCommand())){
                    powerFlashCommand.flashPowerOff(cb.getPower_id(), cb.getPort());
                }else if("re_power_on".equalsIgnoreCase(cb.getCommand())){
                    powerSwitchCommand.switchPowerOff(cb.getPower_id(), cb.getPort());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    powerSwitchCommand.switchPowerOn(cb.getPower_id(), cb.getPort());
                }
            }

            statusCenter.finishTask(commandsKey);
        }).start();
    }
}
