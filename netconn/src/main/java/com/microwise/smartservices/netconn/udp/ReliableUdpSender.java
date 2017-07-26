package com.microwise.smartservices.netconn.udp;

import com.microwise.smartservices.netconn.form.MessageBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.DatagramPacket;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lee on 7/26/2017.
 */
@Component("reliableUdpSender")
public class ReliableUdpSender {
    @Resource(name = "taskStatusCenter")
    private TaskStatusCenter taskStatusCenter;
    @Resource(name = "messageSender")
    private MessageSender messageSender;

    public void sendPacket(DatagramPacket packet, String messagekey) {
        taskStatusCenter.setUnfinishedTask(messagekey);
        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                if (taskStatusCenter.checkUnfinishedTask(messagekey)) {
                    try {
                        messageSender.sendPacket(packet);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                } else {
                    break;
                }
            }
        }).start();
    }

    public void sendSuccessed(MessageBean messageBean){
        taskStatusCenter.rmUnfinishedTask(messageBean.getToken());
    }
}
