package com.hww.app.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import com.hww.base.common.entity.IBaseEntity;
import com.hww.base.common.entity.IBaseTree;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.sql.Timestamp;



@Entity
@Table(name = "app_version")
public class AppVersion implements IBaseEntity<Long> {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	@Id
	@Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "app_name")
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	@Column(name = "version_code")
	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	@Column(name = "version_name")
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getApkUrl() {
		return apkUrl;
	}
	@Column(name = "apk_url")
	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}
	@Column(name = "change_log")
	public String getChangeLog() {
		return changeLog;
	}

	public void setChangeLog(String changeLog) {
		this.changeLog = changeLog;
	}
	@Column(name = "update_tips")
	public String getUpdateTips() {
		return updateTips;
	}

	public void setUpdateTips(String updateTips) {
		this.updateTips = updateTips;
	}
	@Column(name = "force_upgrade")
	public Integer getForceUpgrade() {
		return forceUpgrade;
	}

	public void setForceUpgrade(Integer forceUpgrade) {
		this.forceUpgrade = forceUpgrade;
	}
	@Column(name = "create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}