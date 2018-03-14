package com.hww.cms.webadmin.config;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hww.base.util.Md5PwdEncoder;
import com.hww.base.util.PwdEncoder;
import com.hww.cms.webadmin.web.AdminContextInterceptor;
import com.hww.framework.web.mvc.RealPathResolver;
import com.hww.framework.web.mvc.ServletContextRealPathResolver;
import com.hww.framework.web.session.HttpSessionProvider;
import com.hww.framework.web.session.SessionProvider;

@Configuration
public class CmsConfig
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

	@Bean
	public AdminContextInterceptor adminContextInterceptor() {
		AdminContextInterceptor a = new AdminContextInterceptor();
		String[] excludeUrls = { "/login.do","/cas/ssologin.do", "/logout.do", "/captcha.svl","/ueditor1.4.3" };
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
	
	@Bean
    @ConditionalOnMissingBean({RestOperations.class, RestTemplate.class})
    public RestOperations restOperations() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(5000);
        requestFactory.setConnectTimeout(5000);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        // 使用 utf-8 编码集的 conver 替换默认的 conver（默认的 string conver 的编码集为 "ISO-8859-1"）
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
        while (iterator.hasNext()) {
            HttpMessageConverter<?> converter = iterator.next();
            if (converter instanceof StringHttpMessageConverter) {
                iterator.remove();
            }
        }
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

        return restTemplate;
    }
}
