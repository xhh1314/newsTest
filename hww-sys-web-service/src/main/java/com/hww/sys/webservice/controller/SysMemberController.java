package com.hww.sys.webservice.controller;
/*package com.hww.sys.webadmin.controller;

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
import com.hww.sys.common.dto.SysMemberDto;
import com.hww.sys.common.util.SysUtils;
import com.hww.sys.common.vo.SysMemberVo;
import com.hww.sys.webadmin.Constants;
import com.hww.sys.webadmin.service.SysMemberService;

@Controller
@RequestMapping("/member")
public class SysMemberController extends AbsBaseController {
	@Autowired
	private SysMemberService sysMemberService;

	@RequestMapping(value = "index.do")
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "member/index";
	}

	*//**
	 * 系统管理中的会员管理应该在用户管理中，不应该在此系统出现
	 * @param request
	 * @param vo
	 * @param model
	 * @return
	 *//*
	@Deprecated
	@RequestMapping("v_list.do")
	public String list(HttpServletRequest request, SysMemberVo vo, ModelMap model) {
		Integer siteId = SysUtils.getSiteId(request);
		SysMemberDto searchDto = BeanMapper.map(vo, SysMemberDto.class);
		searchDto.setSiteId(siteId);
		Pagination p = sysMemberService.list(searchDto, vo.getPageNo(), 10);
		if (p != null && p.getList() != null) {
			List<SysMemberVo> memberVoList = BeanMapper.mapList(p.getList(), SysMemberVo.class);
			p.setList(memberVoList);
		}

		model.addAttribute("page", p);
		model.addAttribute("form", vo);
		return "member/list";
	}

	@RequestMapping("v_add.do")
	public String add(HttpServletRequest request) {
		return "member/add";
	}

	@RequestMapping("v_edit.do")
	public String edit(HttpServletRequest request, HttpServletResponse response, SysMemberVo vo, ModelMap model) {
		SysMemberDto memberDto = sysMemberService.findMemberById(vo.getMemberId());
		SysMemberVo memverVo = BeanMapper.map(memberDto, SysMemberVo.class);
		model.addAttribute("entity", memverVo);
		return "member/edit";
	}

	@RequestMapping("o_save.do")
	@ResponseBody
	public Boolean save(HttpServletRequest request, @Valid @ModelAttribute("form") SysMemberVo form,
			BindingResult errors) {
		if (errors.hasErrors()) {
			return false;
		}
		Integer siteId = SysUtils.getSiteId(request);
		SysMemberDto memberDto = BeanMapper.map(form, SysMemberDto.class);
		memberDto.setSiteId(siteId);
		sysMemberService.saveMember(memberDto);
		return true;
	}

	@RequestMapping("o_update.do")
	@ResponseBody
	public Boolean update(@Valid @ModelAttribute("form") SysMemberVo form, BindingResult errors) {
		if (errors.hasErrors()) {
			return false;
		}
		SysMemberDto memberDto = BeanMapper.map(form, SysMemberDto.class);
		sysMemberService.updateMember(memberDto);
		// model.addAttribute("entity", entity);
		// return "member/edit";
		return true;
	}

	@RequestMapping("o_delete.do")
	public String delete(HttpServletRequest request, HttpServletResponse response, SysMemberVo vo) {
		Collection<Long> memberIds = null;
		if (vo != null) {
			if (StringUtils.isNotBlank(vo.getAllIDCheck())) {
				String[] ids = StringUtils.split(vo.getAllIDCheck(), ",");
				if (ArrayUtils.isNotEmpty(ids)) {
					memberIds = new ArrayList<Long>(ids.length);
				}
				for (String str : ids) {
					if (StringUtils.isNumeric(str)) {
						memberIds.add(Long.parseLong(str));
					}
				}
			}
			if (vo.getSiteId() != null) {
				memberIds = new ArrayList<Long>(1);
				memberIds.add(vo.getMemberId());
			}
		}
		if (memberIds != null) {
			sysMemberService.updateMemberStatusByIds(Constants.MEMBER_STATUS_DELETED, memberIds);
		}
		return "redirect:v_list.do";
	}
}
*/