package com.hww.ucenter.webadmin.dto;

import com.hww.base.common.dto.AbsBaseDto;

public class UCenterUser extends AbsBaseDto<Long> {

	private Long memberId;
	private String realName;
	/**
	 * 笔名
	 */
	private String pseudonym;
	private Integer pageNo;
	private Integer pageSize;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPseudonym() {
		return pseudonym;
	}

	public void setPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
