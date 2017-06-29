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
public class EchoMessengerIntegrationTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private MessageBean mb;
    private JsonConverter jsonConverter = new JsonConverter();
    private int count = 0;

    @Before
    public void setUp() throws Exception {
        String message = "{\"id\":\"001\",\"target\":\"007\",\"logType\":\"path\",\"strategy\":\"echo\",\"quality\":0,\"timestamp\":1494825498577," +
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

    @Test
    public void concurrencyTest() throws Exception {
        for (int i = 0; i < 1000; i++) {
            mb.setId("guide" + i);
            String sendStr = jsonConverter.getJsonString(mb);
            startNewThread(sendStr);
            //Thread.sleep(1);
        }
        Thread.sleep(10000);
        logger.debug(count + " main thread end !!!!!!!!!!!!!!!!!!");
    }

    private void startNewThread(String json) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    testByBean(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }
}