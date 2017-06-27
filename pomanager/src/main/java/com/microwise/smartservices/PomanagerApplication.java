package com.microwise.smartservices;

import com.microwise.smartservices.netconn.udp.MessageReceiver;
import com.microwise.smartservices.netconn.udp.MessageSender;
import com.microwise.smartservices.pomanager.PoServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.net.DatagramSocket;
import java.net.SocketException;

@SpringBootApplication
public class PomanagerApplication implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource(name = "messageReceiver")
    private MessageReceiver messageReceiver;
    @Resource(name = "messageSender")
    private MessageSender messageSender;
    @Resource(name = "poServer")
    private PoServer poServer;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PomanagerApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Bean("udpServer")
    public DatagramSocket udpServer() {
        DatagramSocket server = null;
        try {
            server = new DatagramSocket(5557);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return server;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.debug("************* poServer is start!  ***************");
        messageReceiver.startServer();
        messageSender.startServer();
        poServer.startServers();
    }
}
