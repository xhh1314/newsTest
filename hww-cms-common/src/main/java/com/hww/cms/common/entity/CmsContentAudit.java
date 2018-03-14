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
@Table(name = "cms_content_audit")
public class CmsContentAudit extends AbsBaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long auditId;
	private Long contentId;// 新闻id
	private Timestamp lastModifyTime;// 审核时间(最后修改时间)
	private Long auditFlowId;// 审核流程id
	private Integer auditStep;// 审核流程内容id
	private Integer autitStepResult;// 审核结果0:通过1:不通过
	private Long auditRole;// 审核角色
	private Long auditUser;// 审核人id
	private String auditUserStr;// 审核人
	private Integer auditHisRecord;// 审核次数

	@Column(name = "audit_his_record")
	public Integer getAuditHisRecord() {
		return auditHisRecord;
	}

	public void setAuditHisRecord(Integer auditHisRecord) {
		this.auditHisRecord = auditHisRecord;
	}

	@Id
	@GeneratedValue(generator = "snowFlake")
	@GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name = "audit_id")
	public Long getAuditId() {
		return auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	@Column(name = "content_id")
	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

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

	@Override
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Column(name = "audit_flow_id")
	public Long getAuditFlowId() {
		return auditFlowId;
	}

	public void setAuditFlowId(Long auditFlowId) {
		this.auditFlowId = auditFlowId;
	}

	@Column(name = "audit_step")
	public Integer getAuditStep() {
		return auditStep;
	}

	public void setAuditStep(Integer auditStep) {
		this.auditStep = auditStep;
	}

	@Column(name = "autit_step_result")
	public Integer getAutitStepResult() {
		return autitStepResult;
	}

	public void setAutitStepResult(Integer autitStepResult) {
		this.autitStepResult = autitStepResult;
	}

	@Column(name = "audit_role")
	public Long getAuditRole() {
		return auditRole;
	}

	public void setAuditRole(Long auditRole) {
		this.auditRole = auditRole;
	}

	@Column(name = "audit_user")
	public Long getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(Long auditUser) {
		this.auditUser = auditUser;
	}

	@Column(name = "audit_user_str")
	public String getAuditUserStr() {
		return auditUserStr;
	}

	public void setAuditUserStr(String auditUserStr) {
		this.auditUserStr = auditUserStr;
	}

}
