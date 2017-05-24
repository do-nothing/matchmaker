package com.microwise.smartservices.netconn.udp;

import org.springframework.stereotype.Component;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * Created by lee on 5/15/2017.
 */
@Component("packetHelper")
public class DatagramPacketHelper {
    private class Address{
        InetAddress address;
        int port;

        Address(InetAddress address, int port) {
            this.address = address;
            this.port = port;
        }
    }
    private HashMap<String, Address> addressPool = new HashMap<>();

    public String getReceiveStr(DatagramPacket datagramPacket){
        String recvStr = new String(datagramPacket.getData() , 0 , datagramPacket.getLength());
        return recvStr;
    }

    public void setClientAddress(String id, DatagramPacket datagramPacket){
        InetAddress ip = datagramPacket.getAddress();
        int port = datagramPacket.getPort();
        Address address = new Address(ip, port);
        addressPool.put(id, address);
    }

    public DatagramPacket getDatagramPacket(String target, String sendStr){
        Address address = addressPool.get(target);
        byte[] sendBuf = sendStr.getBytes();
        DatagramPacket packet = new DatagramPacket(sendBuf ,sendBuf.length , address.address , address.port);
        return packet;
    }

    public void setServerAddress(String ipstr, int port) throws UnknownHostException {
        InetAddress ip = InetAddress.getByName(ipstr);
        Address address = new Address(ip, port);
        addressPool.put("server", address);
    }
}
