package com.hww.ucenter.common.dto;

import java.io.Serializable;

public class UserMessageQueryDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long memberId;
    private String isRead;
    private Integer bevType;
    private Integer targetType;
    
    private Integer pageNo;
    private Integer pageSize;
    
 
  
    
    public Integer getPageNo() {
		return pageNo;
	}
	public UserMessageQueryDto setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
		return this;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public UserMessageQueryDto setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		return this;
	}
	public Long getMemberId() {
		return memberId;
	}
	public UserMessageQueryDto setMemberId(Long memberId) {
		this.memberId = memberId;
		return this;
	}
	public String getIsRead() {
		return isRead;
	}
	public UserMessageQueryDto setIsRead(String isRead) {
		this.isRead = isRead;
		return this;
	}
	public Integer getBevType() {
		return bevType;
	}
	public UserMessageQueryDto setBevType(Integer bevType) {
		this.bevType = bevType;
		return this;
	}
	public Integer getTargetType() {
		return targetType;
	}
	public UserMessageQueryDto setTargetType(Integer targetType) {
		this.targetType = targetType;
		return this;
	}
	
	public String tocacheKey() {
		return "_memberId=" + memberId + ", isRead=" + isRead + ", bevType=" + bevType
				+ ", targetType=" + targetType + ", pageNo=" + pageNo + ", pageSize=" + pageSize + "_";
	}
	@Override
	public String toString() {
		return "UserMessageQueryDto [memberId=" + memberId + ", isRead=" + isRead + ", bevType=" + bevType
				+ ", targetType=" + targetType + ", pageNo=" + pageNo + ", pageSize=" + pageSize + "]";
	}
	
    
   
    
    
}
