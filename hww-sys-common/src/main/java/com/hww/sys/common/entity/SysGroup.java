package com.hww.sys.common.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
 * 组
 *
 */
@Entity
@Table(name = "sys_group")
public class SysGroup
	extends
		AbsBaseEntity<Long> {

	static final String GROUP_ID = "groupId";
	static final String GROUP_NAME = "groupName";

	// Fields
	private Long groupId;
	private String groupName;

	private List<SysUser> sysUsers = new ArrayList<SysUser>(0);
	private List<SysMenu> sysMenus = new ArrayList<SysMenu>(0);

	// Property accessors
	@Id
	@GeneratedValue(generator = "snowFlake")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="snowFlake", strategy="com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name = "group_id")
	public Long getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	@Column(name = "group_name")
	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@ManyToMany(mappedBy = "sysGroups")
	public List<SysUser> getSysUsers() {
		return this.sysUsers;
	}

	public void setSysUsers(List<SysUser> sysUsers) {
		this.sysUsers = sysUsers;
	}

	@JoinTable(name = "sys_group_r_menu", joinColumns = {
			@JoinColumn(name = "group_id", referencedColumnName = "group_id") }, inverseJoinColumns = {
					@JoinColumn(name = "menu_id", referencedColumnName = "menu_id") })
	@ManyToMany
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
		return groupId;
	}

}