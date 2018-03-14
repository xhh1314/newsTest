package com.hww.sys.webservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.sys.common.dto.SysModuleDto;
import com.hww.sys.common.entity.SysModule;
import com.hww.sys.common.manager.SysModuleMng;
import com.hww.sys.webservice.service.SysModuleService;
@Service("sysModuleService")
@Transactional
public class SysModuleServiceImpl implements SysModuleService{
	
	
	@Autowired
	private SysModuleMng sysModuleMng;

	@Override
	public List<SysModule> querySysModelList() {
		
		return sysModuleMng.querySysModelList();
	}

	@Override
	public void saveModule(SysModuleDto sysModuleDto) {
		//保存，更新
		sysModuleMng.saveModule(sysModuleDto);
		
	}

	
	public void delModule(SysModuleDto sysModuleDto) {
		sysModuleMng.delModule(sysModuleDto);
		
	}

	@Override
	public SysModuleDto queryModelById(Long moduleId) {
		
		return sysModuleMng.queryModelById(moduleId);
	}

	@Override
	public Integer getWordCount(SysModuleDto dto) {
		// TODO Auto-generated method stub
		return sysModuleMng.getWordCount(dto);
	}

}
