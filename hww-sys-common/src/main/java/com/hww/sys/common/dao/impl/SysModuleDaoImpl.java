package com.hww.sys.common.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.sys.common.dao.SysModuleDao;
import com.hww.sys.common.dto.SysModuleDto;
import com.hww.sys.common.entity.SysModule;

@Repository("sysModuleDao")
public class SysModuleDaoImpl
	extends
		LocalEntityDaoImpl<Long, SysModule>
	implements
		SysModuleDao {

	public List<SysModule> querySysModelList() {
		Finder finder=Finder.create(" from SysModule");
		return (List<SysModule>) find(finder);
	}
	public SysModuleDto queryModelById(Long moduleId) {
		Finder finder=Finder.create(" from SysModule where 1=1 ");
		finder.append(" and moduleId=:moduleId").setParam("moduleId", moduleId);
		List<SysModule> sysModuleList=(List<SysModule>) find(finder);
		SysModuleDto sysModuleDto=BeanMapper.map(sysModuleList.get(0), SysModuleDto.class);
		sysModuleDto.setModuleTab(sysModuleList.get(0).getModuleSymbol());
		return sysModuleDto;
	}
	public void saveModule(SysModuleDto sysModuleDto) {
		SysModule sysModule=BeanMapper.map(sysModuleDto, SysModule.class);
		sysModule.setModuleSymbol(sysModuleDto.getModuleTab());
		save(sysModule);	
	}
	
	public void delModule(SysModuleDto sysModuleDto) {
		SysModule sysModule=BeanMapper.map(sysModuleDto, SysModule.class);
		delete(sysModule);
	}
	public Integer getWordCount(SysModuleDto dto) {
		  Finder f = Finder.create("SELECT count(*) ");
	      f.append(" FROM sys_module");
	      f.append(" where module_name = :name");
	      f.setParam("name", dto.getModuleName());
	      Integer wordCount = countQueryResultWithNativeInteger(f);
	      return wordCount;
	}

}
