package com.hww.cms.common.vo;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.hww.base.common.vo.AbsBaseVo;

/**
 * cms审核表
 *
 */
public class CmsContentAuditVo  extends AbsBaseVo

// implements
// ISocializable,IContentable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long auditId;
	private Long contentId;//新闻id
	private Timestamp lastModifyTime;//审核时间(最后修改时间)
	private Long auditFlowId;//审核流程id
	private Integer auditStep;//审核流程内容
	private Integer autitStepResult;//审核结果0:通过1:不通过
	private Long auditRole;//审核角色
	private Long auditUser;//审核人id
	private String auditUserStr;//审核人
	private Timestamp createTime;//创建时间
	private Short status;//状态 0 禁用 1 可用
	private Integer auditHisRecord;// 审核次数
	private Long categoryId;
	
	
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getAuditHisRecord() {
		return auditHisRecord;
	}

	public void setAuditHisRecord(Integer auditHisRecord) {
		this.auditHisRecord = auditHisRecord;
	}
	
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Long getAuditId() {
		return auditId;
	}
	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}
	public Long getContentId() {
		return contentId;
	}
	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}
	public Timestamp getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public Long getAuditFlowId() {
		return auditFlowId;
	}
	public void setAuditFlowId(Long auditFlowId) {
		this.auditFlowId = auditFlowId;
	}
	public Integer getAuditStep() {
		return auditStep;
	}
	public void setAuditStep(Integer auditStep) {
		this.auditStep = auditStep;
	}
	public Integer getAutitStepResult() {
		return autitStepResult;
	}
	public void setAutitStepResult(Integer autitStepResult) {
		this.autitStepResult = autitStepResult;
	}
	public Long getAuditRole() {
		return auditRole;
	}
	public void setAuditRole(Long auditRole) {
		this.auditRole = auditRole;
	}
	public Long getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(Long auditUser) {
		this.auditUser = auditUser;
	}
	public String getAuditUserStr() {
		return auditUserStr;
	}
	public void setAuditUserStr(String auditUserStr) {
		this.auditUserStr = auditUserStr;
	}
	
	
	
	
	
}
