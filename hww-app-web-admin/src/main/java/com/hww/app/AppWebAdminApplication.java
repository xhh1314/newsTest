package com.hww.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.hww.app.admin.config.AppCxtUtil;


@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@ComponentScan({"com.hww.cms.api","com.hww.sys.api","com.hww.app"})
@EnableFeignClients(basePackages = {"com.hww.cms.api","com.hww.sys.api","com.hww.app"})
public class AppWebAdminApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AppWebAdminApplication.class);
        ConfigurableApplicationContext ctx=app.run(args);
        AppCxtUtil.setCtx(ctx);
    }
}
