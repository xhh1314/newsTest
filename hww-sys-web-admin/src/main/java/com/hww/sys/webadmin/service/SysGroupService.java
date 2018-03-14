package com.hww.sys.webadmin.service;

import java.util.Collection;
import java.util.List;

import com.hww.base.common.page.Pagination;
import com.hww.sys.common.dto.SysGroupDto;
import com.hww.sys.webadmin.dto.SysMenuDto;

public interface SysGroupService {
	
	Pagination list(SysGroupDto searchDto, Integer pageNo, Integer pageSize);
	
	SysGroupDto findGroupById(Long id);
	
	SysGroupDto findGroupWithMenuById(Long id);
	
	void saveGroup(SysGroupDto dto);
	
	void updateGroup(SysGroupDto dto);

	void updateGroupOne(SysGroupDto dto);
	
	void updateGroupStatusByIds(Short status, Collection<Long> groupIds);

	/**
	 * 根据多个分组查询用户权限
	 *
	 * @author Administrator
	 * @email xlfbe696@gmail.com
	 * @date 2017年11月14日 下午3:35:36
	 * @return 
	 * @version v0.1
	 */
	List<SysMenuDto> findMenusByGroups(String groupIds);
}
