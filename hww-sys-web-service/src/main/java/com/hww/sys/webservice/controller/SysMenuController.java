package com.hww.sys.webservice.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.vo.BaseTreeVo;
import com.hww.base.util.ArrayUtils;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.StringUtils;
import com.hww.sys.common.dto.SysMenuDto;
import com.hww.sys.common.dto.SysModuleDto;
import com.hww.sys.common.dto.SysSiteDto;
import com.hww.sys.common.util.SysUtils;
import com.hww.sys.common.vo.SysMenuVo;
import com.hww.sys.common.vo.SysModuleVo;
import com.hww.sys.common.vo.SysSiteVo;
import com.hww.sys.webadmin.Constants;
import com.hww.sys.webservice.service.SysMenuService;
import com.hww.sys.webservice.service.SysModuleService;
import com.hww.sys.webservice.service.SysSiteService;

@Controller
@RequestMapping("/menu")
public class SysMenuController{
	private static final Logger log = LoggerFactory.getLogger(SysMenuController.class);

	@Resource
	SysMenuService sysMenuService;

	@Resource
	SysSiteService sysSiteService;
	@Resource
	SysModuleService sysModuleService;

	@RequestMapping(value = "index.do")
	public String index() {
		return "menu/menu_index";
	}
	@RequestMapping("v_add.do")
	public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model, SysMenuVo vo) {
		SysMenuDto parent = sysMenuService.findMenuById(vo.getParentId());
		SysMenuVo parentVo = BeanMapper.map(parent, SysMenuVo.class);
		List<SysSiteDto> siteDtos = (List<SysSiteDto>) sysSiteService.list(null, 1, 10);
		List<SysSiteVo> siteVos = BeanMapper.mapList(siteDtos, SysSiteVo.class);
		model.addAttribute("sites", siteVos);
		vo.setSiteId(1L);
		model.addAttribute("form", vo);
		model.addAttribute("parent", parentVo);
		return "menu/add";
	}
	@RequestMapping("v_menu_list.do")
	public String menuList(HttpServletRequest request, HttpServletResponse response, ModelMap model, SysMenuVo vo) {
		SysMenuDto menuDto = sysMenuService.findMenuById(vo.getMenuId());
		SysMenuVo menuVo = BeanMapper.map(menuDto, SysMenuVo.class);
		List<SysMenuDto> sysMenuList= sysMenuService.getChildList(menuVo.getMenuId(), (short) 1);
		model.addAttribute("page", sysMenuList);
		model.addAttribute("parent", menuVo);
		return "menu/show_menu";
	}
	
	@RequestMapping("v_menu_lists.do")
	public String menuLists(HttpServletRequest request, HttpServletResponse response, ModelMap model, Long moduleId) {
		SysModuleDto sysModuleDto=sysModuleService.queryModelById(moduleId);
		SysModuleVo sysModuleVo = BeanMapper.map(sysModuleDto, SysModuleVo.class);
		List<SysMenuDto> sysMenuList=sysMenuService.getModuleChildList(moduleId, (short)1);
		//List<SysMenuDto> sysMenuList= sysMenuService.getChildList(sysModuleDto.getModuleId(), (short) 1);
		model.addAttribute("page", sysMenuList);
		model.addAttribute("parent", sysModuleVo);
		return "menu/menu_index";
	}
	@RequestMapping(value = "v_tree.do")
	public String tree(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "menu/treemain";
	}

	@RequestMapping(value = "toedit.do")
	public String toedit(HttpServletRequest request, HttpServletResponse response, Long menuId, ModelMap model) {
		SysMenuDto sysMenuDto=sysMenuService.findMenuById(menuId);
		/*SysMenuVo menuVo = BeanMapper.map(sysMenuDto, SysMenuVo.class);
		model.addAttribute("menu",menuVo);
		JSONObject array = JSONArray.parseObject(menuVo);*/
		return "menu/edit";

	}
	
	
	
	
	@RequestMapping(value = "v_edit.do")
	public String edit(HttpServletRequest request, HttpServletResponse response, SysMenuVo vo, ModelMap model) {
		SysMenuDto menuDto = sysMenuService.findMenuById(vo.getMenuId());
		SysMenuVo menuVo = BeanMapper.map(menuDto, SysMenuVo.class);
		List<SysSiteDto> siteDtos = (List<SysSiteDto>) sysSiteService.list(null, 1, 10);
		List<SysSiteVo> siteVos = BeanMapper.mapList(siteDtos, SysSiteVo.class);
		model.addAttribute("entity", menuVo);
		model.addAttribute("sites", siteVos);
		model.addAttribute("form", vo);
		return "menu/edit";

	}

	@RequestMapping("o_update.do")
	@ResponseBody
	public Boolean update(@Valid @ModelAttribute("form") SysMenuVo form, BindingResult errors) {
		if (errors.hasErrors()) {
			return false;
		}
		SysMenuDto menuDto = BeanMapper.map(form, SysMenuDto.class);
		sysMenuService.updateMenu(menuDto);
		return true;
	}

	@RequestMapping("o_save.do")
	@ResponseBody
	public Boolean save(HttpServletRequest request, @Valid @ModelAttribute("form") SysMenuVo form,
			BindingResult errors) {
		if (errors.hasErrors()) {
			return false;
		}
		SysMenuDto menuDto = BeanMapper.map(form, SysMenuDto.class);
		Integer siteId = SysUtils.getSiteId(request);// sysSiteService.findSiteById(1);
		menuDto.setSiteId(1);
		sysMenuService.saveMenu(menuDto);
		return true;
	}

	@RequestMapping(value = "v_list.do")
	public String subList(HttpServletRequest request, HttpServletResponse response, ModelMap model, SysMenuVo vo) {
		Integer siteId = SysUtils.getSiteId(request);
		SysMenuDto search = new SysMenuDto();
		search.setSiteId(siteId);
		Pagination p = sysMenuService.list(search, 1, 20);
		if (p != null && p.getList() != null) {
			List<SysMenuVo> menuVoList = BeanMapper.mapList(p.getList(), SysMenuVo.class);
			p.setList(menuVoList);
		}
		model.addAttribute("page", p);
		model.addAttribute("form", vo);
		return "menu/sublist";

	}

	@RequestMapping(value = "enableMenuJson.do")
	@ResponseBody
	public List<BaseTreeVo> enableMenuJson(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			Integer node) {
		if (node == null) {
			node = 0;
		}
		List<BaseTreeVo> tree = new ArrayList<BaseTreeVo>();
		Integer siteId = SysUtils.getSiteId(request);
		Long userId = SysUtils.getUserId(request);
		List<SysMenuDto> list = sysMenuService.getRetrievingFullTree(userId, siteId, (short) 1, (short) 1);
		for (SysMenuDto menuDto : list) {
			BaseTreeVo m = new BaseTreeVo();
			m.setId(menuDto.getMenuId());
			if (menuDto.getMenuId() == 1) {
				m.setOpen(true);
			}
			if (menuDto.getRgt() - menuDto.getLft() == 1) {
				m.setIsParent(false);
			} else {
				m.setIsParent(true);
			}
			if (menuDto.getParentId() != null) {
				m.setpId(menuDto.getParentId());
			} else {
				m.setpId(0L);
			}
			m.setName(menuDto.getMenuName());
			m.setAccessPath(menuDto.getUrl());
			m.setChecked(false);
			tree.add(m);
		}
		return tree;

	}

	@RequestMapping(value = "allMenuJson.do")
	@ResponseBody
	public List<BaseTreeVo> allMenuJson(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			Integer node, String cMenuIds,Long ModuleId) {
		log.info("打开菜单列表页");
		if (node == null) {
			node = 0;
		}
		String[] menuIds = null;
		Set<Integer> menuIdSet = null;
		if (StringUtils.isNotBlank(cMenuIds)) {
			menuIds = cMenuIds.substring(0, cMenuIds.length() - 1).split(",");
			menuIdSet = new HashSet<Integer>(menuIds.length);
			for (String menuId : menuIds) {
				menuIdSet.add(Integer.parseInt(menuId));
			}
		}
		Integer siteId = SysUtils.getSiteId(request);
		Long userId = SysUtils.getUserId(request);
		List<SysMenuDto> list = sysMenuService.getRetrievingFullTree(userId, siteId, null, null);
		List<BaseTreeVo> tree = new ArrayList<BaseTreeVo>();
		for (SysMenuDto menuDto : list) {
			BaseTreeVo m = new BaseTreeVo();
			m.setId(menuDto.getMenuId());
			if (menuDto.getRgt() - menuDto.getLft() == 1 && menuDto.getParentId() != null) {
				m.setIsParent(false);
			} else {
				m.setIsParent(true);
			}
			if (menuDto.getParentId() != null) {
				m.setpId(menuDto.getParentId());
			} else {
				m.setpId(0L);
			}
			m.setName(menuDto.getMenuName());
			m.setAccessPath(menuDto.getUrl());
			if (menuIdSet != null && menuIdSet.contains(menuDto.getMenuId())) {
				m.setChecked(true);
			} else {
				m.setChecked(false);
			}
			tree.add(m);
		}
		return tree;

	}
	
	@RequestMapping(value = "allMenuJson_tree.do")
	@ResponseBody
	public List<BaseTreeVo> allMenuJsonTree(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			Long moduleId) {
		log.info("打开菜单列表页");
		String[] menuIds = null;
		Set<Integer> menuIdSet = null;
		List<SysMenuDto> list=sysMenuService.getModuleChildList(moduleId, (short)1);
		List<SysMenuVo> sysMenuVo= BeanMapper.mapList(list, SysMenuVo.class);
		List<BaseTreeVo> tree = new ArrayList<BaseTreeVo>();
		for (SysMenuVo menuDto : sysMenuVo) {
			BaseTreeVo m = new BaseTreeVo();
			m.setId(menuDto.getMenuId());
			if (menuDto.getRgt() - menuDto.getLft() == 1 && menuDto.getParentId() != null) {
				m.setIsParent(false);
			} else {
				m.setIsParent(true);
			}
			if (menuDto.getParentId() != null) {
				m.setpId(menuDto.getParentId());
			} else {
				m.setpId(0L);
			}
			m.setName(menuDto.getMenuName());
			m.setAccessPath(menuDto.getUrl());
			if (menuIdSet != null && menuIdSet.contains(menuDto.getMenuId())) {
				m.setChecked(true);
			} else {
				m.setChecked(false);
			}
			tree.add(m);
		}
		return tree;

	}

	@RequestMapping("o_delete.do")
	public String delete(HttpServletRequest request, HttpServletResponse response, SysMenuVo vo) {
		Collection<Long> menuIds = null;
		if (vo != null) {
			if (StringUtils.isNotBlank(vo.getAllIDCheck())) {
				String[] ids = StringUtils.split(vo.getAllIDCheck(), ",");
				if (ArrayUtils.isNotEmpty(ids)) {
					menuIds = new ArrayList<Long>(ids.length);
				}
				for (String str : ids) {
					if (StringUtils.isNumeric(str)) {
						menuIds.add(Long.parseLong(str));
					}
				}
			}
			if (vo.getMenuId() != null) {
				menuIds = new ArrayList<Long>(1);
				menuIds.add(vo.getMenuId());
			}
		}
		if (menuIds != null) {
			sysMenuService.updateMenuStatusByIds(Short.valueOf("-1"), menuIds);
		}

		return "redirect:v_list.do?parentId=" + vo.getParentId();
	}
}
