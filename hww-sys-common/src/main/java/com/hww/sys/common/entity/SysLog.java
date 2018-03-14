package com.hww.sys.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.hww.base.common.entity.AbsBaseEntity;

/**
 * 日志
 *
 */
@Entity
@Table(name = "sys_log")
public class SysLog
	extends
		AbsBaseEntity<Long> {

	// Fields

	private Long logId;
	private Integer type;
	private String ip;
	private String url;
	private String title;
	private String content;

	private Long userId;

	// Property accessors
	@Id
	@GeneratedValue(generator = "snowFlake")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="snowFlake", strategy="com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name = "log_id")
	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "ip")
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "url")
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "title")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "user_id")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return logId;
	}
}