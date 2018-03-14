package com.hww.ucenter.common.vo;

import java.io.Serializable;

import com.hww.base.common.vo.AbsBaseVo;
@SuppressWarnings("serial")
public class FollowVo implements Serializable{
	private Integer followId;
    private Long memberId;
    private Long tomemberId;
    private String nickName;
    private String avatar;
    private String sex;
    private String nowLon;
    private String nowLat;
    private Double distance;
    private Integer concernType;
    private String address;
    private Double longitude;//经度
    private Double latitude;//纬度
    
    private Integer pageSize;
    private Integer pageNo;
    
    public Integer getFollowId() {
        return followId;
    }
    public void setFollowId(Integer followId) {
        this.followId = followId;
    }
    public Long getMemberId() {
        return memberId;
    }
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
    public Long getTomemberId() {
        return tomemberId;
    }
    public void setTomemberId(Long tomemberId) {
        this.tomemberId = tomemberId;
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
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
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
    public Double getDistance() {
        return distance;
    }
    public void setDistance(Double distance) {
        this.distance = distance;
    }
    public Integer getConcernType() {
        return concernType;
    }
    public void setConcernType(Integer concernType) {
        this.concernType = concernType;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
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
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
    
    
}
