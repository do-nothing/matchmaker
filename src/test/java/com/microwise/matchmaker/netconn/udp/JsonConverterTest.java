package com.microwise.matchmaker.netconn.udp;

import com.microwise.matchmaker.netconn.MessageBean;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by John on 2017/5/14.
 */
public class JsonConverterTest {
    private MessageBean mb;
    private JsonConverter jsonConverter = new JsonConverter();
    private String json;

    @Before
    public void setUp() throws Exception {
        mb = new MessageBean();
        mb.setId("001");
        mb.setTarget("007");
        mb.setCommand("voice");

        json = "{\"id\":\"001\",\"target\":\"007\",\"command\":\"voice\"}";
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
        System.out.println(json);
        assertEquals(json, value);
    }

}