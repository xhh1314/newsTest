package com.hww.ucenter.common.dto;

import java.io.Serializable;

public class MememberSnsDisableDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long memberId;
	private Integer disabled;//1 禁用 0 整除
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Integer getDisabled() {
		return disabled;
	}
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
	
	
}
