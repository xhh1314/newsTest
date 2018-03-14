package com.hww.sns.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * sns审核表
 *
 */
@Entity
@Table(name = "sns_audit")
public class SnsAudit  extends AbsBaseEntity<Long>

// implements
// ISocializable,IContentable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long auditId;
	private Long topicId;//主题id
	private Integer topicType;//类型(0为主题,1为评论)
	private Integer curentstatus;//当前状态
	private String auditUser;// 审核人
	private Integer auditLevel;//审核级别(目前1-4级)
	private String auditOpinion;//审核意见
	
	
	@Id
    @GeneratedValue(generator = "snowFlake")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="snowFlake", strategy="com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name = "audit_id")
	public Long getAuditId() {
		return auditId;
	}


	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	@Column(name = "topic_id")
	public Long getTopicId() {
		return topicId;
	}


	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	@Column(name = "topic_type")
	public Integer getTopicType() {
		return topicType;
	}


	public void setTopicType(Integer topicType) {
		this.topicType = topicType;
	}

	@Column(name = "curentstatus")
	public Integer getCurentstatus() {
		return curentstatus;
	}


	public void setCurentstatus(Integer curentstatus) {
		this.curentstatus = curentstatus;
	}

	@Column(name = "audit_user")
	public String getAuditUser() {
		return auditUser;
	}


	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	@Column(name = "audit_level")
	public Integer getAuditLevel() {
		return auditLevel;
	}


	public void setAuditLevel(Integer auditLevel) {
		this.auditLevel = auditLevel;
	}
	
	@Column(name = "auditopinion")
	public String getAuditOpinion() {
		return auditOpinion;
	}


	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	
	@Override
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return auditId;
	}
	
}
