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
@Component("bulletinController")
public class BulletinController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String messageDemo;
    private MessageBean mb;
    @Resource(name = "jsonConverter")
    private JsonConverter jsonConverter;
    @Resource(name="reliableSender")
    private ReliableSender reliableSender ;

    public BulletinController() {
        messageDemo = "{\"id\":\"web\",\"target\":\"2\",\"monitorId\":\"\",\"logType\":\"bulletin\",\"strategy\":\"relay\",\"quality\":1,\"timestamp\":1494825498577," +
                "\"contentBean\":{\"command\":\"showBulletin\",\"args\":[2, \"离闭馆时间还有30分钟，请抓紧时间游览。\"]}}";
    }

    public void sendBulletin(int deviceId, int times, String bulletinStr) {
        if(mb == null){
            mb = jsonConverter.getMessageBean(messageDemo);
        }
        mb.setTarget(String.valueOf(deviceId));
        mb.getContentBean().getArgs()[0] = times;
        mb.getContentBean().getArgs()[1] = bulletinStr;
        mb.setTimestamp(System.currentTimeMillis());

        reliableSender.sendMessage(mb);
    }
}
