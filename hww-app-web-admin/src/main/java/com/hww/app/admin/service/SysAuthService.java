package com.hww.app.admin.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.hww.base.util.R;
import com.hww.sys.common.dto.SysMenuDto;
import com.hww.sys.common.dto.SysRoleDto;
import com.hww.sys.common.dto.SysSiteDto;
import com.hww.sys.common.dto.SysUserDto;

public interface SysAuthService {

	List<SysMenuDto> findsMenusByRoles(Long roleId);
	List<SysRoleDto> findRole();
	SysUserDto findUserByName(String username);
	SysSiteDto findSiteById(Integer id);
	 List<SysSiteDto> list(SysSiteDto searchDto);
	 public R updateUserPassword( SysUserDto dto);
}
