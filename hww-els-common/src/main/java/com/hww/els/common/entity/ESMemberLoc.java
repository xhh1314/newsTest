package com.hww.els.common.entity;

import java.util.Date;

public class ESMemberLoc {

    private Long memberId;
    private String imei;
	private Double longitude;
	private Double latitude;

	private String geolocation;
	 
    private String address;

    private Date locTime;
    
    private Integer locationType;//1 附近的人 2 签到
    
    private Long locTimeStamp;

	public Long getMemberId() {
		return memberId;
	}

	public ESMemberLoc setMemberId(Long memberId) {
		this.memberId = memberId;
		return this;
	}

	public String getImei() {
		return imei;
	}

	public ESMemberLoc setImei(String imei) {
		this.imei = imei;
		return this;
	}

	public Double getLongitude() {
		return longitude;
	}

	public ESMemberLoc setLongitude(Double longitude) {
		this.longitude = longitude;
		return this;
	}

	public Double getLatitude() {
		return latitude;
	}

	public ESMemberLoc setLatitude(Double latitude) {
		this.latitude = latitude;
		return this;
	}


	public String getGeolocation() {
		return geolocation;
	}

	public void setGeolocation(String geolocation) {
		this.geolocation = geolocation;
	}

	public String getAddress() {
		return address;
	}

	public ESMemberLoc setAddress(String address) {
		this.address = address;
		return this;
	}

	public Date getLocTime() {
		return locTime;
	}

	public ESMemberLoc setLocTime(Date locTime) {
		this.locTime = locTime;
		return this;
	}

	public Integer getLocationType() {
		return locationType;
	}

	public ESMemberLoc setLocationType(Integer locationType) {
		this.locationType = locationType;
		return this;
	}

	public Long getLocTimeStamp() {
		return locTimeStamp;
	}

	public ESMemberLoc setLocTimeStamp(Long locTimeStamp) {
		this.locTimeStamp = locTimeStamp;
		return this;
	}
   
    
   

   
}
