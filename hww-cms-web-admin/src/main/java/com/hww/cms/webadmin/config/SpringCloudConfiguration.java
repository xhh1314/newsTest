package com.hww.cms.webadmin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import feign.Feign;
/**
 * 配置的fallback class也必须在FeignClient Configuration中实例化，否则会报
	java.lang.IllegalStateException: No fallback instance of type class异常。
 * @author hewei
 *
 */
@Configuration
public class SpringCloudConfiguration {
	@Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }

    
}
