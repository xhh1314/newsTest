package com.hww.ucenter.webadmin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.hww.framework.web.WebEhCacheManagerFacotryBean;

@Configuration
public class CacheConfig {
	@Bean
	public WebEhCacheManagerFacotryBean cacheManager() {
		WebEhCacheManagerFacotryBean ret = new WebEhCacheManagerFacotryBean();
		ret.setdiskStoreLocation(new ClassPathResource("/WEB-INF/cache"));
		return ret;
	}
}
