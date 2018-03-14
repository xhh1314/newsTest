package com.hww.sys.webadmin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hww.base.util.Md5PwdEncoder;
import com.hww.base.util.PwdEncoder;
import com.hww.framework.web.mvc.RealPathResolver;
import com.hww.framework.web.mvc.ServletContextRealPathResolver;
import com.hww.framework.web.session.HttpSessionProvider;
import com.hww.framework.web.session.SessionProvider;
import com.hww.sys.webadmin.web.AdminContextInterceptor;

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

	//跨域请求
//	@Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedOrigins("*")
//                .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
//                .allowCredentials(false).maxAge(3600);
//    }
	
	@Bean
	public AdminContextInterceptor adminContextInterceptor() {
		AdminContextInterceptor a = new AdminContextInterceptor();
		String[] excludeUrls = { "/login.do","/cas/ssologin.do", "/logout.do", "/captcha.svl" };
		a.setExcludeUrls(excludeUrls);
		a.setLoginUrl("/login.do");
		a.setReturnUrl("/index.do");
		a.setAuth(true);
		return a;
	}

	// 添加拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(adminContextInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);

	}

}
