package com.hww.file.webservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CustomWebMvcConfiguration extends WebMvcConfigurerAdapter {

	@Value("${video.location}")
	String video;
	@Value("${img.location}")
	String img;
	@Value("${info.location}")
	String app;
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("file:"+img);
        registry.addResourceHandler("/video/**").addResourceLocations("file:"+video);
        registry.addResourceHandler("/app/**").addResourceLocations("file:"+app);
        //registry.addResourceHandler("/**").addResourceLocations("classpath:/view/");
        super.addResourceHandlers(registry);
    }

  

}
