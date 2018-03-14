package com.hww.sys.common.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.hww.base.common.entity.IBaseEntity;

/**
 * 站点
 *
 */
@Entity
@Table(name = "sys_site")
public class SysSite implements IBaseEntity<Integer> {

	static final String SITE_NAME = "siteName";
	static final String SHORT_NAME = "shortName";
	static final String DOMAIN = "domain";
	static final String SITE_PATH = "sitePath";
	static final String STATIC_DIR = "staticDir";

	// Fields
	private Integer siteId;
	private String siteName;
	private String shortName;
	private String domain;
	private String sitePath;
	private String staticDir;
	
	private Short status;// 状态
	private Timestamp createTime;// 创建时间
	private Timestamp lastModifyTime;// 最后修改时间
	
	//private Set<SysRole> sysRoles = new HashSet<SysRole>(0);
	//private Set<SysMenu> sysMenus = new HashSet<SysMenu>(0);
	//private Set<SysGroup> sysGroups = new HashSet<SysGroup>(0);
	//private Set<SysUser> sysUsers = new HashSet<SysUser>(0);

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@GenericGenerator(name="snowFlake", strategy="com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name="site_id")
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	
	@Column(name="site_name")
	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	@Column(name="short_name")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name="domain")
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Column(name="site_path")
	public String getSitePath() {
		return sitePath;
	}

	public void setSitePath(String sitePath) {
		this.sitePath = sitePath;
	}

	@Column(name="static_dir")
	public String getStaticDir() {
		return staticDir;
	}

	public void setStaticDir(String staticDir) {
		this.staticDir = staticDir;
	}

	@Override
	@Transient
	public Integer getId() {
		return siteId;
	}

	@Column(name="status")
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name="create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name="last_modify_time")
	public Timestamp getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	
}