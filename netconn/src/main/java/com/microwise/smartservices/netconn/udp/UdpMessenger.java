package com.microwise.smartservices.netconn.udp;

import com.microwise.smartservices.netconn.AbstractMessenger;
import com.microwise.smartservices.netconn.JsonConverter;
import com.microwise.smartservices.netconn.form.MessageBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.DatagramPacket;
import java.net.UnknownHostException;

/**
 * Created by John on 2017/5/14.
 */

@Component("udpMessenger")
public class UdpMessenger extends AbstractMessenger {
    @Resource(name = "messageReceiver")
    private MessageReceiver messageReceiver;
    @Resource(name = "messageSender")
    private MessageSender messageSender;
    @Resource(name = "jsonConverter")
    private JsonConverter jsonConverter;
    @Resource(name = "packetHelper")
    private DatagramPacketHelper packetHelper;

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
        packetHelper.setClientAddress(messageBean.getId(), packet);
        return messageBean;
    }

    @Override
    public void setServerAddress(String ip, int port) throws UnknownHostException {
        packetHelper.setServerAddress(ip, port);
    }

    @Override
    public void sendMessage(MessageBean message) {
        if (message == null)
            return;
        message.setToken(calcTokenByMessage(message));
        String jsonString = jsonConverter.getJsonString(message);
        DatagramPacket packet = packetHelper.getDatagramPacket(message.getTarget(), jsonString);
        try {
            messageSender.sendPacket(packet);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
