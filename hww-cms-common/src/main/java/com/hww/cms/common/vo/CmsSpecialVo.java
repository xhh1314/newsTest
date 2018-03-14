package com.hww.cms.common.vo;

import java.sql.Timestamp;

import com.hww.base.common.vo.AbsBaseVo;

/**
 * 专题内容vo
 */
public class CmsSpecialVo extends AbsBaseVo {

	private static final long serialVersionUID = 1L;

	private Long specialId;// 专题栏目id
	private Long parentId;// 父类id
	private String specialName;// 专题栏目名称
	private String logo;// 专题封面组图,多图以,分割
	private String summary;// 摘要
	private Integer sortNum;// 排序号
	private String keywords;// 关键词
	private String categoryIds;// 关联新闻id串
	private Timestamp lastModifyTime;// 最后修改时间(如果已审核为发布时间)
	private Timestamp createTime;
	private String creator;
	private Long categoryId;
	private String specialUrl;
	private Integer siteId;
	private Short status;// 状态 0 禁用 1 可用
	private Integer recommPriority;
	private String logoUrls;
	

	public String getLogoUrls() {
		return logoUrls;
	}

	public void setLogoUrls(String logoUrls) {
		this.logoUrls = logoUrls;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getSpecialUrl() {
		return specialUrl;
	}

	public void setSpecialUrl(String specialUrl) {
		this.specialUrl = specialUrl;
	}

	public Integer getRecommPriority() {
		return recommPriority;
	}

	public void setRecommPriority(Integer recommPriority) {
		this.recommPriority = recommPriority;
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

	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

}