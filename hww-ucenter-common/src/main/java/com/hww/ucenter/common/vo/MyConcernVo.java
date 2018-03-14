package com.hww.ucenter.common.vo;

import javax.persistence.Entity;
import com.hww.base.common.vo.AbsBaseVo;
public class MyConcernVo extends AbsBaseVo{

    private Integer concernId;
    private Integer memberId;
    private Integer tomemberId;
    private String nickName;
    private String avatar;
    private String sex;
    private String nowLon;
    private String nowLat;
    private Double distance;
    private String tm;
    
    public Integer getConcernId() {
        return concernId;
    }
    public void setConcernId(Integer concernId) {
        this.concernId = concernId;
    }
    public Integer getMemberId() {
        return memberId;
    }
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
    public Integer getTomemberId() {
        return tomemberId;
    }
    public void setTomemberId(Integer tomemberId) {
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
    public String getTm() {
        return tm;
    }
    public void setTm(String tm) {
        this.tm = tm;
    }
    
    
}
