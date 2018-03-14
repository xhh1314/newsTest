package com.hww.app.admin.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hww.app.admin.service.SysAuthService;
import com.hww.base.util.R;
import com.hww.sys.common.dto.SysMenuDto;
import com.hww.sys.common.dto.SysRoleDto;
import com.hww.sys.common.dto.SysSiteDto;
import com.hww.sys.common.dto.SysUserDto;
import com.hww.sys.api.SysFeignClient;
@Service("sysAuthServiceImpl")
@Transactional
public class SysAuthServiceImpl implements SysAuthService{

	@Autowired
	SysFeignClient SysFeignClient;
	
	@Override
	public SysUserDto findUserByName(String username) {
		
		return SysFeignClient.findUser(username);
	}

	@Override
	public SysSiteDto findSiteById(Integer id) {
		
		return SysFeignClient.findSiteById(id);
	}

	@Override
	public List<SysSiteDto> list(SysSiteDto searchDto) {
		
		return SysFeignClient.findSitelist(searchDto);
	}

	@Override
	public List<SysMenuDto> findsMenusByRoles(Long roleId) {
		
		return SysFeignClient.findByRole(roleId);
	}

	@Override
	public List<SysRoleDto> findRole() {
		
		return SysFeignClient.allList();
	}

	@Override
	public R updateUserPassword(SysUserDto dto) {
		// TODO Auto-generated method stub
		return SysFeignClient.updateUserPassword(dto);
	}

}
