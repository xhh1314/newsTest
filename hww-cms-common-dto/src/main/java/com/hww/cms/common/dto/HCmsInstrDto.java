package com.hww.cms.common.dto;


import java.io.Serializable;


public class HCmsInstrDto  implements Serializable{
 
	private static final long serialVersionUID = 1L;
	
	protected Long memberId;// 当前登录用户id
	protected String imei;// 
	protected Double longitude;// 经度
	protected Double latitude;// 纬度
	private Long contentId; // 新闻id
	//5 感兴趣，6内容太水，7看过了
	private Integer uninterestType;
	//不感兴趣关键词
	private String uninterestWords;
	
	
    public HCmsInstrDto() {
		super();
	}

	public Double getLongitude() {
		return longitude;
	}
	public HCmsInstrDto setLongitude(Double longitude) {
		this.longitude = longitude;
		return this;
	}
	public Double getLatitude() {
		return latitude;
	}
	public HCmsInstrDto setLatitude(Double latitude) {
		this.latitude = latitude;
		return this;
	}


	public String getImei() {
		return imei;
	}
	public HCmsInstrDto setImei(String imei) {
		this.imei = imei;
		return this;
	}


	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getContentId() {
		return contentId;
	}

	public HCmsInstrDto setContentId(Long contentId) {
		this.contentId = contentId;
		return this;
	}

	public Integer getUninterestType() {
		return uninterestType;
	}

	public HCmsInstrDto setUninterestType(Integer uninterestType) {
		this.uninterestType = uninterestType;
		return this;
	}

	public String getUninterestWords() {
		return uninterestWords;
	}

	public void setUninterestWords(String uninterestWords) {
		this.uninterestWords = uninterestWords;
	}
	

}
