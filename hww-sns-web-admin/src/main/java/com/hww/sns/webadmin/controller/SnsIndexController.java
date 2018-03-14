package com.hww.sns.webadmin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hww.sns.webadmin.service.SysAuthService;
import com.hww.sys.api.SysFeignClient;
import com.hww.sys.common.dto.SysModuleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hww.framework.web.mvc.controller.AbsBaseController;
import com.hww.framework.web.session.SessionProvider;

import java.util.List;

@Controller
public class SnsIndexController
	extends
		AbsBaseController {

	@Autowired
	private SessionProvider sessionProvider;
	@Autowired
	private SysFeignClient sysFeignClient;
	@Autowired
	private SysAuthService sysAuthService;

	@RequestMapping("index.do")
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		List<SysModuleDto> moduleDtos=sysAuthService.listModule();
		model.addAttribute("modules",moduleDtos);
		return "index";

	}
	
	@RequestMapping("console.do")
	public String console(HttpServletRequest request, HttpServletResponse response, ModelMap model) {


		return "console";

	}

}
