package com.microwise.smartservices.moroesst;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lee on 7/25/2017.
 */
public class CommandControllerIntergrationTest {

    Socket socket;
    OutputStream outputStream;
    InputStream inputStream;

    @Before
    public void init() {
        try {
            socket = new Socket("localhost", 5555);
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendCommands() {
        byte[] commandBytes = ("[{\"command\":\"power_on\",\"power_id\":\"JY05mmBEm0G73G8d\",\"port\":1}," +
                "{\"command\":\"sleep\",\"second\":2}," +
                "{\"command\":\"flash_off\",\"power_id\":\"JY05mmBEm0G73G8d\",\"port\":3}]").getBytes();
        System.out.println(commandBytes.length);
        List<Byte> lByte = new LinkedList<Byte>();
        byte[] head = DataTools.hexStringToBytes("55 AA 01 01");
        for (int i = 0; i < 4; i++) {
            lByte.add(head[i]);
        }
        byte[] len = DataTools.getBytesByInt(commandBytes.length, 2);
        lByte.add(len[0]);
        lByte.add(len[1]);
        System.out.println(DataTools.bytesToHexString(lByte));
        for (int i = 0; i < commandBytes.length; i++) {
            lByte.add(commandBytes[i]);
        }

        try {
            outputStream.write(DataTools.getBytesFromList(lByte));
            Thread.sleep(1000);
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}