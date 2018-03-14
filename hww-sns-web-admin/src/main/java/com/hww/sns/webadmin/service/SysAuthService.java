package com.hww.sns.webadmin.service;

import java.util.List;

import com.hww.sys.common.dto.*;
import com.hww.sys.common.entity.SysMenu;

public interface SysAuthService {

	List<SysMenuDto> findsMenusByRoles(Long roleId);
	List<SysRoleDto> findRole();
	SysUserDto findUserByName(String username);
	SysSiteDto findSiteById(Integer id);
	 List<SysSiteDto> list(SysSiteDto searchDto);
	 List<SysModuleDto> listModule();
}
