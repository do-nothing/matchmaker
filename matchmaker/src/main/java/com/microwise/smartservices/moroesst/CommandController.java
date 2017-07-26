package com.microwise.smartservices.moroesst;

import com.microwise.smartservices.netconn.JsonConverter;
import com.microwise.smartservices.netconn.Messenger;
import com.microwise.smartservices.netconn.form.MessageBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by lee on 7/25/2017.
 */
@Component("commandController")
public class CommandController {
    private String messageDemo;
    private MessageBean mb;
    @Resource(name = "jsonConverter")
    private JsonConverter jsonConverter;
    @Resource(name="udpMessenger")
    private Messenger messenger ;

    public CommandController() {
        messageDemo = "{\"id\":\"web\",\"target\":\"2\",\"monitorId\":\"\",\"logType\":\"bulletin\",\"strategy\":\"relay\",\"quality\":1,\"timestamp\":1494825498577," +
                "\"contentBean\":{\"command\":\"showBulletin\",\"args\":[2, \"离闭馆时间还有30分钟，请抓紧时间游览。\"]}}";
    }

    public String getCommandsKey(String commands){
        String key = null;
        return key;
    }

    public void execCommands(String commands){

    }
}
