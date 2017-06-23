package com.microwise.smartservices.pomanager;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by lee on 6/23/2017.
 */
@Component
public class PoServer {
    public void startServer() {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8888);
            System.out.println("服务端已启动，等待客户端连接.");
            Socket socket = serverSocket.accept();
            System.out.println("客户端已连接.来自 -->" + socket.getPort());

            InputStream inputStream = socket.getInputStream();
            int len = 0;
            while (len != -1) {
                byte[] bytes = new byte[32];
                len = inputStream.read(bytes);
                String str = null;
                if (len == 21) {
                    str = ByteTools.getIdByBytes(bytes);
                } else {
                    str = ByteTools.bytesToHexString(bytes, len);
                }
                System.out.println(str);
            }

            inputStream.close();
            socket.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
