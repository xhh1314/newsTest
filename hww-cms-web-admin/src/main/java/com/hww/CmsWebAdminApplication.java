package com.hww;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableEurekaClient
@EnableCircuitBreaker
@ServletComponentScan
@SpringBootApplication
@EnableSwagger2
@ComponentScan({"com.hww.cms.api","com.hww.file.api","com.hww.els.api","com.hww.sys.api","com.hww.cms"})
@EnableFeignClients(basePackages = {"com.hww.cms.api","com.hww.file.api","com.hww.els.api","com.hww.sys.api","com.hww.cms"})
public class CmsWebAdminApplication {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication app = new SpringApplication(CmsWebAdminApplication.class);
		app.run(args);
	}
	/**
	 * 修改DispatcherServlet默认配置
	 *
	 * @param dispatcherServlet
	 * @return
	 */
	/*@Bean
	public ServletRegistrationBean dispatcherRegistration(
			DispatcherServlet dispatcherServlet) {
		ServletRegistrationBean registration = new ServletRegistrationBean(
				dispatcherServlet);
		registration.getUrlMappings().clear();
		registration.addUrlMappings("*.do");
		registration.addUrlMappings("*.html");
		// registration.addUrlMappings("*.json");
		return registration;

	}
*/
	@Bean(name = "multipartResolver")
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		resolver.setResolveLazily(true);// resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
		resolver.setMaxInMemorySize(40960);
		resolver.setMaxUploadSize(200 * 1024 * 1024);// 上传文件大小 50M 50*1024*1024
		return resolver;
	}



}
