package com.hww.api.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ApiCenterApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ApiCenterApplication.class);
		app.run(args);
	}
}
