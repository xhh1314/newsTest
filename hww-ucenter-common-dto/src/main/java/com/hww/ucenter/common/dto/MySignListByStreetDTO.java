package com.hww.ucenter.common.dto;

import com.hww.base.common.dto.AbsBaseDto;

public class MySignListByStreetDTO extends AbsBaseDto<Integer> {

    private Integer memberId;

    private String oneMonthDate;

    private String currentDate;

  public Integer getMemberId() {
    return memberId;
  }

  public void setMemberId(Integer memberId) {
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
