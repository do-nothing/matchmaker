package com.microwise.smartservices.pomanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

/**
 * Created by lee on 6/26/2017.
 */
public class PoController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private PoInfo poInfo = new PoInfo();
    private Map<String, PoController> poMap;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public PoController(Map<String, PoController> poMap, Socket socket) {
        this.poMap = poMap;
        this.socket = socket;
        if (socket != null) {
            try {
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                poInfo.isAlive = true;
                startReceiver();
                askIdUntilAnswer();
                askStatus();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void startReceiver() {
        new Thread() {
            public void run() {
                try {
                    byte[] bytes = new byte[32];
                    int len = inputStream.read(bytes);
                    String str = null;
                    while (len > 0 && poInfo.isAlive) {
                        if (bytes[0] == -2) {
                            str = ByteTools.bytesToHexString(bytes, len);
                        } else {
                            str += " " + ByteTools.bytesToHexString(bytes, len);
                        }

                        if (str.startsWith("FE 04 10")) {
                            poInfo.id = ByteTools.getIdByString(str);
                            if (poInfo.id != null) {
                                poMap.put(poInfo.id, PoController.this);
                                logger.debug("poMap put a device --> " + poInfo.id);
                            }
                        } else if (str.startsWith("FE 01 01")) {
                            poInfo.status = ByteTools.getStatusByString(str);
                            logger.debug(poInfo.id + "'s status --> " + poInfo.status);
                        } else {
                            poInfo.needToChangeStatus = true;
                        }

                        bytes = new byte[32];
                        len = inputStream.read(bytes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    poInfo.isAlive = false;
                    poMap.remove(poInfo.id);
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }

    private void askIdUntilAnswer() {
        PoHelper.askIdUntilAnswer(poInfo, outputStream);
    }

    private void askStatus() {
        PoHelper.askStatus(poInfo, outputStream);
    }

    public void setDeviceStatus(String binaryString) throws Exception {
        PoHelper.setDeviceStatus(outputStream, binaryString);
    }

    public String getDeviceStatus(){
        return poInfo.status;
    }
}
