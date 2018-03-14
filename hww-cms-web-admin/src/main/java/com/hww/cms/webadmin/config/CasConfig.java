package com.hww.cms.webadmin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "cas")
public class CasConfig {
	
	@Value("casServerUrlPrefix")
	// CasServerUrlPrefix
	private String casServerUrlPrefix; //"https://cas.example.org:8443/cas";
	@Value("casServerLoginUrl")
	// Cas登录页面地址
	private String casServerLoginUrl; // = casServerUrlPrefix + "/login";
	@Value("casServerLogoutUrl")
	// Cas登出页面地址
	private String casServerLogoutUrl; // casServerUrlPrefix + "/logout";
	
	@Value("serverName")
	private String serverName; // "https://cas.example.org:8444"
	@Value("localServerUrlPrefix")
	// 当前工程对外提供的服务地址
	private String localServerUrlPrefix; // "https://cas.example.org:8444/admincp";
	@Value("casFilterUrlPattern")
	// casFilter UrlPattern
	// 登录成功地址
	private String casFilterUrlPattern; // "/cas/ssologin.do";
	@Value("localServerLoginUrl")
	// 登录地址
	private String localServerLoginUrl; // casServerLoginUrl + "?service=" + localServerUrlPrefix + casFilterUrlPattern;

	
	public String getCasServerUrlPrefix() {
		return casServerUrlPrefix;
	}

	public void setCasServerUrlPrefix(String casServerUrlPrefix) {
		this.casServerUrlPrefix = casServerUrlPrefix;
	}

	public String getCasServerLoginUrl() {
		return casServerLoginUrl;
	}

	public void setCasServerLoginUrl(String casServerLoginUrl) {
		this.casServerLoginUrl = casServerLoginUrl;
	}

	public String getCasServerLogoutUrl() {
		return casServerLogoutUrl;
	}

	public void setCasServerLogoutUrl(String casServerLogoutUrl) {
		this.casServerLogoutUrl = casServerLogoutUrl;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getLocalServerUrlPrefix() {
		return localServerUrlPrefix;
	}

	public void setLocalServerUrlPrefix(String localServerUrlPrefix) {
		this.localServerUrlPrefix = localServerUrlPrefix;
	}

	public String getCasFilterUrlPattern() {
		return casFilterUrlPattern;
	}

	public void setCasFilterUrlPattern(String casFilterUrlPattern) {
		this.casFilterUrlPattern = casFilterUrlPattern;
	}

	public String getLocalServerLoginUrl() {
		return localServerLoginUrl;
	}

	public void setLocalServerLoginUrl(String localServerLoginUrl) {
		this.localServerLoginUrl = localServerLoginUrl;
	}
	public String getCasService(){
		return localServerUrlPrefix + casFilterUrlPattern;
	}
	
}
