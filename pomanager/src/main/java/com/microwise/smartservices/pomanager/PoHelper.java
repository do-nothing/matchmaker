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
    public static void setDeviceStatus(PoInfo poInfo, OutputStream outputStream) throws Exception {
        for (int i = 0; i < 4; i++) {
            if (poInfo.status.substring(i, i + 1).equals(poInfo.getTargetStatus().substring(i, i + 1))) {
                continue;
            }
            String command = "";
            String ts = poInfo.getTargetStatus().substring(i, i + 1);
            if ("1".equals(ts)) {
                command = CommandDict.turnOnArray[i];
            } else {
                command = CommandDict.turnOffArray[i];
            }
            //System.out.println("command --> " + command);
            outputStream.write(ByteTools.hexStringToBytes(command));
            outputStream.flush();
            Thread.sleep(100);
        }
    }

    public static void flashByPort(String port, String flag, OutputStream outputStream) throws Exception {
        String command = "";
        int iPort = Integer.parseInt(port) - 1;
        if ("1".equals(flag)) {
            command = CommandDict.flashOnArray[iPort];
        } else if ("0".equals(flag)) {
            command = CommandDict.flashOffArray[iPort];
        } else {
            throw new Exception("flashByPort(String port, String flag) --> flag is illegal.");
        }
        //System.out.println("command --> " + command);
        outputStream.write(ByteTools.hexStringToBytes(command));
        Thread.sleep(100);
    }

    public static void askIdUntilAnswer(PoInfo poInfo, OutputStream outputStream) {
        new Thread() {
            public void run() {
                try {
                    while (poInfo.isAlive && poInfo.id == null) {
                        String command = CommandDict.ASK_ID;
                        //System.out.println("command --> " + command);
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
                            Thread.sleep(500);
                            String command = CommandDict.ASK_STATUS;
                            //System.out.println("command --> " + command);
                            outputStream.write(ByteTools.hexStringToBytes(command));
                        }
                        Thread.sleep(500);
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
