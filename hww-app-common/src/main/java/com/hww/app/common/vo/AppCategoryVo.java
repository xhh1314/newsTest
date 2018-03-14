package com.hww.app.common.vo;

import java.sql.Timestamp;
import java.util.List;

import com.hww.app.common.entity.AppCategory;
import com.hww.base.common.vo.BaseTreeVo;

public class AppCategoryVo extends BaseTreeVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long columnId;
	private Integer specialType;
	private String columnName;
	private Long columnTypeId;
	private String columnDesc;
	private Integer sort;
	private Short status;
	private Integer siteId;
	private Timestamp createTime;
	private Timestamp lastModifyTime;
	private Long parentId; 
	private String logo;
	private Short isDefault;
	private Short isCustom;
	private Integer recommNum;
	private String allIDCheck;
	private String cmsCategoryIds;
	private Integer isDisplay;
	
	
	public Integer getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
	public String getCmsCategoryIds() {
		return cmsCategoryIds;
	}
	public void setCmsCategoryIds(String cmsCategoryIds) {
		this.cmsCategoryIds = cmsCategoryIds;
	}
	public String getAllIDCheck() {
		return allIDCheck;
	}
	public void setAllIDCheck(String allIDCheck) {
		this.allIDCheck = allIDCheck;
	}
	public Long getColumnId() {
		return columnId;
	}
	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}
	public Integer getSpecialType() {
		return specialType;
	}
	public void setSpecialType(Integer specialType) {
		this.specialType = specialType;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Long getColumnTypeId() {
		return columnTypeId;
	}
	public void setColumnTypeId(Long columnTypeId) {
		this.columnTypeId = columnTypeId;
	}
	public String getColumnDesc() {
		return columnDesc;
	}
	public void setColumnDesc(String columnDesc) {
		this.columnDesc = columnDesc;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
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
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public Short getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Short isDefault) {
		this.isDefault = isDefault;
	}
	public Short getIsCustom() {
		return isCustom;
	}
	public void setIsCustom(Short isCustom) {
		this.isCustom = isCustom;
	}
	public Integer getRecommNum() {
		return recommNum;
	}
	public void setRecommNum(Integer recommNum) {
		this.recommNum = recommNum;
	}
	
	
	
	
}
