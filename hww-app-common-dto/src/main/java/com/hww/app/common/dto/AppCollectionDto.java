package com.hww.app.common.dto;

import com.hww.base.common.dto.AbsBaseDto;
import com.hww.base.common.entity.AbsBaseEntity;

import java.sql.Timestamp;



public class AppCollectionDto   extends AbsBaseDto<Long> {
	private Long collectionId;//收藏表id
	private Long contentId;//新闻id
	private Short collectionValue;//收藏状态 0 未收藏  1收藏
	private Short plateType;//0  新闻  1 帖子 2 回复
	private Timestamp createTime;//创建时间
	
	
	
	public Long getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(Long collectionId) {
		this.collectionId = collectionId;
	}
	public Long getContentId() {
		return contentId;
	}
	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}
	public Short getCollectionValue() {
		return collectionValue;
	}
	public void setCollectionValue(Short collectionValue) {
		this.collectionValue = collectionValue;
	}
	public Short getPlateType() {
		return plateType;
	}
	public void setPlateType(Short plateType) {
		this.plateType = plateType;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	
	
	

}