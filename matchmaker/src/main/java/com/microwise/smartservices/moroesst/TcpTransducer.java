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
                    int len = -1;
                    List<Byte> lbyte = new LinkedList<Byte>();
                    while ((len = inputStream.read(bytes)) != -1) {
                        for (int i = 0; i < len; i++) {
                            lbyte.add(bytes[i]);
                        }
                    }
                    if (validation(lbyte.toArray(new Byte[0]))) {
                        outputStream.write(hexStringToBytes("55 AA 81 00 00"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private boolean validation(Byte[] bytes) {
        if (bytes.length < 5)
            return false;
        byte[] head = new byte[5];
        for (int i = 0; i < 5; i++) {
            head[i] = bytes[i];
        }
        byte[] body = new byte[bytes.length - 5];
        for (int i = 5; i < bytes.length; i++) {
            body[i-5] = bytes[i];
        }
        try {
            String str = new String(body, "UTF-8");
            logger.debug(bytesToHexString(head) + " --> " + str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.replaceAll(" ", "");
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
            stringBuilder.append(" ");
        }
        String rt = stringBuilder.toString();
        rt = rt.trim();
        rt = rt.toUpperCase();
        return rt;
    }
}
