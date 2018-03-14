package com.hww.cms.common.dao.impl;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.StringUtils;
import com.hww.cms.common.dto.CmsOriginDto;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hww.cms.common.dao.CmsOriginDao;
import com.hww.cms.common.entity.CmsOrigin;

@Repository("cmsOriginDao")
public class CmsOriginDaoImpl extends LocalDataBaseDaoImpl<Long, CmsOrigin> implements CmsOriginDao {

	@Override
	public Pagination list(CmsOriginDto dto) {
		Finder finder = Finder.create("from CmsOrigin where 1=1");
		if (StringUtils.isNotBlank(dto.getOriginName())) {
			finder.append(" and originName=:originName").setParam("originName", dto.getOriginName());
		}
		if (StringUtils.isNotBlank(dto.getOriginUrl())) {
			finder.append(" and originUrl=:originUrl").setParam("originUrl", dto.getOriginUrl());
		}
		if (StringUtils.isNotBlank(dto.getLink())) {
			finder.append(" and link=:link").setParam("link", dto.getLink());
		}
		if (dto.getStatus() != null) {
			finder.append(" and status=:status").setParam("status", dto.getStatus());
		}
		Pagination p = find(finder, dto.getPageNo(), dto.getPageSize());
		return p;
	}

	@Override
	public List<CmsOrigin> listOrigin() {
		Finder finder = Finder.create("from CmsOrigin where 1=1");
		finder.append(" and status=:status").setParam("status",Short.valueOf("1"));
		List<CmsOrigin> list = (List<CmsOrigin>)find(finder);
		return list;
	}
}
