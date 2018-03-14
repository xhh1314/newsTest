package com.hww.sys;

import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@EnableEurekaClient
@EnableTransactionManagement
@ServletComponentScan
@SpringBootApplication
@ComponentScan({"com.hww.ucenter.api","com.hww.sys"})
@EnableFeignClients(basePackages = {"com.hww.ucenter.api","com.hww.sys"})
public class SysWebAdminApplication extends SpringBootServletInitializer{

	/**
	 * 修改DispatcherServlet默认配置
	 *
	 * @param dispatcherServlet
	 * @return
	 */
	/*@Bean
	public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
		registration.getUrlMappings().clear();
		registration.addUrlMappings("*.html");
		registration.addUrlMappings("*.do");
		registration.addUrlMappings("*.svl");
		// registration.addUrlMappings("*.json");
		return registration;
	}*/
	
	@Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {  
        // TODO Auto-generated method stub  
        builder.sources(this.getClass());  
        return super.configure(builder);  
      
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication app = new SpringApplication(SysWebAdminApplication.class);
		ConfigurableApplicationContext ctx=app.run(args);
	     //Stream.of(ctx.getBeanDefinitionNames()).forEach(val->{System.out.println(val); });
	}
	

}
