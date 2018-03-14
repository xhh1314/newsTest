package com.hww.sys.common.manager;

import java.util.List;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.sys.common.dao.SysModuleDao;
import com.hww.sys.common.dto.SysModuleDto;
import com.hww.sys.common.entity.SysModule;

public interface SysModuleMng
	extends
		IBaseEntityMng<Long, SysModule, SysModuleDao> {
	List<SysModule> querySysModelList();
	SysModuleDto queryModelById(Long moduleId);
	void saveModule(SysModuleDto sysModule);
	void delModule(SysModuleDto sysModule);
	public Integer getWordCount(SysModuleDto dto);
	

}
