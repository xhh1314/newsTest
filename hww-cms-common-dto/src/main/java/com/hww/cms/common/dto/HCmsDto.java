package com.hww.cms.common.dto;


import java.io.Serializable;


public class HCmsDto  implements Serializable{
 
	private static final long serialVersionUID = 1L;
	
	protected Long memberId;// 当前登录用户id
	protected String imei;// 
	protected Double longitude;// 经度
	protected Double latitude;// 纬度
	private Long contentId; // 新闻id
	
    public HCmsDto() {
		super();
	}

	public Double getLongitude() {
		return longitude;
	}
	public HCmsDto setLongitude(Double longitude) {
		this.longitude = longitude;
		return this;
	}
	public Double getLatitude() {
		return latitude;
	}
	public HCmsDto setLatitude(Double latitude) {
		this.latitude = latitude;
		return this;
	}


	public String getImei() {
		return imei;
	}
	public HCmsDto setImei(String imei) {
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

	public HCmsDto setContentId(Long contentId) {
		this.contentId = contentId;
		return this;
	}
	

}
