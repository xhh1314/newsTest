package com.hww.sys.webservice.service;

import java.util.Collection;

import com.hww.base.common.page.Pagination;
import com.hww.sys.common.dto.SysGroupDto;

public interface SysGroupService {
	
	Pagination list(SysGroupDto searchDto, Integer pageNo, Integer pageSize);
	
	SysGroupDto findGroupById(Long id);
	
	SysGroupDto findGroupWithMenuById(Long id);
	
	void saveGroup(SysGroupDto dto);
	
	void updateGroup(SysGroupDto dto);

	void updateGroupOne(SysGroupDto dto);
	
	void updateGroupStatusByIds(Short status, Collection<Long> groupIds);

}
