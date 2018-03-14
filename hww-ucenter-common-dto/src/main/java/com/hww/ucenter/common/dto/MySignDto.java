package com.hww.ucenter.common.dto;

import com.hww.base.common.dto.AbsBaseDto;

public class MySignDto extends AbsBaseDto<Integer> {

  Long memberId;

  String oneMonthDate;

  String currentDate;

  public Long getMemberId() {
    return memberId;
  }

  public void setMemberId(Long memberId) {
    this.memberId = memberId;
  }


  public String getOneMonthDate() {
    return oneMonthDate;
  }

  public void setOneMonthDate(String oneMonthDate) {
    this.oneMonthDate = oneMonthDate;
  }

  public String getCurrentDate() {
    return currentDate;
  }

  public void setCurrentDate(String currentDate) {
    this.currentDate = currentDate;
  }

}
