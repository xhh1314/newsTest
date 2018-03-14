package com.hww.ucenter.common.dto;

import java.sql.Timestamp;
import org.hibernate.validator.constraints.NotBlank;
import com.hww.base.common.dto.AbsBaseDto;

public class LoginUcenterMemberDto extends
AbsBaseDto<Integer>{

    private Integer memberId;
    @NotBlank(message = "登录帐号不能为空")
    private String memberName;// 登录账号
    @NotBlank(message = "登录密码不能为空")
    private String password;
    private String realName;// 真名
    private String pseudonym;// 笔名
    private String email;
    private String phoneNo;// 手机号码
    private Timestamp lastLoginTime;
    private Integer loginCount;
    private String registerIp;
    private String lastLoginIp;
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
    private String address;
    public Integer getMemberId() {
        return memberId;
    }
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
    public String getMemberName() {
        return memberName;
    }
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
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
    public String getLastLoginIp() {
        return lastLoginIp;
    }
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    
    
}
