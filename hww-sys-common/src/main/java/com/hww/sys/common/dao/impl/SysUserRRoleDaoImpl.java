package com.hww.sys.common.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.hww.base.common.util.Finder;
import com.hww.sys.common.dao.SysUserRRoleDao;
import com.hww.sys.common.dto.SysRoleDto;
import com.hww.sys.common.entity.SysRole;
import com.hww.sys.common.entity.SysUserRRole;
@Repository("sysUserRRoleDao")
public class SysUserRRoleDaoImpl extends LocalEntityDaoImpl<Long, SysUserRRole> implements SysUserRRoleDao{

	@Override
	public List<SysRole> queryByUserId(Long userId) {
		Finder f=Finder.create("from SysUserRRole");
		f.append(" where userId=:userId").setParam("userId", userId);
		return (List<SysRole>) find(f);
	}
	
    @Override
    public List<SysUserRRole> findByUserId(Long userId) {
      Finder f = Finder.create("select user_id as userId, role_id as RoleId from sys_user_r_role");
      f.append(" where user_id=:userId");
      f.setParam("userId", userId);
      List<SysUserRRole> list = (List<SysUserRRole>) findJoin(f, SysUserRRole.class);
      return list;
    }

}
