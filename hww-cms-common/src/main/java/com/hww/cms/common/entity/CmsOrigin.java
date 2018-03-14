package com.hww.cms.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 内容来源
 */
@Entity
@Table(name = "cms_origin")
public class CmsOrigin extends AbsBaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long originId; // 来源id
	private String originName; // 来源名称
	private String originUrl; // 来源地址
	private String icon;
	private String word;
	private String link;

	@Id
    @GeneratedValue(generator = "snowFlake")
	@GenericGenerator(name="snowFlake", strategy="com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name = "origin_id")
	public Long getOriginId() {
		return this.originId;
	}

	public void setOriginId(Long originId) {
		this.originId = originId;
	}

	@Column(name = "origin_name")
	public String getOriginName() {
		return this.originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	@Column(name = "origin_url")
	public String getOriginUrl() {
		return this.originUrl;
	}

	public void setOriginUrl(String originUrl) {
		this.originUrl = originUrl;
	}

	@Override
	@Transient
	public Long getId() {
		return originId;
	}

	@Column(name = "icon")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "word")
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Column(name = "link")
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}