package com.hww.sys.common.vo;

import org.hibernate.validator.constraints.NotBlank;

import com.hww.base.common.vo.AbsBaseVo;

public class SysSiteVo extends AbsBaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer siteId;
	@NotBlank
	private String siteName;
	private String shortName;
	private String domain;
	private String sitePath;
	private String staticDir;
	private Short status;

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSitePath() {
		return sitePath;
	}

	public void setSitePath(String sitePath) {
		this.sitePath = sitePath;
	}

	public String getStaticDir() {
		return staticDir;
	}

	public void setStaticDir(String staticDir) {
		this.staticDir = staticDir;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

}
