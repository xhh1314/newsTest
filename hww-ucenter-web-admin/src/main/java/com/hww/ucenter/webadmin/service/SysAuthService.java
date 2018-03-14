
package com.hww.ucenter.webadmin.service;

import java.util.List;


import com.hww.base.util.R;
import com.hww.sys.common.dto.SysMenuDto;
import com.hww.sys.common.dto.SysRoleDto;
import com.hww.sys.common.dto.SysSiteDto;
import com.hww.sys.common.dto.SysUserDto;
import com.hww.ucenter.common.dto.MememberSnsDisableDto;
import com.hww.ucenter.common.dto.UCenterMemberDto;

public interface SysAuthService {

	List<SysMenuDto> findsMenusByRoles(Long roleId);
	List<SysRoleDto> findRole();
	SysUserDto findUserByName(String username);
	SysSiteDto findSiteById(Integer id);
	 List<SysSiteDto> list(SysSiteDto searchDto);
//	 List<SysUserDto> findUserList();
	 
	 
	 public UCenterMemberDto userInfoInFeginApi(Long memberId);
	 public R setMememberSnsDisabled( MememberSnsDisableDto dto);
}
