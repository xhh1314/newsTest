package com.hww.app.common.dto;


import java.io.Serializable;
public class AppBehaviorDto   implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long memberId;//会员id
	private Long contentId;//cmsConent/topic/post  id
	private String uuid;//设备imei号
	private Integer bevType;// 0 详情查看， 1点赞 ，2 收藏，3评论，4爆料，5 不感兴趣(不感兴趣，内容太水，看过了)
	private String keywords;//不感兴趣新闻关键词
	private String uninstresTypes;//不感兴趣类型
	private Integer bevValue;//1 点赞收藏等，0 取消点赞收藏等
	private Integer plateType;// 0  新闻  1 帖子/新鲜事  2 评论
	private String bevValueStr;
	
	//0 详情查看， 1点赞 ，2 收藏，3评论，4爆料，5不感兴趣（ 不感兴趣，内容太水，看过了，）
	 
		public static final class BevType{
			public static final int b0_xq=0;
			public static final int b1_dz=1; 
			public static final int b2_sc=2; 
			public static final int b3_pl=3; 
			public static final int b4_bl=4; 
			public static final int b5_gxq=5; //5不感兴趣（ 不感兴趣，内容太水，看过了，）
			
//			public static final int b6_nrts=6; 
//			public static final int b7_kgl=7; 
//			public static final int b8_fx=8; 

		}
		public static final class PlatType{
			public static final int b0_news=0;
			public static final int b1_topic=1; 
			public static final int b2_post=2; 
		}
		
		public static final class BevValue{
			public static final int b1_ok=1;
			public static final int b0_cancel=0; 
		}
	public AppBehaviorDto() {
		super();
	}
	
	public AppBehaviorDto( Long memberId, Long contentId, Integer bevType) {
		super();
		this.memberId = memberId;
		this.contentId = contentId;
		this.bevType = bevType;
	}
	
	
	public AppBehaviorDto( Long memberId, Long contentId, Integer bevType,
			Integer bevValue, Integer plateType) {
		super();
		this.memberId = memberId;
		this.contentId = contentId;
		this.bevType = bevType;
		this.bevValue = bevValue;
		this.plateType = plateType;
	}
	public AppBehaviorDto( Long memberId, Long contentId, Integer bevType,
			String keywords, Integer bevValue, Integer plateType) {
		super();
		this.memberId = memberId;
		this.contentId = contentId;
		this.bevType = bevType;
		this.keywords = keywords;
		this.bevValue = bevValue;
		this.plateType = plateType;
	}
	public AppBehaviorDto(Long memberId, Long contentId, String uuid, Integer bevType,
			String keywords, Integer bevValue, Integer plateType) {
		super();
		this.memberId = memberId;
		this.contentId = contentId;
		this.uuid = uuid;
		this.bevType = bevType;
		this.keywords = keywords;
		this.bevValue = bevValue;
		this.plateType = plateType;
	}

	public Long getMemberId() {
		return memberId;
	}
	public AppBehaviorDto setMemberId(Long memberId) {
		this.memberId = memberId;
		return this;
	}
	public Long getContentId() {
		return contentId;
	}
	public AppBehaviorDto setContentId(Long contentId) {
		this.contentId = contentId;
		return this;
	}
	public String getUuid() {
		return uuid;
	}
	public AppBehaviorDto setUuid(String uuid) {
		this.uuid = uuid;
		return this;
	}
	public Integer getBevType() {
		return bevType;
	}
	public AppBehaviorDto setBevType(Integer bevType) {
		this.bevType = bevType;
		return this;
	}
	public String getKeywords() {
		return keywords;
	}
	public AppBehaviorDto setKeywords(String keywords) {
		this.keywords = keywords;
		return this;
	}
	public Integer getBevValue() {
		return bevValue;
	}
	public AppBehaviorDto setBevValue(Integer bevValue) {
		this.bevValue = bevValue;
		return this;
	}
	public Integer getPlateType() {
		return plateType;
	}
	public AppBehaviorDto setPlateType(Integer plateType) {
		this.plateType = plateType;
		return this;
	}

	public String getBevValueStr() {
		return bevValueStr;
	}

	public AppBehaviorDto setBevValueStr(String bevValueStr) {
		this.bevValueStr = bevValueStr;
		return this;
	}

	public String getUninstresTypes() {
		return uninstresTypes;
	}

	public AppBehaviorDto setUninstresTypes(String uninstresTypes) {
		this.uninstresTypes = uninstresTypes;
		return this;
	}
	
	

	
	
	
	
}