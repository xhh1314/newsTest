package com.hww.cms.common.vo;

import java.sql.Timestamp;

import com.hww.base.common.vo.BaseTreeVo;

public class CmsContentTypeVo extends BaseTreeVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long contentTypeId; // 内容类型id
	private String contentTypeName; // 内容类型名称
	private String allIDCheck; // 选中id列表
	private Integer siteId;// 站点id
	private Short status; // 状态 0 禁用 1 可用
	private Timestamp createTime;// 创建时间
	private Timestamp lastModifyTime;// 最后修改时间
	private Integer pageNo; // 页码

	public Long getContentTypeId() {
		return contentTypeId;
	}

	public String getContentTypeName() {
		return contentTypeName;
	}

	public Short getStatus() {
		return status;
	}

	public String getAllIDCheck() {
		return allIDCheck;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setContentTypeId(Long contentTypeId) {
		this.contentTypeId = contentTypeId;
	}

	public void setContentTypeName(String contentTypeName) {
		this.contentTypeName = contentTypeName;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public void setAllIDCheck(String allIDCheck) {
		this.allIDCheck = allIDCheck;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
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

}
