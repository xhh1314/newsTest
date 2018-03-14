package com.hww.sys.webservice.service;

import java.util.Collection;
import java.util.List;

import com.hww.sys.common.dto.SysSiteDto;

public interface SysSiteService {
	
	List<SysSiteDto> list(SysSiteDto searchDto, Integer pageNo, Integer pageSize);
	
	SysSiteDto findSiteById(Integer id);
	
	void saveSite(SysSiteDto siteDto);
	
	void updateSite(SysSiteDto siteDto);
	
	void updateSiteStatusByIds(Short status, Collection<Integer> siteIds);

	Object find(int i, int j);

	List<SysSiteDto> siteName();

	List<SysSiteDto> findAllSite();
	
}
