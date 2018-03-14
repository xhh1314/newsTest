//package com.hww.app.admin.service.impl;
//
//
//import java.util.ArrayList;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import com.hww.base.common.util.Finder;
//import com.hww.app.admin.service.SysRoleService;
//import com.hww.sys.common.manager.SysMenuMng;
//import com.hww.sys.common.manager.SysRoleMng;
//
//
//@Service("sysRoleService")
//@Transactional
//public class SysRoleServiceImpl implements SysRoleService {
//
//	@Autowired
//	SysRoleMng sysRoleMng;
//	@Autowired
//	SysMenuMng sysMenuMng;
//	
//
//
//	public List<SysMenu> findsMenusByRoles(Long roleId) {
//
//		List<SysMenu> menu = new ArrayList<SysMenu>();
//		SysRole role= sysRoleMng.find(roleId);
//		if (role!=null) {	
//				List<SysMenu> menus = role.getSysMenus();
//				menu.addAll(menus);
//		}
//
//		
//		return menu;
//	}
//	public List<SysRole> findRole() {
//		Finder finder=Finder.create(" from SysRole");
//		return (List<SysRole>) sysRoleMng.find(finder);
//	}
//
//	
//	
//
//}
