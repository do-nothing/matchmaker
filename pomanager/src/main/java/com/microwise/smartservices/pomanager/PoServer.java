package com.microwise.smartservices.pomanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lee on 6/23/2017.
 */
@Component
public class PoServer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Map<String, PoController> poMap = new HashMap<String, PoController>();

    public void startServer() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(5557);
            logger.debug("************* poServer has been started!  ***************");
            while(true) {
                Socket socket = serverSocket.accept();
                new PoController(poMap, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
