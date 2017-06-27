package com.microwise.smartservices.pomanager;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by lee on 6/26/2017.
 */
public class PoHelper {
    public static void setDeviceStatus(OutputStream outputStream, String binaryString) throws Exception {
        if (binaryString == null || binaryString.length() != 4) {
            return;
        } else {
            for (int i = 0; i < 4; i++) {
                if ("1".equals(binaryString.substring(i, i + 1))) {
                    String command = CommandDict.turnOnArray[i];
                    outputStream.write(ByteTools.hexStringToBytes(command));
                } else {
                    String command = CommandDict.turnOffArray[i];
                    outputStream.write(ByteTools.hexStringToBytes(command));
                }
                Thread.sleep(100);
            }
        }
    }

    public static void askIdUntilAnswer(PoInfo poInfo, OutputStream outputStream) {
        new Thread() {
            public void run() {
                try {
                    while (poInfo.isAlive && poInfo.id == null) {
                        String command = CommandDict.ASK_ID;
                        outputStream.write(ByteTools.hexStringToBytes(command));
                        Thread.sleep(1900);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    poInfo.isAlive = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    poInfo.isAlive = false;
                }
            }
        }.start();
    }

    public static void askStatus(PoInfo poInfo, OutputStream outputStream) {
        new Thread() {
            public void run() {
                try {
                    while (poInfo.isAlive) {
                        if (poInfo.needToChangeStatus) {
                            Thread.sleep(1000);
                            String command = CommandDict.ASK_STATUS;
                            outputStream.write(ByteTools.hexStringToBytes(command));
                            Thread.sleep(1000);
                            if (poInfo.status != null) {
                                poInfo.needToChangeStatus = false;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    poInfo.isAlive = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    poInfo.isAlive = false;
                }
            }
        }.start();
    }
}
