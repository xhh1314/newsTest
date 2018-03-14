package com.hww.sys.webadmin.config;

import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.hww.base.util.PwdEncoder;
import com.hww.framework.web.WebEhCacheManagerFacotryBean;

@Configuration
public class CacheConfig {
	@Bean
	public WebEhCacheManagerFacotryBean cacheManager() {
		WebEhCacheManagerFacotryBean ret = new WebEhCacheManagerFacotryBean();
		ret.setdiskStoreLocation(new ClassPathResource("/WEB-INF/cache"));
		return ret;
	}
	
	public static void main(String[] args) {
	}
}
