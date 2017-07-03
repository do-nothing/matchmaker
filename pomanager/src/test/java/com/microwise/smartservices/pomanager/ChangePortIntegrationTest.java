package com.microwise.smartservices.pomanager;

import com.microwise.smartservices.netconn.form.MessageBean;
import com.microwise.smartservices.netconn.udp.JsonConverter;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by lee on 5/16/2017.
 */

//To test this, you must start MatchmakerApplication first.
public class ChangePortIntegrationTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private MessageBean mb;
    private JsonConverter jsonConverter = new JsonConverter();
    private int count = 0;

    @Before
    public void setUp() throws Exception {
        String message = "{\"id\":\"monitor\",\"target\":\"JY05SfZdGcM0WDdO\",\"logType\":\"path\",\"strategy\":\"relay\",\"quality\":1,\"timestamp\":1494825498577," +
                "\"contentBean\":{\"command\":\"setStatus\",\"args\":[4, 1]}}";
        System.out.println(message);
        mb = jsonConverter.getMessageBean(message);
    }

    private void testByBean(String sendStr) throws IOException {
        DatagramSocket client = new DatagramSocket();
        byte[] sendBuf;
        sendBuf = sendStr.getBytes();
        InetAddress addr = InetAddress.getByName("121.42.196.133");
        int port = 5555;
        DatagramPacket sendPacket
                = new DatagramPacket(sendBuf, sendBuf.length, addr, port);
        client.send(sendPacket);
        client.close();
    }

    @Test
    public void sendRandomCommandTest() throws Exception {
        String sendStr = jsonConverter.getJsonString(mb);
        testByBean(sendStr);
    }

    @Test
    public void groupTest() throws Exception {
        int flag = 1;
        mb.getContentBean().setArgs(new Integer[]{1,flag});
        String sendStr = jsonConverter.getJsonString(mb);
        testByBean(sendStr);
        mb.getContentBean().setArgs(new Integer[]{2,flag});
        sendStr = jsonConverter.getJsonString(mb);
        testByBean(sendStr);
        mb.getContentBean().setArgs(new Integer[]{3,flag});
        sendStr = jsonConverter.getJsonString(mb);
        testByBean(sendStr);
        mb.getContentBean().setArgs(new Integer[]{4,flag});
        sendStr = jsonConverter.getJsonString(mb);
        testByBean(sendStr);
    }
}