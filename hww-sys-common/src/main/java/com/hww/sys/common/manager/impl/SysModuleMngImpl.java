package com.hww.sys.common.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.sys.common.dao.SysModuleDao;
import com.hww.sys.common.dao.SysRoleDao;
import com.hww.sys.common.dto.SysModuleDto;
import com.hww.sys.common.entity.SysModule;
import com.hww.sys.common.manager.SysModuleMng;

@Service("sysModuleMng")
@Transactional
public class SysModuleMngImpl
	extends
		BaseEntityMngImpl<Long, SysModule, SysModuleDao>
	implements
		SysModuleMng {

	
	
	
	
	@Autowired
   SysModuleDao sysModuleDao;
	
	  @Autowired
	    public void setSysRoleDao(SysModuleDao sysModuleDao)
	    {
	        super.setEntityDao(sysModuleDao);
	        this.sysModuleDao = sysModuleDao;
	    }
	  
	  
	public List<SysModule> querySysModelList() {
	
		return sysModuleDao.querySysModelList();
	}
	public SysModuleDto queryModelById(Long moduleId) {
	
		return sysModuleDao.queryModelById(moduleId);
	}

	public void saveModule(SysModuleDto sysModuleDto) {
		sysModuleDao.saveModule(sysModuleDto);
		
	}

	public void delModule(SysModuleDto sysModuleDto) {
		sysModuleDao.delModule(sysModuleDto);
		
	}
	@Override
	public Integer getWordCount(SysModuleDto dto) {
		// TODO Auto-generated method stub
		return sysModuleDao.getWordCount(dto);
	}

}
