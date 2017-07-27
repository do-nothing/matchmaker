package com.microwise.smartservices;

import com.microwise.smartservices.netconn.JsonConverter;
import com.microwise.smartservices.netconn.form.MessageBean;
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
public class RestartIntegrationTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private MessageBean mb;
    private JsonConverter jsonConverter = new JsonConverter();
    private int count = 0;

    @Before
    public void setUp() throws Exception {
        String message = "{\"id\":\"monitor\",\"target\":\"1\",\"logType\":\"path\",\"strategy\":\"relay\",\"quality\":1,\"timestamp\":1494825498577," +
                "\"contentBean\":{\"command\":\"restart\",\"args\":[\"guanniao\",\"\"]}}";
        mb = jsonConverter.getMessageBean(message);
    }

    private void testByBean(String sendStr) throws IOException {
        DatagramSocket client = new DatagramSocket();
        byte[] sendBuf;
        sendBuf = sendStr.getBytes();
        InetAddress addr = InetAddress.getByName("127.0.0.1");
        int port = 5555;
        DatagramPacket sendPacket
                = new DatagramPacket(sendBuf, sendBuf.length, addr, port);

        client.send(sendPacket);
        client.close();
    }

    @Test
    public void udpMessengerTest() throws Exception {
        String sendStr = jsonConverter.getJsonString(mb);
        testByBean(sendStr);
    }

    @Test
    public void vrGroupPcTest() throws Exception {
        String[] group = new String[]{"lijun","fengbin","jianghao","yangwei"};
        for(String one : group){
            mb.setTarget(one);
            String sendStr = jsonConverter.getJsonString(mb);
            logger.debug(sendStr);
            testByBean(sendStr);
        }
    }
}