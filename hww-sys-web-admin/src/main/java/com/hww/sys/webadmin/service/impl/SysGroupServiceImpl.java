package com.hww.sys.webadmin.service.impl;

import java.awt.datatransfer.StringSelection;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.list.SetUniqueList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.ArrayListUtils;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.BeanUtils;
import com.hww.base.util.StringUtils;
import com.hww.sys.common.dto.SysGroupDto;
import com.hww.sys.common.entity.SysGroup;
import com.hww.sys.common.entity.SysMenu;
import com.hww.sys.common.manager.SysGroupMng;
import com.hww.sys.common.manager.SysMenuMng;
import com.hww.sys.webadmin.dto.SysMenuDto;
import com.hww.sys.webadmin.service.SysGroupService;

@Service("sysGroupService")
@Transactional
public class SysGroupServiceImpl implements SysGroupService {

	@Autowired
	SysGroupMng sysGroupMng;
	@Autowired
	SysMenuMng sysMenuMng;

	
	public Pagination list(SysGroupDto searchDto, Integer pageNo,
			Integer pageSize) {
		// TODO Auto-generated method stub
		Finder hql = Finder.create("from ");
		hql.append(SysGroup.class.getName());
		hql.append(" where 1=1");
		if (searchDto.getSiteId() != null) {
			hql.append(" and siteId=:siteIdP").setParam("siteIdP",
					searchDto.getSiteId());
		}
		if (StringUtils.isNotBlank(searchDto.getGroupName())) {
			hql.append(" and groupName like :groupNameP").setParam("groupNameP",
					"%" + searchDto.getGroupName() + "%");
		}
		if (searchDto.getStatus() != null) {
			hql.append(" and status=:statusP").setParam("statusP",
					searchDto.getStatus());
		} else {
			hql.append(" and status>0");
		}

		Pagination p = sysGroupMng.find(hql, pageNo, pageSize);
		if (p != null && p.getList() != null) {
			List<SysGroupDto> groupDtoList = BeanMapper.mapList(p.getList(),
					SysGroupDto.class);
			p.setList(groupDtoList);
		}

		return p;
	}

	
	public SysGroupDto findGroupById(Long id) {
		// TODO Auto-generated method stub
		SysGroup entity = sysGroupMng.find(id);
		SysGroupDto dto = BeanMapper.map(entity, SysGroupDto.class);
		return dto;
	}

	
	public SysGroupDto findGroupWithMenuById(Long id) {
		// TODO Auto-generated method stub
		SysGroup entity = sysGroupMng.find(id);
		StringBuffer menuIds = new StringBuffer();
		for (SysMenu menu : entity.getSysMenus()) {
			menuIds.append(menu.getMenuId());
			menuIds.append(",");
		}
		SysGroupDto dto = BeanMapper.map(entity, SysGroupDto.class);
		dto.setMenuIds(menuIds.toString());

		return dto;
	}

	
	public void saveGroup(SysGroupDto dto) {
		// TODO Auto-generated method stub
		SysGroup entity = BeanMapper.map(dto, SysGroup.class);
		sysGroupMng.save(entity);
	}
	public void updateGroup(SysGroupDto dto) {
		// TODO Auto-generated method stub
		SysGroup entity = sysGroupMng.find(dto.getGroupId());
		try {
			BeanUtils.copyProperty(entity,"", dto);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (StringUtils.isNotBlank(dto.getMenuIds())) {
			List<SysMenu> menus = null;
			String[] menuIdStrArray = dto.getMenuIds().split(",");
			Object[] menuIdIntArray = new Object[menuIdStrArray.length];
			for (int i = 0; i < menuIdStrArray.length; i++) {
				if (StringUtils.isNumeric(menuIdStrArray[i])) {
					menuIdIntArray[i]=(Long.parseLong(menuIdStrArray[i]));
				}
			}
			menus = sysMenuMng.find("menuId", menuIdIntArray);
			entity.setSysMenus(menus);
		}
		sysGroupMng.update(entity);
	}

	public void updateGroupStatusByIds(Short status,Collection<Long> groupIds) {
		sysGroupMng.updateStatusByProperty(status, "groupId", groupIds);
	}
	@Override
	public List<SysMenuDto> findMenusByGroups(String groupIds) {

		List<SysMenu> menu = new ArrayList<>();

		String[] ids = groupIds.split(",");
		Object[] groupIdArray = new Object[ids.length];

		for (int i = 0; i < ids.length; i++) {

			if (StringUtils.isNumeric(ids[i])) {
				groupIdArray[i] = (Long.parseLong(ids[i]));
			}

		}

		List<SysGroup> groupList = sysGroupMng.find("groupId", groupIdArray);

		if (groupList.size() > 0) {
			for (SysGroup sysGroup : groupList) {
				List<SysMenu> menus = sysGroup.getSysMenus();
				menu.addAll(menus);
			}
		}

		List menuList = ArrayListUtils.removeDuplicateWithOrder(menu);
		return BeanMapper.mapList(menuList, SysMenuDto.class);

	}


	@Override
	public void updateGroupOne(SysGroupDto dto) {
		SysGroup entity = BeanMapper.map(dto, SysGroup.class);
		entity.setSiteId(1);
		sysGroupMng.update(entity);
		
	}

}
