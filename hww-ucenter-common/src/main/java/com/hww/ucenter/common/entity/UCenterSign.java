package com.hww.ucenter.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 签到表
 *
 * @author yx
 * @email 798823035@qq.com
 * @date 2017年11月4日 上午11:30:55
 * @version v0.1
 */
@Entity
@Table(name = "ucenter_sign")
public class UCenterSign extends AbsBaseEntity<Long> {

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

  @Id
  @GeneratedValue(generator = "snowFlake")
  @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
  @Column(name = "sign_id")
  public Long getSignId() {
    return signId;
  }


  public void setSignId(Long signId) {
    this.signId = signId;
  }


  @Column(name = "latitude")
  public String getLatitude() {
    return latitude;
  }


  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }


  @Column(name = "longitude")
  public String getLongitude() {
    return longitude;
  }


  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }


  @Column(name = "accuracy")
  public String getAccuracy() {
    return accuracy;
  }


  public void setAccuracy(String accuracy) {
    this.accuracy = accuracy;
  }


  @Column(name = "address")
  public String getAddress() {
    return address;
  }


  public void setAddress(String address) {
    this.address = address;
  }


  @Column(name = "country")
  public String getCountry() {
    return country;
  }


  public void setCountry(String country) {
    this.country = country;
  }


  @Column(name = "province")
  public String getProvince() {
    return province;
  }


  public void setProvince(String province) {
    this.province = province;
  }


  @Column(name = "city")
  public String getCity() {
    return city;
  }


  public void setCity(String city) {
    this.city = city;
  }


  @Column(name = "street")
  public String getStreet() {
    return street;
  }


  public void setStreet(String street) {
    this.street = street;
  }


  @Column(name = "cityCode")
  public String getCityCode() {
    return cityCode;
  }


  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  @Column(name = "member_id")
  public Long getMemberId() {
    return memberId;
  }


  public void setMemberId(Long memberId) {
    this.memberId = memberId;
  }

  @Override
  @Transient
  public Long getId() {
    // TODO Auto-generated method stub
    return signId;
  }


  @Override
  public String toString() {
    return "Sign [signId=" + signId + ", latitude=" + latitude + ", longitude=" + longitude
        + ", accuracy=" + accuracy + ", address=" + address + ", country=" + country + ", province="
        + province + ", city=" + city + ", street=" + street + ", cityCode=" + cityCode
        + ", memberId=" + memberId + "]";
  }

}
