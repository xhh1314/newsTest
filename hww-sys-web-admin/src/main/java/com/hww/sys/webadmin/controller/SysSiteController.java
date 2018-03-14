package com.hww.sys.webadmin.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.hww.sys.common.dto.SysSiteDto;
import com.hww.sys.common.vo.SysSiteVo;
import com.hww.sys.webadmin.Constants;
import com.hww.sys.webadmin.service.SysSiteService;

@Controller
@RequestMapping("/site")
public class SysSiteController
	extends
		AbsBaseController {
	@Autowired
	private SysSiteService SysSiteService;

	@RequestMapping(value = "index.do")
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "site/index";
	}

	@RequestMapping("v_list.do")
	public String list(HttpServletRequest request, SysSiteVo vo, ModelMap model) {
		SysSiteDto search = BeanMapper.map(vo, SysSiteDto.class);
		Pagination p = SysSiteService.list(search, vo.getPageNo(), 10);
		if (p != null && p.getList() != null) {
			List<SysSiteVo> siteVoList = BeanMapper.mapList(p.getList(), SysSiteVo.class);
			p.setList(siteVoList);
		}
		model.addAttribute("page", p);
		model.addAttribute("form", vo);
		return "site/list";
	}

	@RequestMapping("v_add.do")
	public String add(HttpServletRequest request) {
		return "site/add";
	}

	@RequestMapping("v_edit.do")
	public String edit(HttpServletRequest request, HttpServletResponse response, SysSiteVo vo, ModelMap model) {
		SysSiteDto siteDto = SysSiteService.findSiteById(vo.getSiteId());
		SysSiteVo siteVo = BeanMapper.map(siteDto, SysSiteVo.class);
		model.addAttribute("entity", siteVo);
		return "site/edit";
	}

	@RequestMapping("o_save.do")
	@ResponseBody
	public Boolean save(HttpServletRequest request, @Valid @ModelAttribute("form") SysSiteVo form,
			BindingResult errors) {
		if (errors.hasErrors()) {
			return false;
		}
		SysSiteDto siteDto = BeanMapper.map(form, SysSiteDto.class);
		SysSiteService.saveSite(siteDto);

		return true;
	}

	@RequestMapping("o_update.do")
	@ResponseBody
	public Boolean update(@Valid @ModelAttribute("form") SysSiteVo form, BindingResult errors) {
		if (errors.hasErrors()) {
			return false;
		}
		SysSiteDto siteDto = BeanMapper.map(form, SysSiteDto.class);
		if (siteDto != null) {
			SysSiteService.updateSite(siteDto);
			return true;
		} else {
			return false;
		}
	}

	@RequestMapping("o_delete.do")
	public String delete(HttpServletRequest request, HttpServletResponse response, SysSiteVo vo) {
		Collection<Integer> siteIds = null;
		if (vo != null) {
			if (StringUtils.isNotBlank(vo.getAllIDCheck())) {
				String[] ids = StringUtils.split(vo.getAllIDCheck(), ",");
				if (ArrayUtils.isNotEmpty(ids)) {
					siteIds = new ArrayList<Integer>(ids.length);
				}
				for (String str : ids) {
					if (StringUtils.isNumeric(str)) {
						siteIds.add(Integer.parseInt(str));
					}
				}
			}
			if (vo.getSiteId() != null) {
				siteIds = new ArrayList<Integer>(1);
				siteIds.add(vo.getSiteId());
			}
		}
		if (siteIds != null) {
			SysSiteService.updateSiteStatusByIds(Constants.SITE_STATUS_DELETED, siteIds);
		}
		return "redirect:v_list.do";
	}
}
