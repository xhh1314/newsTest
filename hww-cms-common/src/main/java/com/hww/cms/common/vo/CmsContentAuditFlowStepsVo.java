package com.hww.cms.common.vo;

import java.sql.Timestamp;
import com.hww.base.common.vo.AbsBaseVo;

public class CmsContentAuditFlowStepsVo extends AbsBaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long auditFlowStepsId;
	private Long flowId;//所属流程id
	private String sepDesc;//审核流程步详情
	private Integer stepIndex;//审核流程步骤索引
	private String stepAuditRole;//审核的角色类型
	private String stepAutidRoleName;//审核人
	private Timestamp createTime;//创建时间
	private Timestamp lastModifyTime;//修改时间
	private Short status;//状态 0 启用 1 禁用
	private Long roleId;
	
	public Long getAuditFlowStepsId() {
		return auditFlowStepsId;
	}
	public void setAuditFlowStepsId(Long auditFlowStepsId) {
		this.auditFlowStepsId = auditFlowStepsId;
	}
	public Long getFlowId() {
		return flowId;
	}
	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}
	public String getSepDesc() {
		return sepDesc;
	}
	public void setSepDesc(String sepDesc) {
		this.sepDesc = sepDesc;
	}
	public Integer getStepIndex() {
		return stepIndex;
	}
	public void setStepIndex(Integer stepIndex) {
		this.stepIndex = stepIndex;
	}
	public String getStepAuditRole() {
		return stepAuditRole;
	}
	public void setStepAuditRole(String stepAuditRole) {
		this.stepAuditRole = stepAuditRole;
	}
	public String getStepAutidRoleName() {
		return stepAutidRoleName;
	}
	public void setStepAutidRoleName(String stepAutidRoleName) {
		this.stepAutidRoleName = stepAutidRoleName;
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
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	
	
	
	
}
