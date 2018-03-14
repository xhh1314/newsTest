package com.hww.app.common.dto;

import com.hww.base.common.dto.AbsBaseDto;

import java.sql.Timestamp;

public class AppRecommConfigDto  extends AbsBaseDto<Long>  {

    private Long columnId; //栏目id
    private String columnName; //栏目名称
    private Integer recommNum; //推荐数量
    private Integer type; //1 栏目 2 专题 3 新鲜事
    private Short status;
    private Integer siteId;
    private Timestamp createTime;
    private Timestamp lastModifyTime;
	public Long getColumnId() {
		return columnId;
	}
	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Integer getRecommNum() {
		return recommNum;
	}
	public void setRecommNum(Integer recommNum) {
		this.recommNum = recommNum;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

   
}