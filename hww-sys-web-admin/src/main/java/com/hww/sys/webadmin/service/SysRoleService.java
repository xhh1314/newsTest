package com.hww.sys.webadmin.service;

import java.util.Collection;
import java.util.List;

import com.hww.base.common.page.Pagination;
import com.hww.sys.common.dto.SysRoleDto;
import com.hww.sys.common.entity.SysRole;
import com.hww.sys.webadmin.dto.SysMenuDto;

public interface SysRoleService {
	
	Pagination list(SysRoleDto searchDto, Integer pageNo, Integer pageSize);
	
	List<SysRole> allList();
	
	SysRoleDto findRoleById(Long id);
	
	SysRoleDto findRoleWithMenuById(Long id);
	
	public void saveRole(SysRoleDto dto);
	
	public void updateRole(SysRoleDto dto);
	public void updateRoleOne(SysRoleDto dto);
	
	void updateRoleStatusByIds(Short status, Collection<Long> roleIds);
    void delRole(Long roleId);
	/**
	 * 查询用户所有角色具有的菜单
	 *
	 * @author Administrator
	 * @email xlfbe696@gmail.com
	 * @date 2017年11月14日 下午4:38:05
	 * @return 
	 * @version v0.1
	 */
	List<SysMenuDto> findMenusByRoles(String roleIds);
	List<SysMenuDto> findsMenusByRoles(Long roleId);
}
