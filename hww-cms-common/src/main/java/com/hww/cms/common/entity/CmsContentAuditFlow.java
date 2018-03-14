package com.hww.cms.common.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.hww.base.common.entity.AbsBaseEntity;

@Entity
@Table(name = "cms_content_audit_flow")
public class CmsContentAuditFlow extends AbsBaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long flowId;
	private String flowName;//审核流程名
	private Short status;
	private String remark;
	
	@Id
	@GeneratedValue(generator = "snowFlake")
	@GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name = "flow_id")
	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	@Column(name = "flow_name")
	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	@Column(name = "status")
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}




	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	@Transient
	public Long getId() {
		return this.flowId;
	}
	
	
	
	
	
	
}
