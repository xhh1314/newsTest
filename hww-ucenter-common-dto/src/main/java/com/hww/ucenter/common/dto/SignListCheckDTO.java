package com.hww.ucenter.common.dto;

import com.hww.base.common.dto.AbsBaseDto;

public class SignListCheckDTO extends AbsBaseDto<Integer> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  Long memberId;

  String country;

  String province;

  String city;

  String signsStr;

  Double longitude;

  Double latitude;

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Long getMemberId() {
    return memberId;
  }

  public void setMemberId(Long memberId) {
    this.memberId = memberId;
  }

  public String getSignsStr() {
    return signsStr;
  }

  public void setSignsStr(String signsStr) {
    this.signsStr = signsStr;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }



}
