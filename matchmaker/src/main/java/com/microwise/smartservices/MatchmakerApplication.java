package com.microwise.smartservices;

import com.microwise.smartservices.netconn.udp.MessageReceiver;
import com.microwise.smartservices.netconn.udp.MessageSender;
import com.microwise.smartservices.persistency.mapper.Power_controllerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

@SpringBootApplication
public class MatchmakerApplication implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MatchmakerApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
    @Resource(name = "moroesTcpServer")
    private MoroesTcpServer moroesTcpServer;
    @Resource(name = "matchmakerServer")
    private MatchmakerServer matchmakerServer;
    @Resource(name = "messageReceiver")
    private MessageReceiver messageReceiver;
    @Resource(name = "messageSender")
    private MessageSender messageSender;

    //@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            //System.out.println("Let's inspect the beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }

    @Bean("udpServer")
    public DatagramSocket udpServer() {
        DatagramSocket server = null;
        int port = 5555;
        try {
            server = new DatagramSocket(port);
            logger.info("Udp server start at port : " + port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return server;
    }

    @Bean
    public String startMatchmakerServer() {
        String str = "udp server has been started.";
        messageReceiver.startServer();
        messageSender.startServer();
        matchmakerServer.startServer();
        logger.info("***************************** " + str + " ***********************************");
        return str;
    }

    @Bean
    public String MoroesTcpServer() {
        String str = "Moroes tcp server has been started.";
        moroesTcpServer.startTcpServer();
        logger.info("***************************** " + str + " ***********************************");
        return str;
    }

    @Resource
    private Power_controllerMapper power_controllerMapper;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(power_controllerMapper.selectByPid("JY05SfZdGcM0WDdO"));
    }
}
