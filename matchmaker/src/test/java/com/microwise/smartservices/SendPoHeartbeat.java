package com.microwise.smartservices;

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
public class SendPoHeartbeat {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private MessageBean mb;
    private JsonConverter jsonConverter = new JsonConverter();

    @Before
    public void setUp() throws Exception {
        String message = "{\"id\":\"monitor\",\"target\":\"server\",\"logType\":\"nolog\",\"strategy\":\"po_heartbeat\",\"quality\":0,\"timestamp\":1494825498577," +
                "\"contentBean\":{\"command\":\"recordStatus\",\"args\":[\"0000\"]}}";
        mb = jsonConverter.getMessageBean(message);
    }

    private void sendMessage(String sendStr) throws IOException {
        DatagramSocket client = new DatagramSocket();
        byte[] sendBuf;
        sendBuf = sendStr.getBytes();
        InetAddress addr = InetAddress.getByName("127.0.0.1");
        int port = 5555;
        DatagramPacket sendPacket
                = new DatagramPacket(sendBuf, sendBuf.length, addr, port);

        client.send(sendPacket);
    }

    @Test
    public void udpMessengerTest() throws Exception {
        String sendStr = jsonConverter.getJsonString(mb);
        sendMessage(sendStr);
    }

}