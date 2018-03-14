package com.hww.sys.common.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.hww.base.common.entity.AbsBaseEntity;

/**
 * 管理员 并且一定是 SysMember 用来统一前后台登录和会员制
 *
 */
@Entity
@Table(name = "sys_user")
public class SysUser extends AbsBaseEntity<Long> {

	private static final long serialVersionUID = 7554901654340321627L;
	static final String USER_ID = "userId";
	static final String IS_ADMIN = "isAdmin";
	static final String IS_SELF_ADMIN = "isSelfAdmin";
	static final String IS_VIEW_ONLY_ADMIN = "isViewonlyAdmin";
	static final String IS_ALL_CATEGORY = "isAllCategory";
	static final String RANK = "rank";

	// Fields

	private Long userId;
	private String email;
	private Short isAdmin;
	private Short isSelfAdmin;
	private Short isViewonlyAdmin;
	private Short isAllCategory;
	private Integer rank;
	private String username;
	private String password;

	private String registerIp;
	private Long defaultRole;

	@Column(name = "register_ip")
	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	private List<SysGroup> sysGroups = new ArrayList<SysGroup>(0);
	private List<SysRole> sysRoles = new ArrayList<SysRole>(0);

	// Property accessors
	@Id
	//@GeneratedValue(generator = "snowFlake")
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	//@GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name = "user_id")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "is_admin")
	public Short getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(Short isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Column(name = "is_self_admin")
	public Short getIsSelfAdmin() {
		return this.isSelfAdmin;
	}

	public void setIsSelfAdmin(Short isSelfAdmin) {
		this.isSelfAdmin = isSelfAdmin;
	}

	@Column(name = "is_viewonly_admin")
	public Short getIsViewonlyAdmin() {
		return this.isViewonlyAdmin;
	}

	public void setIsViewonlyAdmin(Short isViewonlyAdmin) {
		this.isViewonlyAdmin = isViewonlyAdmin;
	}

	@Column(name = "is_all_category")
	public Short getIsAllCategory() {
		return this.isAllCategory;
	}

	public void setIsAllCategory(Short isAllCategory) {
		this.isAllCategory = isAllCategory;
	}

	@Column(name = "rank")
	public Integer getRank() {
		return this.rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	@JoinTable(name = "sys_user_r_group", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "user_id") }, inverseJoinColumns = {
					@JoinColumn(name = "group_id", referencedColumnName = "group_id") })
	@ManyToMany
	public List<SysGroup> getSysGroups() {
		return this.sysGroups;
	}

	public void setSysGroups(List<SysGroup> sysGroups) {
		this.sysGroups = sysGroups;
	}

	@JoinTable(name = "sys_user_r_role", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "user_id") }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "role_id") })
	@ManyToMany
	public List<SysRole> getSysRoles() {
		return this.sysRoles;
	}

	public void setSysRoles(List<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}

	public Set<String> getPerms(Integer siteId, Set<String> perms) {
		Set<String> allPerms = new HashSet<String>();
		allPerms.add("*");
		return allPerms;
	}

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "default_role")
	public Long getDefaultRole() {
		return defaultRole;
	}

	public void setDefaultRole(Long defaultRole) {
		this.defaultRole = defaultRole;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return userId;
	}

}