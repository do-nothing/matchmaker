package com.microwise.smartservices.netconn.udp;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by lee on 5/15/2017.
 */
@Component("messageReceiver")
public class MessageReceiver {
    @Resource(name = "udpServer")
    private DatagramSocket server;
    private BlockingQueue<DatagramPacket> queue = new LinkedBlockingQueue<DatagramPacket>();
    private boolean isStart = false;

    public DatagramPacket getPacket() throws InterruptedException {
        return queue.take();
    }

    public void startServer() {
        if (isStart)
            return;

        isStart = true;
        new Thread() {
            public void run() {
                while (isStart) {
                    byte[] recvBuf = new byte[548];
                    DatagramPacket recvPacket
                            = new DatagramPacket(recvBuf, recvBuf.length);
                    try {
                        server.receive(recvPacket);
                        queue.put(recvPacket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
