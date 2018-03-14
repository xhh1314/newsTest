package com.hww;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.DispatcherServlet;

@EnableCaching
@EnableEurekaClient
@EnableCircuitBreaker
@SpringBootApplication
@EnableScheduling
@EnableAsync
@ComponentScan({"com.hww.cms.api","com.hww.ucenter.api","com.hww.file.api","com.hww.els.api","com.hww.sys.api","com.hww.sns"})
@EnableFeignClients(basePackages = {"com.hww.cms.api","com.hww.ucenter.api","com.hww.file.api","com.hww.els.api","com.hww.sys.api","com.hww.sns"})

public class SnsWebAdminApplication {

	
	/*@Bean
	public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
		registration.getUrlMappings().clear();
		registration.addUrlMappings("*.do");
		registration.addUrlMappings("*.html");
		// registration.addUrlMappings("*.json");
		return registration;
	}*/
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SnsWebAdminApplication.class);
		app.run(args);
	}
}
