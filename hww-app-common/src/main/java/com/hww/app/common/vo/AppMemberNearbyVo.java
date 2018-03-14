package com.hww.app.common.vo;

import com.hww.base.common.vo.AbsBaseVo;

import java.sql.Timestamp;

public class AppMemberNearbyVo extends AbsBaseVo {

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

    public Long getNearbyId() {
        return nearbyId;
    }

    public void setNearbyId(Long nearbyId) {
        this.nearbyId = nearbyId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Timestamp getLocateTime() {
        return locateTime;
    }

    public void setLocateTime(Timestamp locateTime) {
        this.locateTime = locateTime;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Integer getConcernType() {
        return concernType;
    }

    public void setConcernType(Integer concernType) {
        this.concernType = concernType;
    }

    public Boolean getRandom() {
        return random;
    }

    public void setRandom(Boolean random) {
        this.random = random;
    }



    
}
