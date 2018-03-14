package com.hww.cms.common.vo;

import java.sql.Timestamp;

import com.hww.base.common.vo.BaseTreeVo;

public class CmsContentPushVo extends BaseTreeVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long ContentId;
	private Timestamp pushTime;
	private Timestamp createTime;
	private Short status;
	private Integer pushResults;
	private String creator;
	private String pushAlert;
	private String pushNewsTitle;
	private Integer pushNewsType;
	private String pushNewsUrl;
	public Long getContentId() {
		return ContentId;
	}
	public void setContentId(Long contentId) {
		ContentId = contentId;
	}
	public Timestamp getPushTime() {
		return pushTime;
	}
	public void setPushTime(Timestamp pushTime) {
		this.pushTime = pushTime;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Integer getPushResults() {
		return pushResults;
	}
	public void setPushResults(Integer pushResults) {
		this.pushResults = pushResults;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getPushAlert() {
		return pushAlert;
	}
	public void setPushAlert(String pushAlert) {
		this.pushAlert = pushAlert;
	}
	public String getPushNewsTitle() {
		return pushNewsTitle;
	}
	public void setPushNewsTitle(String pushNewsTitle) {
		this.pushNewsTitle = pushNewsTitle;
	}
	public Integer getPushNewsType() {
		return pushNewsType;
	}
	public void setPushNewsType(Integer pushNewsType) {
		this.pushNewsType = pushNewsType;
	}
	public String getPushNewsUrl() {
		return pushNewsUrl;
	}
	public void setPushNewsUrl(String pushNewsUrl) {
		this.pushNewsUrl = pushNewsUrl;
	}
	
	
	
}
