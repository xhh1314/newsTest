package com.hww.sns.common.entity;


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
@Table(name = "sns_report")
public class SnsReport extends
     AbsBaseEntity<Long> {
	
	private static final long serialVersionUID = 1L;
    private Long reportId;
    private Long snsContentId;
	private Integer plateType;
	private String reportContent;
	private Long memberId;
	private String imei;
	private Integer auditstatus;
	private Integer siteId;
	private Short status;
	private Timestamp createTime;
	private Timestamp lastModifyTime;
	private Integer anonymous;
	
	@Id
    @GeneratedValue(generator = "snowFlake")
	@GenericGenerator(name="snowFlake", strategy="com.hww.framework.common.idgen.SnowFlakeIdGenerator")
    @Column(name = "report_id")
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}


 @Column(name = "sns_content_id")
	public Long getSnsContentId() {
		return snsContentId;
	}
	public void setSnsContentId(Long snsContentId) {
		this.snsContentId = snsContentId;
	}

	 @Column(name = "plate_type")
	public Integer getPlateType() {
		return plateType;
	}
	public void setPlateType(Integer plateType) {
		this.plateType = plateType;
	}


	 @Column(name = "report_content")
	public String getReportContent() {
		return reportContent;
	}
	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}


	 @Column(name = "member_id")
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	 @Column(name = "imei")
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	 @Column(name = "auditstatus")
	public Integer getAuditstatus() {
		return auditstatus;
	}


	public void setAuditstatus(Integer auditstatus) {
		this.auditstatus = auditstatus;
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

	 @Column(name = "anonymous")
	public Integer getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(Integer anonymous) {
		this.anonymous = anonymous;
	}
	@Override
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return reportId;
	}


	

}
