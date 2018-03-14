package com.hww.sys.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hww.base.common.entity.AbsBaseEntity;
import com.hww.base.common.entity.IBaseEntity;


@Entity
@Table(name = "sys_user_r_role")
@JsonIgnoreProperties(value = "id")
public class SysUserRRole implements IBaseEntity<Long>  {
	
	private Long userId;
	private Long RoleId;
	
	@Id
	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column(name="role_id")
	public Long getRoleId() {
		return RoleId;
	}
	public void setRoleId(Long roleId) {
		RoleId = roleId;
	}
	@Override
	@Transient
	public Long getId() {
		return userId;
	}
	
	

}
