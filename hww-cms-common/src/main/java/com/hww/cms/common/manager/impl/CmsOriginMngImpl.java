package com.hww.cms.common.manager.impl;

import com.hww.base.common.page.Pagination;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.TimeUtils;
import com.hww.cms.common.dto.CmsOriginDto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.cms.common.dao.CmsOriginDao;
import com.hww.cms.common.entity.CmsOrigin;
import com.hww.cms.common.manager.CmsOriginMng;

@Service("cmsOriginMng")
public class CmsOriginMngImpl extends BaseEntityMngImpl<Long, CmsOrigin, CmsOriginDao>
		implements CmsOriginMng {

	@Autowired
	CmsOriginDao cmsOriginDao;

	public CmsOriginDao getCmsOriginDao() {
		return cmsOriginDao;
	}

	@Autowired
	public void setCmsOriginDao(CmsOriginDao cmsOriginDao) {
		super.setEntityDao(cmsOriginDao);
		this.cmsOriginDao = cmsOriginDao;
	}

	@Override
	public Pagination list(CmsOriginDto dto) {
		return cmsOriginDao.list(dto);
	}

	@Override
	public void saveOrigin(CmsOriginDto dto) {
		CmsOrigin cmsOrigin = BeanMapper.map(dto, CmsOrigin.class);
		cmsOrigin.setCreateTime(TimeUtils.getDateToTimestamp());
		cmsOrigin.setLastModifyTime(TimeUtils.getDateToTimestamp());
		cmsOriginDao.save(cmsOrigin);
	}

	@Override
	public void updateOrigin(CmsOriginDto dto) {
		CmsOrigin cmsOrigin = find(dto.getOriginId());
		cmsOrigin.setIcon(dto.getIcon());
		cmsOrigin.setLink(dto.getLink());
		cmsOrigin.setWord(dto.getWord());
		cmsOrigin.setOriginName(dto.getOriginName());
		cmsOrigin.setOriginUrl(dto.getOriginUrl());
		cmsOrigin.setStatus(dto.getStatus());
		cmsOrigin.setLastModifyTime(dto.getLastModifyTime());
		cmsOriginDao.update(cmsOrigin);
	}

	@Override
	public List<CmsOrigin> listOrigin() {
		
		return cmsOriginDao.listOrigin();
	}
}
