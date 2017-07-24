package com.microwise.smartservices;

import com.microwise.smartservices.moroesst.TcpTransducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by lee on 7/24/2017.
 */
@Component("moroesTcpServer")
public class MoroesTcpServer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${tcpServer_port}")
    private int tcpServer_port;
    @Resource(name = "tcpTransducer")
    private TcpTransducer tcpTransducer;

    public void startTcpServer() {
        new Thread() {
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(tcpServer_port);
                    logger.info("start tcp ServerSocket at 0.0.0.0:" + tcpServer_port);
                    while (true) {
                        Socket socket = serverSocket.accept();
                        logger.debug("aaaaaaaaaaa");
                        tcpTransducer.socketHandller(socket);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
