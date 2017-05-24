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

import static org.junit.Assert.assertEquals;

/**
 * Created by lee on 5/16/2017.
 */

//To test this, you must start MatchmakerApplication first.
public class RelayMessengerIntegrationTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private MessageBean mb;
    private JsonConverter jsonConverter = new JsonConverter();
    private int count = 0;

    @Before
    public void setUp() throws Exception {
        String message = "{\"id\":\"001\",\"target\":\"001\",\"logType\":\"path\",\"strategy\":\"relay\",\"quality\":0,\"timestamp\":1494825498577," +
                "\"contentBean\":{\"command\":\"updateUserInfo\",\"args\":[\"108.8549\",\"34.19662\"]}}";
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

        byte[] recvBuf = new byte[548];
        DatagramPacket recvPacket
                = new DatagramPacket(recvBuf, recvBuf.length);
        client.receive(recvPacket);

        count++;

        String recvStr = new String(recvPacket.getData(), 0, recvPacket.getLength());
        //assertEquals(sendStr, recvStr);
        logger.debug("received: :" + recvStr);
        client.close();
    }

    @Test
    public void udpMessengerTest() throws Exception {
        String sendStr = jsonConverter.getJsonString(mb);
        testByBean(sendStr);
    }

}