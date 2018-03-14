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
import com.hww.sys.common.dto.SysGroupDto;
import com.hww.sys.common.util.SysUtils;
import com.hww.sys.common.vo.SysGroupVo;
import com.hww.sys.webadmin.Constants;
import com.hww.sys.webadmin.service.SysGroupService;

@Controller
@RequestMapping("/group")
public class SysGroupController
	extends
		AbsBaseController {
	@Resource
	private SysGroupService sysGroupService;

	@RequestMapping(value = "index.do")
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "group/index";
	}

	@RequestMapping("v_list.do")
	public String list(HttpServletRequest request, SysGroupVo vo, ModelMap model) {
		Integer siteId = SysUtils.getSiteId(request);
		SysGroupDto searchDto = new SysGroupDto();
		searchDto.setSiteId(siteId);
		Pagination p = sysGroupService.list(searchDto, vo.getPageNo(), 10);
		if (p != null && p.getList() != null) {
			List<SysGroupVo> groupVoList = BeanMapper.mapList(p.getList(), SysGroupVo.class);
			p.setList(groupVoList);
		}

		model.addAttribute("page", p);
		model.addAttribute("form", vo);
		return "group/list";
	}

	@RequestMapping("v_add.do")
	public String add(HttpServletRequest request) {
		return "group/add";
	}

	@RequestMapping("v_edit.do")
	public String edit(HttpServletRequest request, HttpServletResponse response, SysGroupVo vo, ModelMap model) {
		SysGroupDto groupDto = sysGroupService.findGroupById(vo.getGroupId());
		SysGroupVo groupVo = BeanMapper.map(groupDto, SysGroupVo.class);
		model.addAttribute("entity", groupVo);
		return "group/edit";
	}

	@RequestMapping("o_save.do")
	@ResponseBody
	public Boolean save(HttpServletRequest request, @Valid @ModelAttribute("form") SysGroupVo form,
			BindingResult errors) {
		if (errors.hasErrors()) {
			return false;
		}
		Integer siteId = SysUtils.getSiteId(request);
		SysGroupDto groupDto = BeanMapper.map(form, SysGroupDto.class);
		groupDto.setSiteId(siteId);
		sysGroupService.saveGroup(groupDto);
		return true;
	}

	@RequestMapping("o_update.do")
	@ResponseBody
	public Boolean update(@Valid @ModelAttribute("form") SysGroupVo form, BindingResult errors) {
		if (errors.hasErrors()) {
			return false;
		}
		SysGroupDto groupDto = BeanMapper.map(form, SysGroupDto.class);
		if (groupDto != null) {
			sysGroupService.updateGroupOne(groupDto);
			return true;
		} else {
			return false;
		}
	}

	@RequestMapping(value = "v_menutree_checkbox.do")
	public String treecheckbox(HttpServletRequest request, HttpServletResponse response, SysGroupVo vo,
			ModelMap model) {
		SysGroupDto groupDto = sysGroupService.findGroupWithMenuById(vo.getGroupId());
		if (groupDto != null) {
			model.addAttribute("menuIds", groupDto.getMenuIds());
		}
		model.addAttribute("form", vo);
		SysGroupVo groupVo = BeanMapper.map(groupDto, SysGroupVo.class);
		model.addAttribute("entity", groupVo);
		return "group/treecheckbox";

	}

	@RequestMapping("o_update_per.do")
	@ResponseBody
	public Boolean updatePer(HttpServletRequest request, HttpServletResponse response, Long groupId,
			String menuIds) {
		SysGroupDto groupDto = new SysGroupDto();
		groupDto.setGroupId(groupId);
		groupDto.setMenuIds(menuIds);
		sysGroupService.updateGroup(groupDto);
		return true;
	}
	@RequestMapping("o_delete.do")
	public String delete(HttpServletRequest request, HttpServletResponse response, SysGroupVo form) {
		Collection<Long> groupIds = null;
		if (form != null) {

			if (StringUtils.isNotBlank(form.getAllIDCheck())) {
				String[] ids = StringUtils.split(form.getAllIDCheck(), ",");
				if (ArrayUtils.isNotEmpty(ids)) {
					groupIds = new ArrayList<Long>(ids.length);
				}
				for (String str : ids) {
					if (StringUtils.isNumeric(str)) {
						groupIds.add(Long.parseLong(str));
					}
				}
			}
			if (form.getGroupId() != null) {
				groupIds = new ArrayList<Long>(1);
				groupIds.add(form.getGroupId());
			}
		}
		if (groupIds != null) {
			sysGroupService.updateGroupStatusByIds(Constants.GROUP_STATUS_DELETED, groupIds);
		}
		return "redirect:v_list.do";
	}
}
