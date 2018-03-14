package com.hww.sys.webservice.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.BeanUtils;
import com.hww.base.util.StringUtils;
import com.hww.sys.common.dto.SysSiteDto;
import com.hww.sys.common.entity.SysSite;
import com.hww.sys.common.manager.SysSiteMng;
import com.hww.sys.webservice.service.SysSiteService;
@Service("sysSiteService")
@Transactional
public class SysSiteServiceImpl implements SysSiteService {

	@Autowired
	SysSiteMng sysSiteMng;

	@Override
	public List<SysSiteDto> list(SysSiteDto searchDto, Integer pageNo,
			Integer pageSize) {
		// TODO Auto-generated method stub
		Finder hql = Finder.create("from ");
		hql.append(SysSite.class.getName());
		hql.append(" where 1=1");
		if (searchDto != null) {
			if (StringUtils.isNotBlank(searchDto.getSiteName())) {
				hql.append(" and siteName like :siteNameP").setParam(
						"siteNameP", "%" + searchDto.getSiteName() + "%");
			}
			if (StringUtils.isNotBlank(searchDto.getDomain())) {
				hql.append(" and domain=:domainP").setParam("domainP",
						searchDto.getDomain());
			}
			if (searchDto.getStatus() != null) {
				hql.append(" and status=:statusP").setParam("statusP",
						searchDto.getStatus());
			} else {
				hql.append(" and status>=0");
			}
		}

		List<SysSite> list= (List<SysSite>) sysSiteMng.find(hql);
		
		List<SysSiteDto> siteDtoList = null;
		if (list!= null ) {
			siteDtoList = BeanMapper.mapList(list,
					SysSiteDto.class);
		}
		return siteDtoList;
	}

	@Override
	public SysSiteDto findSiteById(Integer id) {
		// TODO Auto-generated method stub
		
		SysSite entity = sysSiteMng.find(id);
		SysSiteDto dto = BeanMapper.map(entity, SysSiteDto.class);
		return dto;
	}

	@Override
	public void saveSite(SysSiteDto siteDto) {
		// TODO Auto-generated method stub
		SysSite entity = BeanMapper.map(siteDto, SysSite.class);
		sysSiteMng.saveSite(entity);
	}

	@Override
	public void updateSite(SysSiteDto siteDto) {
		// TODO Auto-generated method stub
		SysSite entity = sysSiteMng.find(siteDto.getId());
		try {
			BeanUtils.copyProperties(entity, siteDto);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sysSiteMng.update(entity);
	}

	@Override
	public void updateSiteStatusByIds(Short status,Collection<Integer> siteIds) {
		// TODO Auto-generated method stub
		sysSiteMng.updateStatusByProperty(status, "siteId", siteIds);
	}

	@Override
	public Object find(int pageNo, int pageSize) {
		return sysSiteMng.find(pageNo, pageSize);
	}
	
	@Override
	public List<SysSiteDto> findAllSite() {
		
		Finder finder = Finder.create("from SysSite");
		
		
		List<SysSite> sites = (List<SysSite>) sysSiteMng.find(finder);
		
		List<SysSiteDto> dto = BeanMapper.mapList(sites, SysSiteDto.class);
		return dto;
	}

	@Override
	public List<SysSiteDto> siteName() {
		// TODO Auto-generated method stub
		return null;
	}

}
