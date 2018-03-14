package com.hww.ucenter.common.vo;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import com.hww.base.common.dto.AbsBaseDto;

public class SaveSignVo extends AbsBaseDto<Integer> {

  private static final long serialVersionUID = 1L;
  @NotNull(message = "用户不能为空")
  private Long memberId;
  @NotBlank(message = "纬度不能为空")
  private String latitude;
  @NotBlank(message = "经度不能为空")
  private String longitude;
  @NotBlank(message = "地址描述不能为空")
  private String address;

  private String country;

  private String province;

  private String city;

  private String street;

  private Long signId;

  public Long getMemberId() {
    return memberId;
  }

  public void setMemberId(Long memberId) {
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

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public Long getSignId() {
    return signId;
  }

  public void setSignId(Long signId) {
    this.signId = signId;
  }


}
