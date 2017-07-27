package com.microwise.smartservices.moroesst;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lee on 7/25/2017.
 */
public class BulletinControllerIntergrationTest {

    Socket socket;
    OutputStream outputStream;

    @Before
    public void init() {
        try {
            socket = new Socket("127.0.0.1", 5555);
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendBulletin() {
        byte[] bulletinBytes = "你好，世界！".getBytes();
        System.out.println(bulletinBytes.length);
        List<Byte> lByte = new LinkedList<Byte>();
        byte[] head = DataTools.hexStringToBytes("55 AA 02 01");
        for (int i = 0; i < 4; i++) {
            lByte.add(head[i]);
        }
        byte[] len = DataTools.getBytesByInt(bulletinBytes.length + 6, 2);
        lByte.add(len[0]);
        lByte.add(len[1]);
        byte[] deviceId = DataTools.getBytesByInt(1, 4);
        byte[] times = DataTools.getBytesByInt(2, 2);
        for (int i = 0; i < deviceId.length; i++) {
            lByte.add(deviceId[i]);
        }
        for (int i = 0; i < times.length; i++) {
            lByte.add(times[i]);
        }
        for (int i = 0; i < bulletinBytes.length; i++) {
            lByte.add(bulletinBytes[i]);
        }

        try {
            outputStream.write(DataTools.getBytesFromList(lByte));
            Thread.sleep(1000);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}