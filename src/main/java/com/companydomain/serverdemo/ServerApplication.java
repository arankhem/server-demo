package com.companydomain.serverdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.companydomain.serverdemo.sender.Server;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(ServerApplication.class, args);
		Server server = applicationContext.getBean(Server.class);
		server.start();
	}

}

