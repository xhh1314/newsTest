package com.hww.sys.webservice.service;

import java.util.List;

import com.hww.sys.common.dto.SysModuleDto;
import com.hww.sys.common.entity.SysModule;

public interface SysModuleService {
	List<SysModule> querySysModelList();
	void saveModule(SysModuleDto sysModule);
	void delModule(SysModuleDto sysModule);
	SysModuleDto queryModelById(Long moduleId);
	public Integer getWordCount(SysModuleDto dto);
}
