package com.hww.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableSwagger2
public class FileWebServiceApplication {

    /**
     * 文件上传临时路径
     */
//	 @Bean
//	 MultipartConfigElement multipartConfigElement() {
//	    MultipartConfigFactory factory = new MultipartConfigFactory();
//	    String absProjectPath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
//	    factory.setLocation(absProjectPath+"/root");
//	    return factory.createMultipartConfig();
//	}
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(FileWebServiceApplication.class);
        app.run(args);
    }
}
