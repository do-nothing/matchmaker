package com.microwise.smartservices.moroesst;

import com.microwise.smartservices.moroesst.PowerLogic.*;
import com.microwise.smartservices.netconn.JsonConverter;
import com.microwise.smartservices.netconn.Messenger;
import com.microwise.smartservices.netconn.form.MessageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
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

    public void execCommands(Integer deviceId, String commands) {
        new Thread(() -> {
            statusCenter.startTask(deviceId);

            List<CommandBean> list = commandConverter.getCommandList(commands);
            for (CommandBean cb : list) {
                if ("sleep".equalsIgnoreCase(cb.getCommand())) {
                    try {
                        Thread.sleep(cb.getSecond() * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if ("shutdown".equalsIgnoreCase(cb.getCommand())) {
                    moroesSender.sendShutdownCommand(cb.getDevice_id());
                } else if ("power_on".equalsIgnoreCase(cb.getCommand())) {
                    powerSwitchCommand.switchPowerOn(cb.getPower_id(), cb.getPort());
                } else if ("power_off".equalsIgnoreCase(cb.getCommand())) {
                    powerSwitchCommand.switchPowerOff(cb.getPower_id(), cb.getPort());
                } else if ("flash_on".equalsIgnoreCase(cb.getCommand())) {
                    powerFlashCommand.flashPowerOn(cb.getPower_id(), cb.getPort());
                } else if ("flash_off".equalsIgnoreCase(cb.getCommand())) {
                    powerFlashCommand.flashPowerOff(cb.getPower_id(), cb.getPort());
                } else if ("re_power_on".equalsIgnoreCase(cb.getCommand())) {
                    if (statusCenter.getPortStatus(cb.getPower_id() + "_" + cb.getPort())) {
                        powerSwitchCommand.switchPowerOff(cb.getPower_id(), cb.getPort());
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    powerSwitchCommand.switchPowerOn(cb.getPower_id(), cb.getPort());
                }
            }

            statusCenter.finishTask(deviceId);
        }).start();
    }

    public void startApp(int deviceId, String appInfo) {
        try {
            AppInfoBean ab = commandConverter.getAppInfo(appInfo);
            moroesSender.sendStartAppCommand(deviceId, ab.getName(), ab.getVersion());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
