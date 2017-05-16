package com.microwise.matchmaker;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

import com.microwise.matchmaker.netconn.Messenger;
import com.microwise.matchmaker.netconn.udp.MessageReceiver;
import com.microwise.matchmaker.netconn.udp.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@SpringBootApplication
public class MatchmakerApplication {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MatchmakerApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	@Resource(name="matchmakerServer")
	private MatchmakerServer matchmakerServer;
	@Resource(name="messageReceiver")
	private MessageReceiver messageReceiver;
	@Resource(name="messageSender")
	private MessageSender messageSender;

	//@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("Let's inspect the beans provided by Spring Boot:");
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
		};
	}

	@Bean("udpServer")
	public DatagramSocket udpServer(){
		DatagramSocket  server = null;
		try {
			server = new DatagramSocket(5555);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return server;
	}

	@Bean
	public String startMatchmakerServer(){
		String str = "udp server has been started.";
		messageReceiver.startServer();
		messageSender.startServer();
		matchmakerServer.startServer();
		logger.info("***************************** " + str + " ***********************************");
		return str;
	}
}
