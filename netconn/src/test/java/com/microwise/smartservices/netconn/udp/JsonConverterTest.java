package com.microwise.smartservices.netconn.udp;

import com.microwise.smartservices.netconn.JsonConverter;
import com.microwise.smartservices.netconn.Messenger;
import com.microwise.smartservices.netconn.form.ContentBean;
import com.microwise.smartservices.netconn.form.MessageBean;
import org.junit.Before;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

/**
 * Created by John on 2017/5/14.
 */
public class JsonConverterTest {
    private MessageBean mb;
    private JsonConverter jsonConverter = new JsonConverter();
    private String json;
    @Resource(name = "udpMessenger")
    private Messenger messenger;
    @Before
    public void setUp() throws Exception {
        mb = new MessageBean();
        mb.setId("001");
        mb.setTarget("007");
        mb.setLogType("path");
        mb.setStrategy("echoStrategy");
        mb.setQuality(0);
        mb.setTimestamp(1494825498577l);
        mb.setContentBean(new ContentBean());
        mb.getContentBean().setCommand("updateUserInfo");
        mb.getContentBean().setArgs(new String[]{"108.8549","34.19662"});

        json = "{\"id\":\"001\",\"target\":\"007\",\"monitorId\":null,\"logType\":\"path\"," +
                "\"strategy\":\"echoStrategy\",\"quality\":0,\"timestamp\":1494825498577," +
                "\"contentBean\":{\"command\":\"updateUserInfo\",\"args\":[\"108.8549\",\"34.19662\"]}," +
                "\"token\":null}";
    }

    @Test
    public void getMessageBean() throws Exception {
        MessageBean value = jsonConverter.getMessageBean(json);
        System.out.println(value);
        assertEquals(mb.toString(), value.toString());
    }

    @Test
    public void getJsonString() throws Exception {
        String value = jsonConverter.getJsonString(mb);
        System.out.println(value);
        assertEquals(json, value);
    }
}