package com.hww.sys.webadmin.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hww.base.common.page.Pagination;
import com.hww.base.util.ArrayUtils;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.StringUtils;
import com.hww.framework.web.mvc.controller.AbsBaseController;
import com.hww.sys.common.dto.SysRoleDto;
import com.hww.sys.common.entity.SysRole;
import com.hww.sys.common.util.SysUtils;
import com.hww.sys.common.vo.SysRoleVo;
import com.hww.sys.webadmin.Constants;
import com.hww.sys.webadmin.service.SysRoleService;

@Controller
@RequestMapping("/role")
public class SysRoleController extends AbsBaseController {
	@Resource
	private SysRoleService sysRoleService;

	@RequestMapping(value = "index.do")
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "role/index";
	}

	@RequestMapping("v_list.do")
	public String list(HttpServletRequest request, SysRoleVo vo, ModelMap model) {
		Integer siteId = SysUtils.getSiteId(request);
		SysRoleDto searchDto = new SysRoleDto();
		searchDto.setSiteId(siteId);

		Pagination p = sysRoleService.list(searchDto, vo.getPageNo(), 10);

		if (p != null && p.getList() != null) {
			List<SysRoleVo> roleVoList = BeanMapper.mapList(p.getList(), SysRoleVo.class);
			p.setList(roleVoList);
		}
		model.addAttribute("page", p);
		model.addAttribute("form", vo);
		return "role/list";
	}

	@RequestMapping("v_all_list.do")
	@ResponseBody
	public List<SysRole> allList(HttpServletRequest request, HttpServletResponse response) {
		return sysRoleService.allList();
	}

	@RequestMapping("v_add.do")
	public String add(HttpServletRequest request) {
		return "role/add";
	}

	@RequestMapping("v_edit.do")
	public String edit(HttpServletRequest request, HttpServletResponse response, SysRoleVo vo, ModelMap model) {
		SysRoleDto roleDto = sysRoleService.findRoleById(vo.getRoleId());
		SysRoleVo roleVo = BeanMapper.map(roleDto, SysRoleVo.class);
		model.addAttribute("entity", roleVo);
		return "role/edit";
	}

	@RequestMapping("o_save.do")
	@ResponseBody
	public Boolean save(HttpServletRequest request, @Valid @ModelAttribute("form") SysRoleVo form,
			BindingResult errors) {
		if (errors.hasErrors()) {
			return false;
		}
		Integer siteId = SysUtils.getSiteId(request);
		SysRoleDto roleDto = BeanMapper.map(form, SysRoleDto.class);
		roleDto.setSiteId(siteId);
		sysRoleService.saveRole(roleDto);
		return true;
	}

	@RequestMapping("o_update.do")
	@ResponseBody
	public Boolean update(@Valid @ModelAttribute("form") SysRoleVo form, BindingResult errors) {
		if (errors.hasErrors()) {
			return false;
		}
		SysRoleDto roleDto = sysRoleService.findRoleById(form.getRoleId());
		roleDto.setRoleName(form.getRoleName());
		roleDto.setStatus(form.getStatus());
		if (roleDto != null) {
			sysRoleService.updateRoleOne(roleDto);
			return true;
		} else {
			return false;
		}
	}

	@RequestMapping(value = "v_menutree_checkbox.do")
	public String treecheckbox(HttpServletRequest request, HttpServletResponse response, SysRoleVo vo, ModelMap model) {
		SysRoleDto roleDto = sysRoleService.findRoleWithMenuById(vo.getRoleId());
		if (roleDto != null) {
			model.addAttribute("menuIds", roleDto.getMenuIds());
		}
		model.addAttribute("form", vo);
		SysRoleVo roleVo = BeanMapper.map(roleDto, SysRoleVo.class);
		model.addAttribute("entity", roleVo);
		return "role/treecheckbox";

	}

	@RequestMapping("o_update_per.do") // 更新权限
	@ResponseBody
	public Boolean updatePer(HttpServletRequest request, HttpServletResponse response, Long roleId, String menuIds) {
		SysRoleDto roleDto = new SysRoleDto();
		roleDto.setRoleId(roleId);
		roleDto.setMenuIds(menuIds);
		sysRoleService.updateRole(roleDto);
		return true;
	}

	@RequestMapping("o_delete.do")
	public String delete(HttpServletRequest request, HttpServletResponse response, SysRoleVo vo) {
		Collection<Long> roleIds = null;
		if (vo != null) {
			if (StringUtils.isNotBlank(vo.getAllIDCheck())) {
				String[] ids = StringUtils.split(vo.getAllIDCheck(), ",");
				if (ArrayUtils.isNotEmpty(ids)) {
					roleIds = new ArrayList<Long>(ids.length);
				}
				for (String str : ids) {
					if (StringUtils.isNumeric(str)) {
						roleIds.add(Long.parseLong(str));
					}
				}
			}
			if (vo.getRoleId() != null) {
				roleIds = new ArrayList<Long>(1);
				roleIds.add(vo.getRoleId());
			}
		}
		if (roleIds != null) {
			sysRoleService.updateRoleStatusByIds(Constants.ROLE_STATUS_DELETED, roleIds);
		}
		return "redirect:v_list.do";
	}
	
	
	
	

	@RequestMapping("o_del.do")
	public String del(Long roleId) {
		sysRoleService.delRole(roleId);
		return "redirect:v_list.do";
		
	}
	
	
	
	
	
	
	
	
}
