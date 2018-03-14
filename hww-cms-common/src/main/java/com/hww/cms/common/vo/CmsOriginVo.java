package com.hww.cms.common.vo;

import com.hww.base.common.vo.AbsBaseVo;

import java.sql.Timestamp;

/**
 * 内容来源vo
 */
public class CmsOriginVo extends AbsBaseVo {

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
	protected Integer siteId;//站点id
	protected Short status;// 状态
	protected Timestamp createTime;
	protected Timestamp lastModifyTime;
	public Long getOriginId() {
		return originId;
	}
	public void setOriginId(Long originId) {
		this.originId = originId;
	}
	public String getOriginName() {
		return originName;
	}
	public void setOriginName(String originName) {
		this.originName = originName;
	}
	public String getOriginUrl() {
		return originUrl;
	}
	public void setOriginUrl(String originUrl) {
		this.originUrl = originUrl;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
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

	public Timestamp getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
}