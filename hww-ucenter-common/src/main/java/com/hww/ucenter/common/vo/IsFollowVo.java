package com.hww.ucenter.common.vo;

import javax.persistence.Entity;
import com.hww.base.common.vo.AbsBaseVo;
public class IsFollowVo extends AbsBaseVo{

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
    
    public Integer getFollowId() {
        return followId;
    }
    public void setFollowId(Integer concernId) {
        this.followId = concernId;
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
    
    
}
