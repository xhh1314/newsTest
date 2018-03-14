package com.hww.ucenter.common.vo;

import java.sql.Timestamp;
import javax.validation.constraints.NotNull;
import com.hww.base.common.vo.AbsBaseVo;

public class DetailUcenterMemberVo
	extends
		AbsBaseVo {
	// property
    @NotNull(message="用户不能为空")
	private Long memberId;
    private String memberName;
    private String email;
    private String password;
    private String lastLoginIp;
    private Timestamp lastLoginTime;
    private Integer loginCount;
    private String registerIp;
    private String realName;
    private String pseudonym;
    private String phoneNo;
    private Integer countryId;
    private Integer provinceId;
    private Integer cityId;
    private String countryName;
    private String provinceName;
    private String cityName;
    private String lon;
    private String lat;
    private String nowLon;
    private String nowLat;
    private String sex;
    private String nickName;
    private String avatar;
    private String weChatNo;
    private String qqNo;
    private String weiBoNo;
    private Short status;
    private String address;

	// show
	// private String sex;//性别

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

  public String getMemberName() {
    return memberName;
  }

  public void setMemberName(String memberName) {
    this.memberName = memberName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getLastLoginIp() {
    return lastLoginIp;
  }

  public void setLastLoginIp(String lastLoginIp) {
    this.lastLoginIp = lastLoginIp;
  }

  public Timestamp getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(Timestamp lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public Integer getLoginCount() {
    return loginCount;
  }

  public void setLoginCount(Integer loginCount) {
    this.loginCount = loginCount;
  }

  public String getRegisterIp() {
    return registerIp;
  }

  public void setRegisterIp(String registerIp) {
    this.registerIp = registerIp;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getPseudonym() {
    return pseudonym;
  }

  public void setPseudonym(String pseudonym) {
    this.pseudonym = pseudonym;
  }

  public String getPhoneNo() {
    return phoneNo;
  }

  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }

  public Integer getCountryId() {
    return countryId;
  }

  public void setCountryId(Integer countryId) {
    this.countryId = countryId;
  }

  public Integer getProvinceId() {
    return provinceId;
  }

  public void setProvinceId(Integer provinceId) {
    this.provinceId = provinceId;
  }

  public Integer getCityId() {
    return cityId;
  }

  public void setCityId(Integer cityId) {
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

  public String getLon() {
    return lon;
  }

  public void setLon(String lon) {
    this.lon = lon;
  }

  public String getLat() {
    return lat;
  }

  public void setLat(String lat) {
    this.lat = lat;
  }

  public String getNowLon() {
    return nowLon;
  }

  public void setNowLon(String nowLon) {
    this.nowLon = nowLon;
  }

  public String getNowLat() {
    return nowLat;
  }

  public void setNowLat(String nowLat) {
    this.nowLat = nowLat;
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

  public String getWeChatNo() {
    return weChatNo;
  }

  public void setWeChatNo(String weChatNo) {
    this.weChatNo = weChatNo;
  }

  public String getQqNo() {
    return qqNo;
  }

  public void setQqNo(String qqNo) {
    this.qqNo = qqNo;
  }

  public String getWeiBoNo() {
    return weiBoNo;
  }

  public void setWeiBoNo(String weiBoNo) {
    this.weiBoNo = weiBoNo;
  }

  public Short getStatus() {
    return status;
  }

  public void setStatus(Short status) {
    this.status = status;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

	

	
}
