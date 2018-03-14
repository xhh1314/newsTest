package com.hww.ucenter.webadmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hww.sys.api.SysFeignClient;
import com.hww.sys.common.dto.SysModuleDto;

@Controller
@RequestMapping("sys/module")
public class ModuleForUCenter {
	@Autowired
	private SysFeignClient sysFeignClient;
	
	
	@RequestMapping("allModule.do")
	@ResponseBody
	public List<SysModuleDto> list(){
		return sysFeignClient.list();
		
	}

}
