package com.hww.sys.webadmin.web;

import org.jasig.cas.client.authentication.AuthenticationFilter;  
import org.jasig.cas.client.session.SingleSignOutFilter;  
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;  
import org.jasig.cas.client.util.AssertionThreadLocalFilter;  
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.boot.web.servlet.FilterRegistrationBean;  
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;  
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;

import com.hww.sys.webadmin.config.CasConfig;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 过滤器顺序：
1. CAS Single Sign Out Filter
2. CAS Validation Filter
3. CAS Authentication Filter
4. CAS HttpServletRequest Wrapper Filter
5. CAS Assertion Thread Local Filter
 * @author hewei
 *
 */
@Configuration
public class ShiroCasFilter {
	 
	private static final boolean casEnabled = true; 

	@Autowired
    CasConfig casConfig;
	/** 
	   * 用于实现单点登出功能 
	   */
	  @Bean
	  public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener() { 
	    ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> listener = new ServletListenerRegistrationBean<>(); 
	    listener.setEnabled(casEnabled); 
	    listener.setListener(new SingleSignOutHttpSessionListener()); 
	    listener.setOrder(1); 
	    return listener; 
	  }
	  
	  /** 
	   * 该过滤器用于实现单点登出功能，单点退出配置，一定要放在其他filter之前 
	   */
	  @Bean
	  public FilterRegistrationBean singleSignOutFilter() { 
	    FilterRegistrationBean filterRegistration = new FilterRegistrationBean(); 
	    filterRegistration.setFilter(new SingleSignOutFilter()); 
	    filterRegistration.setEnabled(casEnabled);
	    filterRegistration.addUrlPatterns("/cas/ssologout.do"); 
	    filterRegistration.addInitParameter("casServerUrlPrefix", casConfig.getCasServerUrlPrefix()); 
	    filterRegistration.addInitParameter("serverName", casConfig.getServerName()); 
	    filterRegistration.setOrder(3); 
	    return filterRegistration; 
	  }
	  
	  
	    /**  
	     * 该过滤器负责用户的认证工作  
	     */  
	    @Bean  
	    public FilterRegistrationBean authenticationFilter() {  
	        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();  
	        filterRegistration.setFilter(new AuthenticationFilter());  
	        filterRegistration.setEnabled(casEnabled);  
	        filterRegistration.addUrlPatterns("/login.do");  
	        //casServerLoginUrl:cas服务的登陆url  
	        filterRegistration.addInitParameter("casServerLoginUrl", casConfig.getCasServerLoginUrl());  
	        //本项目登录ip+port  
	        filterRegistration.addInitParameter("serverName", casConfig.getServerName());  
	        filterRegistration.addInitParameter("useSession", "true");  
	        filterRegistration.addInitParameter("redirectAfterValidation", "true");  
	        filterRegistration.setOrder(5);  
	        return filterRegistration;  
	    }  
	  /** 
	   * 该过滤器对HttpServletRequest请求包装， 可通过HttpServletRequest的getRemoteUser()方法获得登录用户的登录名 
	   * 
	   */
	  @Bean
	  public FilterRegistrationBean httpServletRequestWrapperFilter() { 
	    FilterRegistrationBean filterRegistration = new FilterRegistrationBean(); 
	    filterRegistration.setFilter(new HttpServletRequestWrapperFilter()); 
	    filterRegistration.setEnabled(true); 
	    filterRegistration.addUrlPatterns("/*"); 
	    filterRegistration.setOrder(6); 
	    return filterRegistration; 
	  } 
	  
	  /** 
	   * 该过滤器使得可以通过org.jasig.cas.client.util.AssertionHolder来获取用户的登录名。 
	   比如AssertionHolder.getAssertion().getPrincipal().getName()。 
	   这个类把Assertion信息放在ThreadLocal变量中，这样应用程序不在web层也能够获取到当前登录信息 
	   */
	  @Bean
	  public FilterRegistrationBean assertionThreadLocalFilter() { 
	    FilterRegistrationBean filterRegistration = new FilterRegistrationBean(); 
	    filterRegistration.setFilter(new AssertionThreadLocalFilter()); 
	    filterRegistration.setEnabled(true); 
	    filterRegistration.addUrlPatterns("/*"); 
	    filterRegistration.setOrder(7); 
	    return filterRegistration; 
	  } 
}
