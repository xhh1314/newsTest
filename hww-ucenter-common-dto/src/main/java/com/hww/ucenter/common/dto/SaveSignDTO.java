package com.hww.ucenter.common.dto;

import com.hww.base.common.dto.AbsBaseDto;

public class SaveSignDTO extends AbsBaseDto<Integer> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  Integer memberId;

  String latitude;

  String longitude;

  String address;

  String country;

  String province;

  String city;

  String street;

  public Integer getMemberId() {
    return memberId;
  }

  public void setMemberId(Integer memberId) {
    this.memberId = memberId;
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


}
