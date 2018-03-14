package com.hww.sns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableAsync
@EnableScheduling
@ComponentScan({"com.hww.cms.api","com.hww.ucenter.api","com.hww.app.api","com.hww.file.api","com.hww.els.api","com.hww.sns"})
@EnableFeignClients(basePackages = {"com.hww.cms.api","com.hww.ucenter.api","com.hww.app.api","com.hww.file.api","com.hww.els.api","com.hww.sns"})
public class SnsWebServiceApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SnsWebServiceApplication.class);
        ConfigurableApplicationContext ctx= app.run(args);
       //Stream.of(ctx.getBeanDefinitionNames()).forEach(val->{System.out.println(val); });
	}
	
}
