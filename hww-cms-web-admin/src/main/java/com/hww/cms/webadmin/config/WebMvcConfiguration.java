
package com.hww.cms.webadmin.config;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.hww.cms.webadmin.json.FastJsonConfigration;
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter{
	@Autowired
	FastJsonConfigration fastJsonConfigration;
	


	/**
	 * 采用fastjson 
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	     converters.add(fastJsonConfigration.fastJsonHttpMessageConverter());
	     
	     StringHttpMessageConverter stringConverter= new StringHttpMessageConverter();
	        stringConverter.setDefaultCharset(Charset.forName("UTF-8"));
	        converters.add(stringConverter);
	        
	}
}
