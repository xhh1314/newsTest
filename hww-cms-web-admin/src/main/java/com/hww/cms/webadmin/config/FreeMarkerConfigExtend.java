package com.hww.cms.webadmin.config;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.jagregory.shiro.freemarker.ShiroTags;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
@org.springframework.context.annotation.Configuration
public class FreeMarkerConfigExtend {//extends FreeMarkerConfigurer {
	 @Autowired
	    freemarker.template.Configuration configuration;
	   @PostConstruct
	    public void setSharedVariable(){
	        configuration.setSharedVariable("shiro", new ShiroTags());
	    }
	 
//	@Override  
//	public void afterPropertiesSet() throws IOException, TemplateException {  
//        super.afterPropertiesSet();
//        Configuration cfg = this.getConfiguration();
//        
//        cfg.setSharedVariable("shiro", new ShiroTags());//shiro标签
//        cfg.setNumberFormat("#");//防止页面输出数字,变成2,000
//        //可以添加很多自己的要传输到页面的[方法、对象、值]
//    }  
}
