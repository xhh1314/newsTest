package com.hww.app.admin.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hww.app.admin.service.AppTelCodeService;
import com.hww.app.common.entity.AppNationalTelCode;
import com.hww.app.common.vo.AppTelCodeVo;
import com.hww.base.common.page.Pagination;
import com.hww.base.util.BeanMapper;



@Controller
@RequestMapping("telCode")
public class AppTelCodeController {

	@Resource
	private AppTelCodeService appTelCodeService;
	
	
	
	
	@RequestMapping("list.do")
	public String list(ModelMap map,AppTelCodeVo vo) {
		AppNationalTelCode telCode=	BeanMapper.map(vo, AppNationalTelCode.class);
		Pagination p= appTelCodeService.telCodeList(vo.getPageNo(), vo.getPageSize());
		map.addAttribute("page", p);
		return "telCode/list";
		
	}
	
	
	@RequestMapping("del.do")
	@ResponseBody
	public String delTelCode(AppTelCodeVo vo) {
	     String result="success";
		appTelCodeService.delTelCode(vo);
		return result;
		
	}
	
	
	
	@RequestMapping("save.do")
	@ResponseBody
	public String saveTelCode(AppTelCodeVo vo) {
		String result="success";
		AppNationalTelCode telCode=	BeanMapper.map(vo, AppNationalTelCode.class);
		if(vo.getId()!=null) {
			appTelCodeService.updateTelCode(telCode);
			result="更新成功";
		}else {
			appTelCodeService.addTelCode(telCode);
			result="添加成功";
		}
		return result;
		
	}
	
	@RequestMapping("find.do")
	@ResponseBody
	public AppTelCodeVo find(AppTelCodeVo vo) {
		AppNationalTelCode telCode=appTelCodeService.queryOne(vo.getId());
		AppTelCodeVo apptelCode=BeanMapper.map(telCode, AppTelCodeVo.class);
		return apptelCode;
	}
	
}
