package com.hww.cms.common.vo;

import java.sql.Timestamp;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hww.base.common.vo.IBaseVo;

public class CmsUserColumnVo {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Long userColumnId;
  private Long userId;
  private Long columnId;
  private String imei; // 手机设备id
  private Integer sort;
  private String cloumIds;

  public Long getUserColumnId() {
    return userColumnId;
  }

  public void setUserColumnId(Long userColumnId) {
    this.userColumnId = userColumnId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getColumnId() {
    return columnId;
  }

  public void setColumnId(Long columnId) {
    this.columnId = columnId;
  }

  public String getImei() {
    return imei;
  }

  public void setImei(String imei) {
    this.imei = imei;
  }

  public Integer getSort() {
    return sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }

  public String getCloumIds() {
    return cloumIds;
  }

  public void setCloumIds(String cloumIds) {
    this.cloumIds = cloumIds;
  }
  

}
