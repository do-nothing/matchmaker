package com.microwise.smartservices.moroesst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Created by lee on 7/24/2017.
 */
@Component("tcpTransducer")
public class TcpTransducer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "bulletinController")
    private BulletinController bulletinController;
    @Resource(name = "commandController")
    private CommandController commandController;
    @Resource(name = "statusCenter")
    private StatusCenter statusCenter;

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
                    LinkedList<Byte> lbyte = new LinkedList<Byte>();
                    int count = 0;
                    int dataLen = -1;
                    while ((len = inputStream.read(bytes)) != -1) {
                        for (int i = 0; i < len; i++) {
                            lbyte.add(bytes[i]);
                            count++;
                            if (count == 6) {
                                //System.out.println("head --> " + DataTools.bytesToHexString(lbyte));
                                dataLen = getDataLen(lbyte);
                                //System.out.println("dataLen --> " + dataLen);
                            }

                            if (count == dataLen) {
                                responseMessage(lbyte, outputStream);

                                count = 0;
                                dataLen = -1;
                                lbyte.clear();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        inputStream.close();
                        outputStream.close();
                    } catch (IOException ioe) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void responseMessage(LinkedList<Byte> lbyte, OutputStream outputStream) throws IOException {
        byte messageTypte = lbyte.get(2);
        String messageSeiral = DataTools.bytesToHexString(new byte[]{lbyte.get(3)});
        for (int j = 0; j < 6; j++) {
            lbyte.pop();
        }

        String ack = "";
        if (messageTypte == 1) {
            String commands = new String(DataTools.getBytesFromList(lbyte));
            logger.debug("receive a command --> " + commands);
            String commandsKey = commandController.getCommandsKey(commands);
            if (statusCenter.commandsIsLoak(commandsKey)) {
                ack = "55 AA 81 " + messageSeiral + " 00 01 00";
            } else {
                commandController.execCommands(commands);
                ack = "55 AA 81 " + messageSeiral + " 00 01 01";
            }
        } else if (messageTypte == 2) {
            int deviceId = DataTools.getIntByBytes(new byte[]{lbyte.pop(), lbyte.pop(), lbyte.pop(), lbyte.pop()});
            int times = DataTools.getIntByBytes(new byte[]{lbyte.pop(), lbyte.pop()});
            String bulletinStr = new String(DataTools.getBytesFromList(lbyte));
            logger.debug("receive a message for device(" + deviceId + ") --> " + bulletinStr);
            bulletinController.sendBulletin(deviceId, times, bulletinStr);
            ack = "55 AA 82 " + messageSeiral + " 00 00";
        } else {
            logger.warn("receive a wrong message type --> " + messageTypte);
            ack = "55 AA 81 " + messageSeiral + " 00 01 00";
        }
        logger.debug("ack --> " + ack);
        outputStream.write(DataTools.hexStringToBytes(ack));
    }

    private int getDataLen(LinkedList<Byte> lByte) {
        if ((DataTools.bytesToHexString(lByte)).startsWith("55 AA")) {
            return DataTools.getIntByBytes(new byte[]{lByte.get(4), lByte.get(5)}) + 6;
        }
        return -1;
    }
}
