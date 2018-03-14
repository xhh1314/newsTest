package com.hww.app.common.dto;

import com.hww.base.common.dto.AbsBaseDto;
import com.hww.base.common.entity.AbsBaseEntity;


public class AppBehaviorCountDto   extends AbsBaseDto<Long> {
	
	
	private Long id;
	private Long contentId;
	private Integer bevCount;
	private Integer bevType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getContentId() {
		return contentId;
	}
	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}
	public Integer getBevCount() {
		return bevCount;
	}
	public void setBevCount(Integer bevCount) {
		this.bevCount = bevCount;
	}
	public Integer getBevType() {
		return bevType;
	}
	public void setType(Integer bevType) {
		this.bevType = bevType;
	}
	
	public void setBevType(Integer bevType) {
		this.bevType = bevType;
	}
	

}