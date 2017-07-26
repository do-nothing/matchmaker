package com.microwise.smartservices.netconn.udp;

import com.microwise.smartservices.netconn.AbstractMessenger;
import com.microwise.smartservices.netconn.JsonConverter;
import com.microwise.smartservices.netconn.form.MessageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.DatagramPacket;
import java.net.UnknownHostException;

/**
 * Created by John on 2017/5/14.
 */

@Component("udpMessenger")
public class UdpMessenger extends AbstractMessenger {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource(name = "messageReceiver")
    private MessageReceiver messageReceiver;
    @Resource(name = "messageSender")
    private MessageSender messageSender;
    @Resource(name = "jsonConverter")
    private JsonConverter jsonConverter;
    @Resource(name = "packetHelper")
    private DatagramPacketHelper packetHelper;
    @Resource(name = "reliableUdpSender")
    private ReliableUdpSender reliableUdpSender;
    @Resource(name = "taskStatusCenter")
    private TaskStatusCenter taskStatusCenter;

    @Override
    public MessageBean getMessage() {
        DatagramPacket packet = null;
        try {
            packet = messageReceiver.getPacket();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (packet == null)
            return null;

        String jsonString = packetHelper.getReceiveStr(packet);
        MessageBean messageBean = jsonConverter.getMessageBean(jsonString);
        if ("ack".equals(messageBean.getStrategy())) {
            reliableUdpSender.sendSuccessed(messageBean);
            return getMessage();
        }
        packetHelper.setClientAddress(messageBean.getId(), packet);
        if (messageBean.getQuality() > 0) {
            replayAck(jsonString);
            if (messageBean.getQuality() == 2) {
                if (taskStatusCenter.checkReceivedTask(messageBean.getToken())) {
                    return getMessage();
                } else {
                    taskStatusCenter.setReceivedTask(messageBean.getToken());
                }
            }
        }
        //logger.debug("receive message" + messageBean);
        return messageBean;
    }

    private void replayAck(String jsonString) {
        MessageBean messageBean = jsonConverter.getMessageBean(jsonString);
        String temp = messageBean.getId();
        messageBean.setId(messageBean.getTarget());
        messageBean.setTarget(temp);
        messageBean.setStrategy("ack");
        jsonString = jsonConverter.getJsonString(messageBean);
        DatagramPacket packet = packetHelper.getDatagramPacket(messageBean.getTarget(), jsonString);
        try {
            messageSender.sendPacket(packet);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setServerAddress(String ip, int port) throws UnknownHostException {
        packetHelper.setServerAddress(ip, port);
    }

    @Override
    public void sendMessage(MessageBean message) {
        if (message == null)
            return;
        //logger.debug("send message" + message);
        message.setToken(calcTokenByMessage(message));
        String jsonString = jsonConverter.getJsonString(message);
        DatagramPacket packet = packetHelper.getDatagramPacket(message.getTarget(), jsonString);
        try {
            if (message.getQuality() > 0) {
                reliableUdpSender.sendPacket(packet, message.getToken());
            } else {
                messageSender.sendPacket(packet);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
