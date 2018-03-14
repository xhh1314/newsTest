package com.hww.ucenter.common.vo;

import com.hww.base.common.vo.AbsBaseVo;

import java.sql.Timestamp;

public class UCenterMemberVo extends AbsBaseVo {

    private Long memberId;
    private String memberName;
    private String email;
    private String lastLoginIp;
    private Timestamp lastLoginTime;
    private Integer loginCount;
    private String registerIp;
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
    private String realName;
    private String password;
    /**
     * 头像地址
     */
    private String avatar;
    private Long avatarId;


    /**
     * 第三方登录账号时，提供的用户唯一标识符uuid
     */
    private String qqUuid;
    private String weChatUuid;
    private String weiBoUuid;
    private Short status;
    private String address;
    private Integer siteId;
    private String weChatNo;
    private String qqNo;
    private String weiBoNo;
    /**
     * 签名
     */
    private String signature;
    private Integer snsDisabled;
    //查看的用户 是否关注本用户
    private Integer concernType;
 
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getQqUuid() {
        return qqUuid;
    }

    public void setQqUuid(String qqUuid) {
        this.qqUuid = qqUuid;
    }

    public String getWeChatUuid() {
        return weChatUuid;
    }

    public void setWeChatUuid(String weChatUuid) {
        this.weChatUuid = weChatUuid;
    }

    public String getWeiBoUuid() {
        return weiBoUuid;
    }

    public void setWeiBoUuid(String weiBoUuid) {
        this.weiBoUuid = weiBoUuid;
    }

    public Long getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Long avatarId) {
        this.avatarId = avatarId;
    }

	public Integer getConcernType() {
		return concernType;
	}

	public void setConcernType(Integer concernType) {
		this.concernType = concernType;
	}

	public Integer getSnsDisabled() {
		return snsDisabled;
	}

	public void setSnsDisabled(Integer snsDisabled) {
		this.snsDisabled = snsDisabled;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
    
    
}
