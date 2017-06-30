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
    private String socketString;

    public PoController(Map<String, PoController> poMap, Socket socket) {
        socketString = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
        logger.debug("accept a socket from " + socketString);
        this.poMap = poMap;
        this.socket = socket;
        if (socket != null) {
            try {
                socket.setSoTimeout(100000);
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
                        //System.out.println("receive --> " + str);
                        if (str.startsWith("FE 04 10")) {
                            poInfo.id = ByteTools.getIdByString(str);
                            if (poInfo.id != null) {
                                poMap.put(poInfo.id, PoController.this);
                                logger.debug("put the device '" + poInfo.id + "'(from " + socketString + ") into poMap");
                            }
                        } else if (str.startsWith("FE 01 01")) {
                            poInfo.status = ByteTools.getStatusByString(str);
                            if (poInfo.getTargetStatus() == null) {
                                poInfo.setTargetStatus(poInfo.status);
                            }
                            logger.debug(poInfo.id + "'s status --> " + poInfo.status);
                            poInfo.needToChangeStatus = false;

                            if (!poInfo.status.equals(poInfo.getTargetStatus())) {
                                setDeviceStatus();
                            }
                        } else {
                            poInfo.needToChangeStatus = true;
                        }

                        bytes = new byte[32];
                        len = inputStream.read(bytes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    detonate();
                }
            }
        }.start();
    }

    private void detonate() {
        poInfo.isAlive = false;
        if (poMap.get(poInfo.id) == this) {
            poMap.remove(poInfo.id);
            logger.debug(poInfo.id + " from(" + socketString + ") is destroyed.");
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void askIdUntilAnswer() {
        PoHelper.askIdUntilAnswer(poInfo, outputStream);
    }

    private void askStatus() {
        PoHelper.askStatus(poInfo, outputStream);
    }

    private synchronized void setDeviceStatus() {
        try {
            PoHelper.setDeviceStatus(poInfo, outputStream);
            logger.debug("change " + poInfo.id + "'s status to --> " + poInfo.getTargetStatus());
            poInfo.needToChangeStatus = true;
        } catch (Exception e) {
            e.printStackTrace();
            detonate();
        }
    }

    public void setDeviceStatus(String binaryString) {
        if (binaryString == null || binaryString.length() != 4)
            return;
        poInfo.setTargetStatus(binaryString);
        setDeviceStatus();
    }

    public void setDeviceStatus(String port, String flag) {
        try {
            int iport = Integer.parseInt(port) - 1;
            StringBuilder sb = new StringBuilder(poInfo.getTargetStatus());
            sb.setCharAt(iport, flag.toCharArray()[0]);
            poInfo.setTargetStatus(sb.toString());
            setDeviceStatus();
        } catch (Exception e) {
            logger.debug("args:" + port + "," + flag + " is illegal.");
        }
    }

    public String getDeviceStatus() {
        return poInfo.status;
    }
}
