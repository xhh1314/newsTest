package com.hww.app.common.dto;


import java.io.Serializable;
import java.sql.Timestamp;

public class AppMemberNearbyDto  implements Serializable {

    private Long nearbyId;
    private Long memberId;
    private Long countryId;
    private Long provinceId;
    private Long cityId;
    private Long distance;
    private String countryName;
    private String provinceName;
    private String cityName;
    private String longitude;
    private String latitude;
    private String sex;
    private String nickName;
    private String avatar;
    private String address;
    private String signature;
    private Boolean random;
    private Integer concernType;
    private Timestamp locateTime;

    private Integer pageNo;
    private Integer pageSize;
    public Long getNearbyId() {
        return nearbyId;
    }

    public AppMemberNearbyDto setNearbyId(Long nearbyId) {
        this.nearbyId = nearbyId;
		return this;
    }

    public Long getMemberId() {
        return memberId;
    }

    public AppMemberNearbyDto setMemberId(Long memberId) {
        this.memberId = memberId;
		return this;
    }

    public Long getCountryId() {
        return countryId;
    }

    public AppMemberNearbyDto setCountryId(Long countryId) {
        this.countryId = countryId;
		return this;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public AppMemberNearbyDto setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
		return this;
    }

    public Long getCityId() {
        return cityId;
    }

    public AppMemberNearbyDto setCityId(Long cityId) {
        this.cityId = cityId;
		return this;
    }

    public String getCountryName() {
        return countryName;
    }

    public AppMemberNearbyDto setCountryName(String countryName) {
        this.countryName = countryName;
		return this;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public AppMemberNearbyDto setProvinceName(String provinceName) {
        this.provinceName = provinceName;
		return this;
    }

    public String getCityName() {
        return cityName;
    }

    public AppMemberNearbyDto setCityName(String cityName) {
        this.cityName = cityName;
		return this;
    }

    public String getLongitude() {
        return longitude;
    }

    public AppMemberNearbyDto setLongitude(String longitude) {
        this.longitude = longitude;
		return this;
    }

    public String getLatitude() {
        return latitude;
    }

    public AppMemberNearbyDto setLatitude(String latitude) {
        this.latitude = latitude;
		return this;
    }

    public String getSex() {
        return sex;
    }

    public AppMemberNearbyDto setSex(String sex) {
        this.sex = sex;
		return this;
    }

    public String getNickName() {
        return nickName;
    }

    public AppMemberNearbyDto setNickName(String nickName) {
        this.nickName = nickName;
		return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public AppMemberNearbyDto setAvatar(String avatar) {
        this.avatar = avatar;
		return this;
    }

    public String getAddress() {
        return address;
    }

    public AppMemberNearbyDto setAddress(String address) {
        this.address = address;
		return this;
    }

    public String getSignature() {
        return signature;
    }

    public AppMemberNearbyDto setSignature(String signature) {
        this.signature = signature;
		return this;
    }

    public Timestamp getLocateTime() {
        return locateTime;
    }

    public AppMemberNearbyDto setLocateTime(Timestamp locateTime) {
        this.locateTime = locateTime;
		return this;
    }

    public Long getDistance() {
        return distance;
    }

    public AppMemberNearbyDto setDistance(Long distance) {
        this.distance = distance;
		return this;
    }

    public Integer getConcernType() {
        return concernType;
    }

    public AppMemberNearbyDto setConcernType(Integer concernType) {
        this.concernType = concernType;
		return this;
    }

    public Boolean getRandom() {
        return random;
    }

    public AppMemberNearbyDto setRandom(Boolean random) {
        this.random = random;
		return this;
    }

	public Integer getPageNo() {
		return pageNo;
	}

	public AppMemberNearbyDto setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
		return this;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public AppMemberNearbyDto setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	
}
