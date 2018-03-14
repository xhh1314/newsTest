package com.hww;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCircuitBreaker
@ServletComponentScan
@EnableEurekaClient
@EnableTransactionManagement
@ComponentScan({"com.hww.cms.api","com.hww.sys.api","com.hww.ucenter"})
@EnableFeignClients(basePackages = {"com.hww.cms.api","com.hww.sys.api","com.hww.ucenter"})
public class UCenterWebAdminApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(UCenterWebAdminApplication.class);
		app.run(args);
	}
}
