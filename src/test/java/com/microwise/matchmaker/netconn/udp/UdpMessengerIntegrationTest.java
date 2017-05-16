package com.microwise.matchmaker.netconn.udp;

import com.microwise.matchmaker.netconn.ContentBean;
import com.microwise.matchmaker.netconn.MessageBean;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static org.junit.Assert.*;

/**
 * Created by lee on 5/16/2017.
 */

//To test this, you must start MatchmakerApplication first.
public class UdpMessengerIntegrationTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String message;

    @Before
    public void setUp() throws Exception {
        message = "{\"id\":\"001\",\"target\":\"007\",\"logType\":\"path\",\"quality\":0,\"timestamp\":1494825498577," +
                "\"contentBean\":{\"command\":\"updateUserInfo\",\"args\":[\"108.8549\",\"34.19662\"]}}";
    }

    @Test
    public void UdpMessengerTest() throws Exception {
        DatagramSocket client = new DatagramSocket();

        String sendStr = message;
        byte[] sendBuf;
        sendBuf = sendStr.getBytes();
        InetAddress addr = InetAddress.getByName("127.0.0.1");
        int port = 5555;
        DatagramPacket sendPacket
                = new DatagramPacket(sendBuf ,sendBuf.length , addr , port);

        client.send(sendPacket);

        byte[] recvBuf = new byte[548];
        DatagramPacket recvPacket
                = new DatagramPacket(recvBuf , recvBuf.length);
        client.receive(recvPacket);
        String recvStr = new String(recvPacket.getData() , 0 ,recvPacket.getLength());

        assertEquals(sendStr, recvStr);

        logger.debug("收到:" + recvStr);
        client.close();
    }

}