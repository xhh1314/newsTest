package com.hww.cms.common.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class HCmsSpecialListVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long specialId;
    private Long parentId;//父类专题id
	private String specialName;// 专题名称
	private String keywords; // 关键词
	private String summary; // 摘要
	private String logo;
	private String logoUrl;//logo---转为url 逗号分隔
	private Integer auditStatus;//审核状态
	private String creator; // 发布人
	private Integer sortNum;// 排序号
	private Integer siteId;// 站点id
	private Timestamp createTime;
	private String categoryIds;
	private Short status; // 状态 0 禁用 1 可用
	private String specialUrl;
	
	private List<CmsContentVo> cmsContentVoList=new ArrayList<CmsContentVo>();
	
	

	public Long getSpecialId() {
		return specialId;
	}

	public void setSpecialId(Long specialId) {
		this.specialId = specialId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
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

	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getSpecialUrl() {
		return specialUrl;
	}

	public void setSpecialUrl(String specialUrl) {
		this.specialUrl = specialUrl;
	}

	public List<CmsContentVo> getCmsContentVoList() {
		return cmsContentVoList;
	}

	public void setCmsContentVoList(List<CmsContentVo> cmsContentVoList) {
		this.cmsContentVoList = cmsContentVoList;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	
}
