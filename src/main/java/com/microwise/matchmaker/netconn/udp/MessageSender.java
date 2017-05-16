package com.microwise.matchmaker.netconn.udp;

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
@Component("messageSender")
public class MessageSender {
    @Resource(name="udpServer")
    private DatagramSocket server;
    private BlockingQueue<DatagramPacket> queue = new LinkedBlockingQueue <DatagramPacket>();
    private boolean isStart = false;

    public void sendPacket(DatagramPacket packet) throws InterruptedException {
        queue.put(packet);
    }

    public void startServer() {
        if (isStart)
            return;
        isStart = true;
        new Thread() {
            public void run() {
                while (isStart) {
                    try {
                        DatagramPacket packet = queue.take();
                        server.send(packet);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
