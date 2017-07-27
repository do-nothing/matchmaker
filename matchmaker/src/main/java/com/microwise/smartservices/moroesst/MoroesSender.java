package com.microwise.smartservices.moroesst;

import com.microwise.smartservices.netconn.JsonConverter;
import com.microwise.smartservices.netconn.Messenger;
import com.microwise.smartservices.netconn.form.MessageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by lee on 7/25/2017.
 */
@Component("moroesSender")
public class MoroesSender {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource(name = "jsonConverter")
    private JsonConverter jsonConverter;
    @Resource(name = "udpMessenger")
    private Messenger messenger;

    public void sendBulletin(int deviceId, int times, String bulletinStr) {
        String messageDemo = "{\"id\":\"web\",\"target\":\"2\",\"monitorId\":\"\",\"logType\":\"moroes_command\"," +
                "\"strategy\":\"relay\",\"quality\":1,\"timestamp\":1494825498577," +
                "\"contentBean\":{\"command\":\"showBulletin\",\"args\":[2, \"离闭馆时间还有30分钟，请抓紧时间游览。\"]}}";
        MessageBean mb = jsonConverter.getMessageBean(messageDemo);
        mb.setTarget(String.valueOf(deviceId));
        mb.getContentBean().getArgs()[0] = times;
        mb.getContentBean().getArgs()[1] = bulletinStr;
        mb.setTimestamp(System.currentTimeMillis());
        messenger.sendMessage(mb);
    }

    public void sendShutdownCommand(int deviceId) {
        String messageDemo = "{\"id\":\"web\",\"target\":\"2\",\"monitorId\":\"\",\"logType\":\"moroes_command\"," +
                "\"strategy\":\"relay\",\"quality\":1,\"timestamp\":1494825498577," +
                "\"contentBean\":{\"command\":\"shutdown\",\"args\":[\"莫罗斯管理平台已发出关机指令，本机将在1分钟后关闭。\"]}}";
        MessageBean mb = jsonConverter.getMessageBean(messageDemo);
        mb.setTarget(String.valueOf(deviceId));
        mb.setTimestamp(System.currentTimeMillis());
        messenger.sendMessage(mb);
    }

    public void sendSwitchCommand(String pid, int port, int flag) {
        String messageDemo = "{\"id\":\"web\",\"target\":\"JY05rR7Dp2a1iyeF\",\"monitorId\":\"\"," +
                "\"logType\":\"moroes_command\",\"strategy\":\"relay\",\"quality\":0,\"timestamp\":1494825498577," +
                "\"contentBean\":{\"command\":\"setStatus\",\"args\":[2, 1]}}";
        MessageBean mb = jsonConverter.getMessageBean(messageDemo);
        mb.setTarget(pid);
        mb.getContentBean().getArgs()[0] = port;
        mb.getContentBean().getArgs()[1] = flag;
        mb.setTimestamp(System.currentTimeMillis());
        logger.debug(mb.toString());
        messenger.sendMessage(mb);
    }

    public void sendFlashCommand(String pid, int port, int flag) {
        String messageDemo = "{\"id\":\"web\",\"target\":\"JY05rR7Dp2a1iyeF\",\"monitorId\":\"\"," +
                "\"logType\":\"moroes_command\",\"strategy\":\"relay\",\"quality\":2,\"timestamp\":1494825498577," +
                "\"contentBean\":{\"command\":\"flash\",\"args\":[2, 1]}}";
        MessageBean mb = jsonConverter.getMessageBean(messageDemo);
        mb.setTarget(pid);
        mb.getContentBean().getArgs()[0] = port;
        mb.getContentBean().getArgs()[1] = flag;
        mb.setTimestamp(System.currentTimeMillis());
        messenger.sendMessage(mb);
    }

    public void sendStartAppCommand(int did, String name, String version) {
        String messageDemo = "{\"id\":\"web\",\"target\":\"JY05rR7Dp2a1iyeF\",\"monitorId\":\"\"," +
                "\"logType\":\"moroes_command\",\"strategy\":\"relay\",\"quality\":1,\"timestamp\":1494825498577," +
                "\"contentBean\":{\"command\":\"restart\",\"args\":[\"guanniao\",\"\"]}}";
        MessageBean mb = jsonConverter.getMessageBean(messageDemo);
        mb.setTarget(String.valueOf(did));
        mb.getContentBean().getArgs()[0] = name;
        if (version != null)
            mb.getContentBean().getArgs()[1] = version;
        mb.setTimestamp(System.currentTimeMillis());
        messenger.sendMessage(mb);
    }
}
