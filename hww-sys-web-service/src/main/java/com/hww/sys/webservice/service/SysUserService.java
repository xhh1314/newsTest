package com.hww.sys.webservice.service;

import java.util.Collection;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.hww.base.common.page.Pagination;
import com.hww.base.util.R;
import com.hww.sys.common.dto.SysUserDto;

public interface SysUserService {
	
	Pagination page(SysUserDto searchDto, Integer pageNo, Integer pageSize);
	
	SysUserDto findUserById(Long id);
	
	SysUserDto findUserByName(String username);
	
	SysUserDto findUserWithGroupById(Long id);
	
	SysUserDto findUserWithRoleById(Long id);
	
	void saveUser(SysUserDto dto);
	
	void updateUser(SysUserDto dto);
	public R updateUserPassword( SysUserDto dto);
	
	void updateUserStatusByIds(Short status, Collection<Long> userIds);
	
	List<SysUserDto> queryUserList();
	
	void delUser(SysUserDto dto);
	public R updateUserDefaultRole(Long userId,Long roleId);
}
