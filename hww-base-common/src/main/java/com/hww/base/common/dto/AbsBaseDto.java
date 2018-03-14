package com.hww.base.common.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public abstract class AbsBaseDto<ID extends Serializable> implements IBaseDto<ID>{
	
	protected ID id;
	protected Integer siteId;//站点id
    protected Short status;// 状态
    protected Timestamp createTime;
    protected Timestamp lastModifyTime;
	
    public ID getId() {
		return id;
	}
	public void setId(ID id) {
		this.id = id;
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
    
	
}
