package com.hww.sys.common.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.sys.common.dao.SysRoleDao;
import com.hww.sys.common.entity.SysRole;
import com.hww.sys.common.manager.SysRoleMng;

@Service("sysRoleMng")
public class SysRoleMngImpl extends BaseEntityMngImpl<Long, SysRole, SysRoleDao> implements SysRoleMng {
	SysRoleDao sysRoleDao;

	public SysRoleDao getSysRoleDao() {
		return sysRoleDao;
	}

	@Autowired
	public void setSysRoleDao(SysRoleDao sysRoleDao) {
		super.setEntityDao(sysRoleDao);
		this.sysRoleDao = sysRoleDao;
	}


}
