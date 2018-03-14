package com.hww.app.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hww.app.admin.service.AppVersionService;
import com.hww.app.common.entity.AppVersion;
import com.hww.app.common.vo.AppVersionVo;
import com.hww.base.common.page.Pagination;
import com.hww.base.util.BeanMapper;

@Controller
@RequestMapping("version")
public class AppVersionController {
		
	@Autowired private AppVersionService appVersionService;
	
	@RequestMapping("list.do")
	public String list( AppVersionVo vo,ModelMap map) {
		Pagination p=appVersionService.appVersionlist(vo);
		
		map.addAttribute("page", p);
		
		return "appVersion/list";
		
	}
	
	@RequestMapping("save.do")
	@ResponseBody
	public String save( AppVersionVo vo,ModelMap map) {
		AppVersion appVersion=BeanMapper.map(vo, AppVersion.class);
		appVersionService.addAppVersion(appVersion);
		return "success";
	}
	
	@RequestMapping("del.do")
	@ResponseBody
	public String del( Long id,ModelMap map) {
		appVersionService.delAppVersion(id);
		return "success";
	}
	
	
	
	
	
}
