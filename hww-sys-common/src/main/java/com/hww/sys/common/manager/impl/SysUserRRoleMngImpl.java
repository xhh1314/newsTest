package com.hww.sys.common.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.sys.common.dao.SysUserDao;
import com.hww.sys.common.dao.SysUserRRoleDao;
import com.hww.sys.common.entity.SysRole;
import com.hww.sys.common.entity.SysUser;
import com.hww.sys.common.entity.SysUserRRole;
import com.hww.sys.common.manager.SysUserMng;
import com.hww.sys.common.manager.SysUserRRoleMng;

@Service("sysUserRRoleMng")
public class SysUserRRoleMngImpl extends BaseEntityMngImpl<Long, SysUserRRole, SysUserRRoleDao>
		implements SysUserRRoleMng {

	@Autowired
	SysUserRRoleDao sysUserRRoleDao;

	@Autowired
	public void setSysUserRRoleDao(SysUserRRoleDao sysUserRRoleDao) {
		super.setEntityDao(sysUserRRoleDao);
		this.sysUserRRoleDao = sysUserRRoleDao;
	}

	@Override
	public List<SysRole> queryByUserId(Long userId) {
		// TODO Auto-generated method stub
		return sysUserRRoleDao.queryByUserId(userId);
	}

	@Override
	public List<SysUserRRole> findByUserId(Long userId) {
		// TODO Auto-generated method stub
		return sysUserRRoleDao.findByUserId(userId);
	}

}
