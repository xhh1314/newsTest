
package com.hww.ucenter.webadmin.config;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hww.ucenter.webadmin.json.FastJsonConfigration;

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
