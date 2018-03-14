//package com.hww.ucenter.webadmin.listener;
//
//import com.hww.base.common.listener.ModifyListenerAdapter;
//import com.hww.sys.common.entity.SysMenu;
//import com.hww.sys.common.entity.SysSite;
//import com.hww.sys.common.manager.SysMenuMng;
//
//import javax.annotation.Resource;
//
//public class SiteMenuModifyListener extends ModifyListenerAdapter<SysSite> {
//	@Resource
//	SysMenuMng sysMenuMng;
//
//	@Override
//	public void afterSave(SysSite entity) {
//		// TODO Auto-generated method stub
//		super.afterSave(entity);
//		SysMenu rootMenu = new SysMenu();
//		rootMenu.setParent(null);
//		rootMenu.setMenuName("菜单");
//		rootMenu.setSiteId(entity.getSiteId());
//		rootMenu.setLft(1L);
//		rootMenu.setRgt(2L);
//		rootMenu.setDisplay((short) 1);
//		rootMenu.setStatus((short) 1);
//		sysMenuMng.save(rootMenu);
//	}
//
//}
