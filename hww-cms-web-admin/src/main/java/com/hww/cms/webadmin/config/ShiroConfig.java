package com.hww.cms.webadmin.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.cas.CasSubjectFactory;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.hww.cms.webadmin.security.MyShiroFilterFactoryBean;
import com.hww.cms.webadmin.security.UserRealm;


@Configuration
@ImportResource("classpath:config/shiro-context.xml")
public class ShiroConfig {
	
    private static final String CAS_FILTER = "casFilter";

    @Autowired
	CasConfig casConfig;

	@Bean
	public EhCacheManager ehcacheManager(){
		EhCacheManager ehcacheManager = new EhCacheManager();
		ehcacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
		return ehcacheManager;
	}

	@Bean
	public UserRealm myShiroRealm(EhCacheManager ehCacheManager){
		UserRealm realm = new UserRealm();
		realm.setCacheManager(ehCacheManager);
		realm.setDefaultRoles("user");
		realm.setCasServerUrlPrefix(casConfig.getCasServerUrlPrefix());
		realm.setCasService(casConfig.getCasService());
		realm.setCachingEnabled(true);
		realm.setAuthenticationCachingEnabled(true);
		return realm;
	}

	/**
	 * 注册shiroFilter
	 */
	@Bean
	public FilterRegistrationBean filterRegistrationBean(){
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
		filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
		// 该值缺省为false，表示生命周期有SpringApplicationContext管理，设置为true则表示由ServletContainer管理
		filterRegistration.addInitParameter("targetFilterLifecycle", "true");
		filterRegistration.setEnabled(true);
		filterRegistration.addUrlPatterns("/*");
		return filterRegistration;
	}

	@Bean(name = "lifecycleBeanPostProcessor")
	public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		return creator;
	}

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager defaultWebSecurityManager(UserRealm realm){
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(realm);
		securityManager.setCacheManager(ehcacheManager());
		// 指定SubjectFactory
		securityManager.setSubjectFactory(new CasSubjectFactory());
		return securityManager;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
		ShiroFilterFactoryBean factoryBean = new MyShiroFilterFactoryBean();
		factoryBean.setSecurityManager(securityManager);
		factoryBean.setLoginUrl(casConfig.getLocalServerLoginUrl());
		factoryBean.setSuccessUrl("/index.do");
		factoryBean.setUnauthorizedUrl("/403");
		// 添加casFilter到shiroFilter中
		Map<String, Filter> filterMap = new HashMap<String, Filter>(1);
		filterMap.put(CAS_FILTER, casFilter());
        factoryBean.setFilters(filterMap);
        
		loadShiroFilterChain(factoryBean);
		return factoryBean;
	}

	/**
	 * 加载ShiroFilter权限控制规则
	 */
	private void loadShiroFilterChain(ShiroFilterFactoryBean factoryBean) {
		/**下面这些规则配置最好配置到配置文件中*/
		Map<String, String> filterChainMap = new LinkedHashMap<String, String>();
		filterChainMap.put(casConfig.getCasFilterUrlPattern(), CAS_FILTER);//shiro集成cas后，首先添加该规则
		//filterChainMap.put("/login.do", CAS_FILTER);
		filterChainMap.put("/captcha.svl", "anon");
		filterChainMap.put("/resources/**", "anon");
		filterChainMap.put("/user", "authc");
		filterChainMap.put("/user/edit/**", "authc,perms[user:edit]");
		//配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		//集成cas不能使用shiro的logout拦截器
		//filterChainMap.put("/logout.do", "logout");
		filterChainMap.put("/**", "anon");
		factoryBean.setFilterChainDefinitionMap(filterChainMap);
	}
	
	/**
	 * CAS过滤器
	 */
	@Bean
	public CasFilter casFilter(){
		CasFilter casFilter = new CasFilter();
		casFilter.setName(CAS_FILTER);
		casFilter.setEnabled(true);
		//casFilter.processPathConfig(path, config)
		casFilter.setFailureUrl(casConfig.getLocalServerLoginUrl());
		return casFilter;
	}
	
//	@Bean  
//    public FilterRegistrationBean casHttpServletRequestWrapperFilter(){  
//        FilterRegistrationBean authenticationFilter = new FilterRegistrationBean();  
//        authenticationFilter.setFilter(new HttpServletRequestWrapperFilter());  
//        authenticationFilter.setOrder(3);  
//        List<String> urlPatterns = new ArrayList<String>();  
//        urlPatterns.add("/*");// 设置匹配的url  
//        authenticationFilter.setUrlPatterns(urlPatterns);  
//        return authenticationFilter;  
//    }  
//      
//    @Bean  
//    public FilterRegistrationBean casAssertionThreadLocalFilter(){  
//        FilterRegistrationBean authenticationFilter = new FilterRegistrationBean();  
//        authenticationFilter.setFilter(new AssertionThreadLocalFilter());  
//        authenticationFilter.setOrder(4);  
//        List<String> urlPatterns = new ArrayList<String>();  
//        urlPatterns.add("/*");// 设置匹配的url  
//        authenticationFilter.setUrlPatterns(urlPatterns);  
//        return authenticationFilter;  
//    }  
	
}
