package com.hww.cms.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.hibernate.annotations.GenericGenerator;

import com.hww.base.common.entity.AbsBaseEntity;

@Entity
@Table(name = "cms_content_audit_flow_steps")
public class CmsContentAuditFlowSteps extends AbsBaseEntity<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long auditFlowStepsId;
	private CmsContentAuditFlow flow;//所属流程id
	private String sepDesc;//审核流程步详情
	private Integer stepIndex;//审核流程步骤索引
	private String stepAuditRole;//审核的角色类型
	private String stepAutidRoleName;//审核人
	private Long roleId;
	
	
	@Id
	@GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name = "audit_flow_steps_id")
	public Long getAuditFlowStepsId() {
		return auditFlowStepsId;
	}

	public void setAuditFlowStepsId(Long auditFlowStepsId) {
		this.auditFlowStepsId = auditFlowStepsId;
	}
	
	
	@Column(name = "role_id")
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@ManyToOne(targetEntity=CmsContentAuditFlow.class)
	@JoinColumn(name = "flow_id", referencedColumnName = "flow_id")
	public CmsContentAuditFlow getFlow() {
		return flow;
	}
	
	
	public void setFlow(CmsContentAuditFlow flow) {
		this.flow = flow;
	}

	@Column(name = "sep_desc")
	public String getSepDesc() {
		return sepDesc;
	}

	public void setSepDesc(String sepDesc) {
		this.sepDesc = sepDesc;
	}

	@Column(name = "step_index")
	public Integer getStepIndex() {
		return stepIndex;
	}

	public void setStepIndex(Integer stepIndex) {
		this.stepIndex = stepIndex;
	}

	@Column(name = "step_audit_role")
	public String getStepAuditRole() {
		return stepAuditRole;
	}

	public void setStepAuditRole(String stepAuditRole) {
		this.stepAuditRole = stepAuditRole;
	}

	@Column(name = "step_autid_role_name")
	public String getStepAutidRoleName() {
		return stepAutidRoleName;
	}

	public void setStepAutidRoleName(String stepAutidRoleName) {
		this.stepAutidRoleName = stepAutidRoleName;
	}

	@Override
	@Transient
	public Long getId() {
		return this.auditFlowStepsId;
	}

	
}
