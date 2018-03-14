package com.hww.cms.common.dto;

import java.io.Serializable;

public class HJPushDataDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4649260962932560495L;
	private Long newsID;
	private String newsTitle;
	private Integer newsType;//1新闻 2 专题
	private String newsUrl;
	
	private String alertContent;//alert
	
	public Long getNewsID() {
		return newsID;
	}
	public HJPushDataDto setNewsID(Long newsID) {
		this.newsID = newsID;
		return this;
	}
	public String getNewsTitle() {
		return newsTitle;
	}
	public HJPushDataDto setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
		return this;
	}
	public Integer getNewsType() {
		return newsType;
	}
	public HJPushDataDto setNewsType(Integer newsType) {
		this.newsType = newsType;
		return this;
	}
	public String getNewsUrl() {
		return newsUrl;
	}
	public HJPushDataDto setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
		return this;
	}
	public String getAlertContent() {
		return alertContent;
	}
	public HJPushDataDto setAlertContent(String alertContent) {
		this.alertContent = alertContent;
		return this;
	}
	
	
}
