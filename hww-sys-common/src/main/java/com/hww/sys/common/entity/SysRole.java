package com.hww.sys.common.entity;

import java.util.ArrayList;
import java.util.List;

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
 * 角色
 *
 */
@Entity
@Table(name = "sys_role")
public class SysRole
	extends
		AbsBaseEntity<Long> {

	static final String ROLE_ID = "roleId";
	static final String ROLE_NAME = "roleName";

	// Fields

	private Long roleId;
	private String roleName;

	private List<SysUser> sysUsers = new ArrayList<SysUser>(0);
	private List<SysMenu> sysMenus = new ArrayList<SysMenu>(0);

	// Property accessors
	@Id
	@GeneratedValue(generator = "snowFlake")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="snowFlake", strategy="com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name = "role_id")
	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Column(name = "role_name")
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@ManyToMany(mappedBy = "sysRoles")
	public List<SysUser> getSysUsers() {
		return this.sysUsers;
	}

	public void setSysUsers(List<SysUser> sysUsers) {
		this.sysUsers = sysUsers;
	}

	@JoinTable(name = "sys_role_r_menu", joinColumns = {
			@JoinColumn(name = "role_id", referencedColumnName = "role_id") }, inverseJoinColumns = {
					@JoinColumn(name = "menu_id", referencedColumnName = "menu_id") })
	@ManyToMany(fetch=FetchType.EAGER)
	public List<SysMenu> getSysMenus() {
		return this.sysMenus;
	}

	public void setSysMenus(List<SysMenu> sysMenus) {
		this.sysMenus = sysMenus;
	}

	@Override
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return roleId;
	}
}