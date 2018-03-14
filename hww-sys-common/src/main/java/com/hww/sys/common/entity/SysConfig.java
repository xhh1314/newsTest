package com.hww.sys.common.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hww.base.common.entity.IBaseEntity;
@Entity
@Table(name="sys_config")
public class SysConfig implements IBaseEntity<SysConfigId>{
	
	private SysConfigId configId;
	private String domain;//域名或者ip
	
	@EmbeddedId
	public SysConfigId getConfigId() {
		return configId;
	}

	public void setConfigId(SysConfigId configId) {
		this.configId = configId;
	}

	@Column(name="domain")
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	@Transient
	public SysConfigId getId() {
		// TODO Auto-generated method stub
		return configId;
	}
}
