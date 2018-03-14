package com.hww.ucenter.common.vo;

import com.hww.base.common.vo.AbsBaseVo;

/**
 * 签到表
 *
 * @author yx
 * @email 798823035@qq.com
 * @date 2017年11月4日 上午11:30:55
 * @version v0.1
 */
public class SignVo extends AbsBaseVo {

  private Long signId;
  private String latitude;
  private String longitude;
  private String accuracy;
  private String address;
  private String country;
  private String province;
  private String city;
  private String street;
  private String cityCode;
  private Long memberId;
  private Integer isSign;
  private Double distance;
  private Integer cu;

  public Long getSignId() {
    return signId;
  }

  public void setSignId(Long signId) {
    this.signId = signId;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getAccuracy() {
    return accuracy;
  }

  public void setAccuracy(String accuracy) {
    this.accuracy = accuracy;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCityCode() {
    return cityCode;
  }

  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  public Long getMemberId() {
    return memberId;
  }

  public void setMemberId(Long memberId) {
    this.memberId = memberId;
  }

  public Integer getIsSign() {
    return isSign;
  }

  public void setIsSign(Integer isSign) {
    this.isSign = isSign;
  }

  public Double getDistance() {
    return distance;
  }

  public void setDistance(Double distance) {
    this.distance = distance;
  }

  public Integer getCu() {
    return cu;
  }

  public void setCu(Integer cu) {
    this.cu = cu;
  }


}
