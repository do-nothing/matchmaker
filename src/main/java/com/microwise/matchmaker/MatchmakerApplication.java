package com.microwise.matchmaker;

import java.util.Arrays;

import com.microwise.matchmaker.netconn.Messenger;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@SpringBootApplication
public class MatchmakerApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MatchmakerApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	@Resource(name="udpMessenger")
	public Messenger messenger;

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

	@Bean
	public String startUDPServer(){
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
		messenger.sendMessage(null);
		return "udp server has been started.";
	}
}
