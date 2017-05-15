package com.microwise.matchmaker.netconn.udp;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by lee on 5/15/2017.
 */
@Component("messageReceiver")
public class MessageReceiver {
    @Resource(name="udpServer")
    private DatagramSocket server;
    private Queue<DatagramPacket> queue = new LinkedList<DatagramPacket>();
    private boolean isStart = false;

    public DatagramPacket getPacket() {
        return queue.poll();
    }

    public void startServer() {
        if (isStart)
            return;

        isStart = true;
        new Thread() {
            public void run() {
                while (isStart) {

                }
            }
        }.start();
    }

    public void test(){
        System.out.println(server);
    }
}
