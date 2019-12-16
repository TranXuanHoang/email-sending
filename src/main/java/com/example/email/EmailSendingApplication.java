package com.example.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
//@EnableConfigServer
public class EmailSendingApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailSendingApplication.class, args);
	}
}
