package com.hww.base.common.dto;

import java.util.List;

/**
 * 
 * 内容化接口
 */
public interface IContentable {

	public String asCmsTitle();// 标题

	public String asCmsShortTitle();// 短标题

	public String asCmsDescription();// 摘要
	
	public List<String> asCmsThumbs();//缩略图地址

	public String asCmsContent();// 正文 *
	
	public String asCmsAuthor();//原文作者*

	public String orginSysSub();//来源syssub*
	
	public String orginModule();//来源模块*
	
	public String orginId();//来源id*

}
