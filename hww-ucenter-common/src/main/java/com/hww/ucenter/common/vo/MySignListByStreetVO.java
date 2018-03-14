package com.hww.ucenter.common.vo;

import com.hww.base.common.vo.AbsBaseVo;

public class MySignListByStreetVO extends AbsBaseVo {

  Integer memberId;
  
  String street;

  Integer signNum;

  String createTime;

  public Integer getMemberId() {
    return memberId;
  }

  public void setMemberId(Integer memberId) {
    this.memberId = memberId;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public Integer getSignNum() {
    return signNum;
  }

  public void setSignNum(Integer signNum) {
    this.signNum = signNum;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

}
