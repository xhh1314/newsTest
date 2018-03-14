package com.hww.sys.webadmin.service.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.common.util.Updater;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.StringUtils;
import com.hww.sys.common.dto.SysModuleDto;
import com.hww.sys.common.entity.SysMenu;
import com.hww.sys.common.manager.SysMenuMng;
import com.hww.sys.common.manager.SysSiteMng;
import com.hww.sys.webadmin.dto.SysMenuDto;
import com.hww.sys.webadmin.service.SysMenuService;

@Service("sysMenuService")
@Transactional
public class SysMenuServiceImpl
	implements
		SysMenuService {

	@Resource
	SysMenuMng sysMenuMng;
	@Resource
	SysSiteMng sysSiteMng;

	@Override
	public Pagination list(SysMenuDto searchDto, Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		Finder hql = Finder.create("from ");
		hql.append(SysMenu.class.getName());
		hql.append(" where 1=1 ");

		if (searchDto != null) {
			if (searchDto.getParentId() != null) {
				hql.append(" and parent.menuId=:pMenuId ").setParam("pMenuId", searchDto.getParentId());
			}
			if (searchDto.getSiteId() != null) {
				hql.append(" and sysSite.siteId=:siteIdP").setParam("siteIdP", searchDto.getSiteId());
			}
			if (StringUtils.isNotBlank(searchDto.getMenuName())) {
				hql.append(" and menuName like :menuNameP").setParam("menuNameP", "%" + searchDto.getMenuName() + "%");
			}
			if (searchDto.getStatus() != null) {
				hql.append(" and status=:statusP").setParam("statusP", searchDto.getStatus());
			} else {
				hql.append(" and status>0");
			}
		}

		Pagination p = sysMenuMng.find(hql, pageNo, pageSize);
		if (p != null && p.getList() != null) {
			List<SysMenuDto> menuDtoList = BeanMapper.mapList(p.getList(), SysMenuDto.class);
			p.setList(menuDtoList);
		}
		return p;
	}

	@Override
	public SysMenuDto findMenuById(Long id) {
		// TODO Auto-generated method stub
		SysMenu entity = sysMenuMng.find(id);
		SysMenuDto dto = BeanMapper.map(entity, SysMenuDto.class);
		SysMenu pEntity = entity.getParent();
		dto.setParentId(pEntity.getMenuId());
		dto.setParentMenuName(pEntity.getMenuName());
		return dto;
	}

	@Override
	public List<SysMenuDto> getChildList(Long parentId, Short display) {
		Finder f = Finder.create("from ");
		f.append(SysMenu.class.getName());
		f.append(" bean");
		f.append(" where bean.parent.menuId=:parentId");
		f.setParam("parentId", parentId);

		if (display != null) {
			f.append(" and bean.display=:display");
			f.setParam("display", display);
		}
		f.append(" order by bean.priority asc,bean.menuId asc");
		List<SysMenu> menuList = (List<SysMenu>) sysMenuMng.find(f);
		List<SysMenuDto> menuDtoList = BeanMapper.mapList(menuList, SysMenuDto.class);
		return menuDtoList;
	}

	@Override
	public List<SysMenuDto> getRetrievingFullTree(Long userId, Integer siteId, Short display, Short status) {
		// TODO Auto-generated method stub
		Finder f = Finder.create("select bean from ");
		f.append(SysMenu.class.getName());
		f.append(" bean where 1=1");

		if (siteId != null) {
			f.append(" and bean.siteId=:siteIdP").setParam("siteIdP", siteId);
		}
		if (display != null) {
			f.append(" and bean.display=:displayP").setParam("displayP", display);
		}
		if (status != null) {
			f.append(" and bean.status=:statusP").setParam("statusP", status);
		}
		f.append(" order by bean.lft asc");
		List<SysMenu> menuList = (List<SysMenu>) sysMenuMng.find(f);
		List<SysMenuDto> menuDtoList = BeanMapper.mapList(menuList, SysMenuDto.class);
		return menuDtoList;
	}

	@Override
	public void saveMenu(SysMenuDto menuDto) {
		// TODO Auto-generated method stub
		SysMenu entity = BeanMapper.map(menuDto, SysMenu.class);
		SysMenu parent = sysMenuMng.find(menuDto.getParentId());
		entity.setParent(parent);
		entity.setPriority(0);
		//获取父节点
		SysMenu parentNode = sysMenuMng.find(menuDto.getParentId());
		Long myRgt = parentNode.getRgt();
		
		//在父节点末尾添加子节点
		//更新右值
		Finder updateRgtHql = Finder.create("update");
		updateRgtHql.append(SysMenu.class.getName());
		updateRgtHql.append("set rgt=rgt+2 where rgt>=:myRgt").setParam("myRgt", myRgt);
		updateRgtHql.append("where 1=1");
		if(menuDto.getSiteId()!=null) {
			updateRgtHql.append("and siteId=:siteId").setParam("siteId", menuDto.getSiteId());
		}
		if(menuDto.getModuleId()!=null) {
			updateRgtHql.append("and moduleId=:moduleId").setParam("moduleId", menuDto.getModuleId());
		}
		
		//更新左值
		Finder updateLeftHql = Finder.create("update");
		updateLeftHql.append(SysMenu.class.getName());
		updateLeftHql.append("set lft=lft+2 where lft>:myRgt").setParam("myRgt", myRgt);
		updateLeftHql.append("where 1=1");
		if(menuDto.getSiteId()!=null) {
			updateLeftHql.append("and siteId=:siteId").setParam("siteId", menuDto.getSiteId());
		}
		if(menuDto.getModuleId()!=null) {
			updateLeftHql.append("and moduleId=:moduleId").setParam("moduleId", menuDto.getModuleId());
		}
		//插入子节点
		entity.setLft(myRgt);
		entity.setRgt(myRgt+1);
		sysMenuMng.save(entity);
	}

	@Override
	public void updateMenu(SysMenuDto menuDto) {
		// TODO Auto-generated method stub
		SysMenu menu = sysMenuMng.find(menuDto.getMenuId());

		menu.setUrl(menuDto.getUrl());
		menu.setMenuName(menuDto.getMenuName());
		menu.setStatus(menuDto.getStatus());
		menu.setPriority(menuDto.getPriority());
		menu.setDisplay(menuDto.getDisplay());
		menu.setIcon(menuDto.getIcon());

		if (menu.getParentId() != menuDto.getParentId()) {
			SysMenu parent = sysMenuMng.find(menuDto.getParentId());
			menu.setParent(parent);
		}

		// 去掉不需要更新的属性
		Updater<SysMenu> updater = new Updater<SysMenu>(menu);
		updater.exclude(menu.getLftName()).exclude(menu.getRgtName());
		sysMenuMng.update(updater);
	}

	@Override
	public void updateMenuStatusByIds(Short status, Collection<Long> menuIds) {
		// TODO Auto-generated method stub
		sysMenuMng.updateStatusByProperty(status, "menuId", menuIds);
	}

	@Override
	public List<SysMenuDto> getModuleChildList(Long moduleId, Short display) {
			Finder finder=Finder.create(" from SysMenu ");
			finder.append("where 1=1");
			if(moduleId!=null) {
			finder.append(" and moduleId=:moduleId ").setParam("moduleId", moduleId);
			}
			return (List<SysMenuDto>)sysMenuMng.find(finder);
		}

	

}
