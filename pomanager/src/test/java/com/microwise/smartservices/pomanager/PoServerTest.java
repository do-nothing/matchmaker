package com.microwise.smartservices.pomanager;

import org.junit.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import static org.junit.Assert.*;

/**
 * Created by lee on 6/23/2017.
 */
public class PoServerTest {
    @Test
    public void startServer() throws Exception {
        try {
            Socket socket=new Socket("localhost",8888);

            OutputStream outputStream=socket.getOutputStream();
            byte[] bytes = ByteTools.hexStringToBytes("fe 04 10 4a 59 30 35 53 66 5a 64 47 63 4d 30 57 44 64 4f dd 36");
            outputStream.write(bytes);
            outputStream.flush();

            /*InputStream inputStream = socket.getInputStream();
            int len = inputStream.read(bytes);*/

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}