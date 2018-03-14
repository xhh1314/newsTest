package com.hww.app;

import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableSwagger2
@EnableCaching
@EnableAsync
@EnableScheduling
@ComponentScan({"com.hww.ucenter.api","com.hww.app"})
@EnableFeignClients(basePackages = {"com.hww.ucenter.api","com.hww.app"})
public class AppWebServiceApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AppWebServiceApplication.class);
        ConfigurableApplicationContext ctx= app.run(args);
        Stream.of(ctx.getBeanDefinitionNames()).forEach(val->{System.out.println(val); });
    }
}
