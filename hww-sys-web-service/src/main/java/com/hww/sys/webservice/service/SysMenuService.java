package com.hww.sys.webservice.service;

import java.util.Collection;
import java.util.List;

import com.hww.base.common.page.Pagination;
import com.hww.sys.common.dto.SysMenuDto;

public interface SysMenuService {

	Pagination list(SysMenuDto searcheDto, Integer pageNo, Integer pageSize);

	SysMenuDto findMenuById(Long id);

	List<SysMenuDto> getChildList(Long parentId, Short display);
	List<SysMenuDto> getModuleChildList(Long moduleId, Short display);

	// 返回完整的树
	List<SysMenuDto> getRetrievingFullTree(Long userId, Integer siteId, Short display, Short status);

	//
	void saveMenu(SysMenuDto menuDto);

	void updateMenu(SysMenuDto menuDto);

	void updateMenuStatusByIds(Short status, Collection<Long> menuIds);
}
