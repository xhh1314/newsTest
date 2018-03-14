package com.hww.app.common.vo;

import java.sql.Timestamp;

import com.hww.base.common.vo.AbsBaseVo;

public class AppVersionVo extends AbsBaseVo {
	
	
	private Long id;
	private String appName;
	private String versionCode;
	private String versionName;
	private String apkUrl;
	private String changeLog;
	private String updateTips;
	private Integer forceUpgrade;
	private Timestamp createTime;

	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	public String getChangeLog() {
		return changeLog;
	}

	public void setChangeLog(String changeLog) {
		this.changeLog = changeLog;
	}

	public String getUpdateTips() {
		return updateTips;
	}

	public void setUpdateTips(String updateTips) {
		this.updateTips = updateTips;
	}

	public Integer getForceUpgrade() {
		return forceUpgrade;
	}

	public void setForceUpgrade(Integer forceUpgrade) {
		this.forceUpgrade = forceUpgrade;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	

}
