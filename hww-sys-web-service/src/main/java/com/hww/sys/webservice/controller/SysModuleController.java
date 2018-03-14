package com.hww.sys.webservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hww.base.util.BeanMapper;
import com.hww.sys.common.dto.SysModuleDto;
import com.hww.sys.common.entity.SysModule;
import com.hww.sys.webservice.service.SysModuleService;

@Controller
@RequestMapping("/module")
public class SysModuleController {
	@Autowired
	private SysModuleService sysModuleService;
	
	@RequestMapping("list.do")
	@ResponseBody
	public List<SysModuleDto> list() {
		List<SysModule> sysModuleList=sysModuleService.querySysModelList();
		List<SysModuleDto> vos = BeanMapper.mapList(sysModuleList, SysModuleDto.class);
		return vos;
	}
}
