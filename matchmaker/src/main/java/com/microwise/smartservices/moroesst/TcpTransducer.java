package com.microwise.smartservices.moroesst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lee on 7/24/2017.
 */
@Component("tcpTransducer")
public class TcpTransducer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void socketHandller(Socket socket) {
        String socketString = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
        logger.debug("accept a socket from " + socketString);
        if (socket != null) {
            try {
                socket.setSoTimeout(10000);
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                messageHandller(inputStream, outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void messageHandller(InputStream inputStream, OutputStream outputStream) {
        new Thread() {
            public void run() {
                try {
                    byte[] bytes = new byte[1024];
                    int len = inputStream.read(bytes);
                    LinkedList<Byte> lbyte = new LinkedList<Byte>();
                    int count = 0;
                    int dataLen = -1;
                    while ((len = inputStream.read(bytes)) != -1) {
                        for (int i = 0; i < len; i++) {
                            lbyte.add(bytes[i]);
                            count++;
                            if(count == 5){
                                dataLen = getDataLen(lbyte);
                            }
                            if(count == dataLen){
                                break;
                            }
                        }
                    }
                    if (count == dataLen) {
                        outputStream.write(DataTools.hexStringToBytes("55 AA 81 00 00"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private int getDataLen( LinkedList<Byte> lByte){
        byte[] bytes = new byte[]{lByte.pop(), lByte.pop(), lByte.pop()};
        if("55 AA 01".equals(DataTools.bytesToHexString(bytes))){
            return DataTools.getIntByBytes(lByte.pop(), lByte.pop()) + 5;
        }
        return -1;
    }
}
