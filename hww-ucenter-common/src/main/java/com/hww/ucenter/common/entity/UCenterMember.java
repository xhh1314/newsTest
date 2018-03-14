package com.hww.ucenter.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 前端会员实体类
 */
@Entity
@Table(name = "ucenter_member")
public class UCenterMember extends AbsBaseEntity<Long> {

    private static final long serialVersionUID = 6320126208542022941L;
    private Long memberId;
    private String memberName;
    private String email;
    private String password;
    private String lastLoginIp;
    private Timestamp lastLoginTime;
    private Integer loginCount;
    private String registerIp;
    /**
     * 手机号
     */
    private String phoneNo;
    private Long countryId;
    private Long provinceId;
    private Long cityId;
    private String countryName;
    private String provinceName;
    private String cityName;
    private String lon;
    private String lat;
    private String nowLon;
    private String nowLat;
    private String sex;
    private String nickName;
    /**
     * 头像
     */
    private String avatar;
    private Long avatarId;
    private Short status;
    private String address;
    /**
     * 签名
     */
    private String signature;

    /**
     * 第三方平台登录时，标识用户的uuid
     */
    private String weChatNo;
    private String qqNo;
    private String weiBoNo;
    private String qqUuid;
    private String weChatUuid;
    private String weiBoUuid;
    private Integer snsDisabled;

    @Id
    @Column(name = "member_id")
    @GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Column(name = "member_name")
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "last_login_ip")
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    @Column(name = "last_login_time")
    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Column(name = "login_count")
    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    @Column(name = "register_ip")
    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    @Column(name = "phone_no")
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Column(name = "country_id")
    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    @Column(name = "province_id")
    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    @Column(name = "city_id")
    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    @Column(name = "country_name")
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Column(name = "province_name")
    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Column(name = "city_name")
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Column(name = "lon")
    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @Column(name = "lat")
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Column(name = "now_lon")
    public String getNowLon() {
        return nowLon;
    }

    public void setNowLon(String nowLon) {
        this.nowLon = nowLon;
    }

    @Column(name = "now_lat")
    public String getNowLat() {
        return nowLat;
    }

    public void setNowLat(String nowLat) {
        this.nowLat = nowLat;
    }

    @Column(name = "sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "nick_name")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Column(name = "avatar")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Column(name = "we_chat_no")
    public String getWeChatNo() {
        return weChatNo;
    }

    public void setWeChatNo(String weChatNo) {
        this.weChatNo = weChatNo;
    }

    @Column(name = "qq_no")
    public String getQqNo() {
        return qqNo;
    }

    public void setQqNo(String qqNo) {
        this.qqNo = qqNo;
    }

    @Column(name = "wei_bo_no")
    public String getWeiBoNo() {
        return weiBoNo;
    }

    public void setWeiBoNo(String weiBoNo) {
        this.weiBoNo = weiBoNo;
    }

    @Column(name = "status")
    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "signature")
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Column(name = "qq_uuid")
    public String getQqUuid() {
        return qqUuid;
    }

    public void setQqUuid(String qqUuid) {
        this.qqUuid = qqUuid;
    }

    @Column(name = "we_chat_uuid")
    public String getWeChatUuid() {
        return weChatUuid;
    }

    public void setWeChatUuid(String weChatUuid) {
        this.weChatUuid = weChatUuid;
    }

    @Column(name = "wei_bo_uuid")
    public String getWeiBoUuid() {
        return weiBoUuid;
    }

    public void setWeiBoUuid(String weiBoUuid) {
        this.weiBoUuid = weiBoUuid;
    }

    @Override
    @Transient
    public Long getId() {
        return memberId;
    }

    @Column(name = "avatar_id")
    public Long getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Long avatarId) {
        this.avatarId = avatarId;
    }
    @Column(name = "sns_disabled")
    public Integer getSnsDisabled() {
		return snsDisabled;
	}

	public void setSnsDisabled(Integer snsDisabled) {
		this.snsDisabled = snsDisabled;
	}
    
    
    
}
