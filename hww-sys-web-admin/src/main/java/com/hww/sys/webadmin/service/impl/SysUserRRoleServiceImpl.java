package com.hww.sys.webadmin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.util.BeanMapper;
import com.hww.sys.common.dto.SysUserRRoleDto;
import com.hww.sys.common.entity.SysUserRRole;
import com.hww.sys.common.manager.SysUserRRoleMng;
import com.hww.sys.webadmin.service.SysUserRRoleService;

@Service("sysUserRRoleService")
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class SysUserRRoleServiceImpl implements SysUserRRoleService {

	@Autowired
	SysUserRRoleMng SysUserRRoleMng;
	
	@Override
	public void saveSysUserRRole(SysUserRRoleDto userRRoleDto) {
		SysUserRRole entity = BeanMapper.map(userRRoleDto, SysUserRRole.class);
		SysUserRRoleMng.save(entity);
	}

	

}
