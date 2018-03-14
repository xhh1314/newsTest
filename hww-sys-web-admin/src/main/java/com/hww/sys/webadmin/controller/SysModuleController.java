package com.hww.sys.webadmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.hww.base.util.BeanMapper;
import com.hww.sys.common.dto.SysModuleDto;
import com.hww.sys.common.entity.SysModule;
import com.hww.sys.common.vo.SysModuleVo;
import com.hww.sys.webadmin.service.SysModuleService;

@Controller
@RequestMapping("/module")
public class SysModuleController {
	@Autowired
	private SysModuleService sysModuleService;
	
	@RequestMapping("list.do")
	public String list(ModelMap map) {
		List<SysModule> sysModuleList=   sysModuleService.querySysModelList();
		List<SysModuleVo> vos = BeanMapper.mapList(sysModuleList, SysModuleVo.class);
		map.addAttribute("page", vos);
		return "menu/medule";
		
	}
	@RequestMapping("add.do")
	@ResponseBody
	public String saveOrUpdate(SysModuleVo vo) {
		String result="success";
		if(StringUtils.isEmpty(vo.getModuleName())) {
			result = "模块名不能为空!";
		}else {
			SysModuleDto dto=BeanMapper.map(vo, SysModuleDto.class);
			dto.setModuleTab(vo.getModuleSymbol());
			Integer wordCount = sysModuleService.getWordCount(dto);
			if(0 == wordCount.intValue()) {//查询该词汇是否已经存在，不存在插入新词汇
				sysModuleService.saveModule(dto);
				result = "保存成功";
			}else {
				sysModuleService.updateModule(dto);
				result = "修改成功";
			}	
		}
		return result;
	}
	
	
	@RequestMapping("find.do")
	@ResponseBody
	public SysModuleVo find(Long moduleId,ModelMap map) {
		SysModuleDto sysModuleDto=sysModuleService.queryModelById(moduleId);
		SysModuleVo vo=BeanMapper.map(sysModuleDto, SysModuleVo.class);	
		vo.setModuleSymbol(sysModuleDto.getModuleTab());
		return vo;
	}
	
	@RequestMapping("delete.do")
	@ResponseBody
	public String del(Long moudleId,ModelMap map) {
		String result="success";
		SysModuleDto sysModuleDto=sysModuleService.queryModelById(moudleId);
		SysModuleVo vo=BeanMapper.map(sysModuleDto, SysModuleVo.class);	
			SysModuleDto dto=BeanMapper.map(vo, SysModuleDto.class);
			sysModuleService.delModule(dto);
		   return result;
	}
	
	
	
	
	
	
	
	
	

}
