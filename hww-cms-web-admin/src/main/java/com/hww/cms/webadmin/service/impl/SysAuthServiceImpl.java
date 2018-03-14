package com.hww.cms.webadmin.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hww.cms.webadmin.service.SysAuthService;
import com.hww.sys.api.SysFeignClient;
import com.hww.sys.common.dto.SysMenuDto;
import com.hww.sys.common.dto.SysRoleDto;
import com.hww.sys.common.dto.SysSiteDto;
import com.hww.sys.common.dto.SysUserDto;

@Service("sysAuthServiceImpl")
@Transactional
public class SysAuthServiceImpl implements SysAuthService{
     @Autowired
	SysFeignClient sysFeignClient;
	
	@Override
	public SysUserDto findUserByName(String username) {
		
		return sysFeignClient.findUser(username);
	}

	@Override
	public SysSiteDto findSiteById(Integer id) {
		
		return sysFeignClient.findSiteById(id);
	}

	@Override
	public List<SysSiteDto> list(SysSiteDto searchDto) {
		
		return sysFeignClient.findSitelist(searchDto);
	}

	@Override
	public List<SysMenuDto> findsMenusByRoles(Long roleId) {
		
		return sysFeignClient.findByRole(roleId);
	}

	@Override
	public List<SysRoleDto> findRole() {
		
		return sysFeignClient.allList();
	}

}
