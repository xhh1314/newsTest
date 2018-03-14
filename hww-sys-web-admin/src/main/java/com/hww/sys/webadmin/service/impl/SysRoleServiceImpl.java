package com.hww.sys.webadmin.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.ArrayListUtils;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.BeanUtils;
import com.hww.base.util.StringUtils;
import com.hww.sys.common.dto.SysRoleDto;
import com.hww.sys.common.entity.SysMenu;
import com.hww.sys.common.entity.SysRole;
import com.hww.sys.common.manager.SysMenuMng;
import com.hww.sys.common.manager.SysRoleMng;
import com.hww.sys.webadmin.dto.SysMenuDto;
import com.hww.sys.webadmin.service.SysRoleService;

@Service("sysRoleService")
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	SysRoleMng sysRoleMng;
	@Autowired
	SysMenuMng sysMenuMng;

	public Pagination list(SysRoleDto searchDto, Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		Finder hql = Finder.create("from ");
		hql.append(SysRole.class.getName());
		hql.append(" where 1=1");
		if (searchDto.getSiteId() != null) {
			hql.append(" and siteId=:siteIdP").setParam("siteIdP", searchDto.getSiteId());
		}
		if (StringUtils.isNotBlank(searchDto.getRoleName())) {
			hql.append(" and roleName like :roleNameP").setParam("roleNameP", "%" + searchDto.getRoleName() + "%");
		}
		if (searchDto.getStatus() != null) {
			hql.append(" and status=:statusP").setParam("statusP", searchDto.getStatus());
		} else {
			hql.append(" and status>=0");
		}

		Pagination p = sysRoleMng.find(hql, pageNo, pageSize);
		if (p != null && p.getList() != null) {
			List<SysRoleDto> roleDtoList = BeanMapper.mapList(p.getList(), SysRoleDto.class);
			p.setList(roleDtoList);
		}

		return p;
	}

	@Override
	public SysRoleDto findRoleById(Long id) {
		// TODO Auto-generated method stub
		SysRole entity = sysRoleMng.find(id);
		SysRoleDto dto = BeanMapper.map(entity, SysRoleDto.class);
		return dto;
	}

	@Override
	public SysRoleDto findRoleWithMenuById(Long id) {
		// TODO Auto-generated method stub
		SysRole entity = sysRoleMng.find(id);
		StringBuffer menuIds = new StringBuffer();
		
		for (SysMenu menu : entity.getSysMenus()) {
			menuIds.append(menu.getMenuId());
			menuIds.append(",");
		}

		SysRoleDto dto = BeanMapper.map(entity, SysRoleDto.class);
		dto.setMenuIds(menuIds.toString());
		return dto;
	}

	@Override
	@Transactional
	public void saveRole(SysRoleDto dto) {
		// TODO Auto-generated method stub
		SysRole entity = BeanMapper.map(dto, SysRole.class);
		entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
		entity.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
		sysRoleMng.save(entity);
	}

	@Override
	public void updateRole(SysRoleDto dto) {
		// TODO Auto-generated method stub
		dto.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
		SysRole entity = sysRoleMng.find(dto.getRoleId());
		try {
			BeanUtils.copyProperty(entity, "", dto);
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
			// ArrayList<Integer> menuIdIntArray = new
			// ArrayList<Integer>(menuIdStrArray.length);
			for (int i = 0; i < menuIdStrArray.length; i++) {
				if (StringUtils.isNumeric(menuIdStrArray[i])) {
					menuIdIntArray[i] = (Long.parseLong(menuIdStrArray[i]));
				}
			}
			menus = sysMenuMng.find("menuId", menuIdIntArray);
			entity.setSysMenus(menus);
		}

		sysRoleMng.update(entity);
	}

	@Override
	public void updateRoleStatusByIds(Short status, Collection<Long> roleIds) {
		// TODO Auto-generated method stub
		sysRoleMng.updateStatusByProperty(status, "roleId", roleIds);
	}

	@Override
	public List<SysMenuDto> findMenusByRoles(String roleIds) {
		List<SysMenu> menu = new ArrayList<>();
		String[] ids = roleIds.split(",");
		Object[] roleIdArray = new Object[ids.length];
		for (int i = 0; i < ids.length; i++) {
			if (StringUtils.isNumeric(ids[i])) {
				roleIdArray[i] = (Long.parseLong(ids[i]));
			}

		}
		List<SysRole> roleList = sysRoleMng.find("roleId", roleIdArray);
		if (roleList.size() > 0) {
			for (SysRole sysRole : roleList) {
				List<SysMenu> menus = sysRole.getSysMenus();
				menu.addAll(menus);
			}
		}
		List menuList = ArrayListUtils.removeDuplicateWithOrder(menu);
		return BeanMapper.mapList(menuList, SysMenuDto.class);
	}

	public void updateRoleOne(SysRoleDto dto) {
		SysRole entity = sysRoleMng.find(dto.getRoleId());
		try {
			BeanUtils.copyProperties(entity, dto);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sysRoleMng.update(entity);

	}

	@Override
	public List<SysRole> allList() {
		// TODO Auto-generated method stub
		Finder hql = Finder.create("from SysRole bean");
		hql.append(" where 1=1");
		hql.append(" and bean.status>0");

		List<SysRole> list = (List<SysRole>) sysRoleMng.find(hql);

		for (SysRole s : list) {
			System.out.println("---------------" + s.getId());
		}
		return list;
	}

	public List<SysMenuDto> findsMenusByRoles(Long roleId) {
		List<SysMenu> menu = new ArrayList<SysMenu>();
		SysRole role = sysRoleMng.find(roleId);
		if (role != null) {
			List<SysMenu> menus = role.getSysMenus();
			menu.addAll(menus);
		}

		List<SysMenuDto> menus = BeanMapper.mapList(menu, SysMenuDto.class);
		return menus;
	}

	@Override
	public void delRole(Long roleId) {
		Finder hql = Finder.create("update SysRole bean");
		hql.append(" set status=:status").setParam("status", Short.valueOf("-1"));
		hql.append(" where roleId=:roleId").setParam("roleId", roleId);
		sysRoleMng.update(hql);
	}

}
