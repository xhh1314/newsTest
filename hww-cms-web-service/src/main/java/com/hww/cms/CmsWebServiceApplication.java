package com.hww.cms;

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

import com.hww.cms.webservice.config.AppCxtUtil;
import com.hww.cms.webservice.service.impl.CmsContentServiceImpl;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableSwagger2
@EnableCaching
@EnableAsync
@EnableScheduling
@ComponentScan({"com.hww.app.api","com.hww.file.api","com.hww.els.api","com.hww.sns.api","com.hww.cms"})
@EnableFeignClients(basePackages = {"com.hww.app.api","com.hww.file.api","com.hww.els.api","com.hww.sns.api","com.hww.cms"})
public class CmsWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CmsWebServiceApplication.class);
        ConfigurableApplicationContext ctx= app.run(args);
        AppCxtUtil.setCtx(ctx);
//        CmsContentServiceImpl cmsContentService= (CmsContentServiceImpl) AppCxtUtil.getCtx().getBean("cmsContentService");
//        System.out.println(cmsContentService);
    }
}
