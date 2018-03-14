package com.hww.sys.webservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hww.base.util.Md5PwdEncoder;
import com.hww.base.util.PwdEncoder;
import com.hww.framework.web.mvc.RealPathResolver;
import com.hww.framework.web.mvc.ServletContextRealPathResolver;
import com.hww.framework.web.session.HttpSessionProvider;
import com.hww.framework.web.session.SessionProvider;


@Configuration
public class SysConfig
	extends
		WebMvcConfigurerAdapter {
	// HttpSession
	@Bean
	public SessionProvider sessionProvider() {
		return new HttpSessionProvider();
	}

	// md5
	@Bean
	public PwdEncoder pwdEncoder() {
		return new Md5PwdEncoder();
	}

	@Bean
	public RealPathResolver realPathResolver() {
		return new ServletContextRealPathResolver();
	}


}
