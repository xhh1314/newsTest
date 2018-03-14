package com.hww.sys.common.dao;

import java.util.List;

import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.sys.common.dto.SysModuleDto;
import com.hww.sys.common.entity.SysModule;

public interface SysModuleDao
	extends
		IBaseEntityDao<Long, SysModule> {
	List<SysModule> querySysModelList();
	SysModuleDto queryModelById(Long moduleId);
	void saveModule(SysModuleDto sysModule);
	void delModule(SysModuleDto sysModule);
	public Integer getWordCount(SysModuleDto dto);

}
