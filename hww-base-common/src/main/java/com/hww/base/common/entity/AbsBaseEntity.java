package com.hww.base.common.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 统一定义的entity基类.
 */
// JPA基类标识
@MappedSuperclass
public abstract class AbsBaseEntity<ID extends Serializable> implements IBaseEntity<ID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final String SITE_ID = "siteId";
	static final String STATUS = "status";
	static final String CREATE_TIME = "createTime";
	static final String LAST_MODIFY_TIME = "lastModifyTime";

	protected Integer siteId;// 站点id
	protected Short status;// 状态
	protected Timestamp createTime;// 创建时间
	protected Timestamp lastModifyTime;// 最后修改时间

	@Column(name = "site_id")
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	@Column(name = "status")
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "last_modify_time")
	public Timestamp getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

}
