package com.microwise.matchmaker.netconn.udp;

import org.springframework.stereotype.Component;

import java.net.DatagramPacket;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by lee on 5/15/2017.
 */
@Component("messageSender")
public class MessageSender {
    private Queue<DatagramPacket> queue = new LinkedList<DatagramPacket>();

    public void sendPacket(DatagramPacket packet){
        queue.offer(packet);
    }
}
