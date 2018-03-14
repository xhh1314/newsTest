package com.hww.cms.common.vo;

import java.sql.Timestamp;

import com.hww.base.common.vo.AbsBaseVo;

/**
 * 专题内容vo
 */
public class CmsSpecialRCategoryVo extends AbsBaseVo {

	private static final long serialVersionUID = 1L;
	private Long rId;//中间表id
	private Long specialId;//专题id
	private Long categoryId;//关联新闻类别id
	private Timestamp createTime;// 
	private Integer siteId;//
	
	
	
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
	public Long getrId() {
		return rId;
	}
	public void setrId(Long rId) {
		this.rId = rId;
	}
	public Long getSpecialId() {
		return specialId;
	}
	public void setSpecialId(Long specialId) {
		this.specialId = specialId;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	
	
	

}