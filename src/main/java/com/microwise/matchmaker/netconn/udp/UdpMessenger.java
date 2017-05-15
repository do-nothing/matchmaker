package com.microwise.matchmaker.netconn.udp;

import com.microwise.matchmaker.netconn.MessageBean;
import com.microwise.matchmaker.netconn.Messenger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.DatagramPacket;

/**
 * Created by John on 2017/5/14.
 */

@Component("udpMessenger")
public class UdpMessenger implements Messenger {
    @Resource(name="messageReceiver")
    private MessageReceiver messageReceiver;
    @Resource(name="messageSender")
    private MessageSender messageSender;
    @Resource(name="jsonConverter")
    private JsonConverter jsonConverter;
    @Resource(name="packetHelper")
    private DatagramPacketHelper packetHelper;

    @Override
    public MessageBean getMessage() {
        DatagramPacket packet = messageReceiver.getPacket();
        String jsonString = packetHelper.getReceiveStr(packet);
        MessageBean messageBean = jsonConverter.getMessageBean(jsonString);
        packetHelper.setClientAddress(messageBean.getId(), packet);
        return messageBean;
    }

    @Override
    public void sendMessage(MessageBean message) {
        String jsonString = jsonConverter.getJsonString(message);
        DatagramPacket packet = packetHelper.getDatagramPacket(message.getId(), jsonString);
        messageSender.sendPacket(packet);
    }
}
