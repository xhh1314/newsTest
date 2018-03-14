package com.hww.cms.common.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.yaml.snakeyaml.events.Event.ID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hww.base.common.entity.AbsBaseEntity;
import com.hww.base.common.entity.IBaseEntity;

@Entity
@Table(name = "cms_content_push")
@JsonIgnoreProperties(value = { "id" })
public class CmsContentPush extends AbsBaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long pushId;
	private Long contentId;
	private Timestamp pushTime;
	private Timestamp createTime;
	private Short status;
	private Integer pushResults;//推送结果 0 失败 1成功
	private String creator;
	private String pushAlert;
	private String pushNewsTitle;
	private Integer pushNewsType;
	private String pushNewsUrl;
	
	private Integer jpSendno;
	private Long jpMsgId;
	private Integer jpErrorCode;
	private Integer jpHttpStatus;

	private String jpErrorMsg;
	
	


	
	public CmsContentPush() {
		super();
		
	}
	public CmsContentPush(Long contentId, Timestamp pushTime, Timestamp createTime, Short status, Integer pushResults,
			String creator, String pushAlert, String pushNewsTitle, Integer pushNewsType, String pushNewsUrl) {
		super();
		this.contentId = contentId;
		this.pushTime = pushTime;
		this.createTime = createTime;
		this.status = status;
		this.pushResults = pushResults;
		this.creator = creator;
		this.pushAlert = pushAlert;
		this.pushNewsTitle = pushNewsTitle;
		this.pushNewsType = pushNewsType;
		this.pushNewsUrl = pushNewsUrl;
	}
	@Id
	@GeneratedValue(generator="snowFlake")
    @GenericGenerator(name="snowFlake", strategy="com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name = "push_id")
	public Long getPushId() {
		return pushId;
	}

	public CmsContentPush setPushId(Long pushId) {
		this.pushId = pushId;
		return this;
	}

	@Column(name = "content_id")
	public Long getContentId() {
		return contentId;
	}

	public CmsContentPush setContentId(Long contentId) {
		this.contentId = contentId;
		return this;
	}

	@Column(name = "push_time")
	public Timestamp getPushTime() {
		return pushTime;
	}


	public CmsContentPush setPushTime(Timestamp pushTime) {
		this.pushTime = pushTime;
		return this;
	}

	@Column(name = "create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "status")
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "push_results")
	public Integer getPushResults() {
		return pushResults;
	}

	public CmsContentPush setPushResults(Integer pushResults) {
		this.pushResults = pushResults;
		return this;
	}

	@Column(name = "creator")
	public String getCreator() {
		return creator;
	}

	public CmsContentPush setCreator(String creator) {
		this.creator = creator;
		return this;
	}

	@Column(name = "push_alert")
	public String getPushAlert() {
		return pushAlert;
	}

	public CmsContentPush setPushAlert(String pushAlert) {
		this.pushAlert = pushAlert;
		return this;
	}

	@Column(name = "push_news_title")
	public String getPushNewsTitle() {
		return pushNewsTitle;
	}


	public CmsContentPush setPushNewsTitle(String pushNewsTitle) {
		this.pushNewsTitle = pushNewsTitle;
		return this;
	}

	@Column(name = "push_news_type")
	public Integer getPushNewsType() {
		return pushNewsType;
	}


	public CmsContentPush setPushNewsType(Integer pushNewsType) {
		this.pushNewsType = pushNewsType;
		return this;
	}

	@Column(name = "push_news_url")
	public String getPushNewsUrl() {
		return pushNewsUrl;
	}

	public CmsContentPush setPushNewsUrl(String pushNewsUrl) {
		this.pushNewsUrl = pushNewsUrl;
		return this;
	}


	@Column(name = "jp_sendno")
	public Integer getJpSendno() {
		return jpSendno;
	}
	public CmsContentPush setJpSendno(Integer jpSendno) {
		this.jpSendno = jpSendno;
		return this;
	}
	@Column(name = "jp_msgId")
	public Long getJpMsgId() {
		return jpMsgId;
	}
	public CmsContentPush setJpMsgId(Long jpMsgId) {
		this.jpMsgId = jpMsgId;
		return this;
	}
	@Column(name = "jp_error_code")
	public Integer getJpErrorCode() {
		return jpErrorCode;
	}
	public CmsContentPush setJpErrorCode(Integer jpErrorCode) {
		this.jpErrorCode = jpErrorCode;
		return this;
	}
	@Column(name = "jp_http_status")
	public Integer getJpHttpStatus() {
		return jpHttpStatus;
	}
	public CmsContentPush setJpHttpStatus(Integer jpHttpStatus) {
		this.jpHttpStatus = jpHttpStatus;
		return this;
	}
	@Column(name = "jp_error_msg")
	public String getJpErrorMsg() {
		return jpErrorMsg;
	}
	public CmsContentPush setJpErrorMsg(String jpErrorMsg) {
		this.jpErrorMsg = jpErrorMsg;
		return this;
	}
	
	
	@Override
	@Transient
	@Id
	public Long getId() {
		return this.pushId;
	}

}
