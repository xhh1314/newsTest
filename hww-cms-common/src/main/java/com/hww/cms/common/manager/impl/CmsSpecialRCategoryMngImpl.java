package com.hww.cms.common.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.cms.common.dao.CmsSpecialDao;
import com.hww.cms.common.dao.CmsSpecialRCategoryDao;
import com.hww.cms.common.entity.CmsSpecial;
import com.hww.cms.common.entity.CmsSpecialRCategory;
import com.hww.cms.common.manager.CmsSpecialMng;
import com.hww.cms.common.manager.CmsSpecialRCategoryMng;

@Service("cmsSpecialRCategoryMng")
@Transactional
public class CmsSpecialRCategoryMngImpl extends BaseEntityMngImpl<Long, CmsSpecialRCategory, CmsSpecialRCategoryDao>
		implements CmsSpecialRCategoryMng {

	CmsSpecialRCategoryDao cmsSpecialRCategoryDao;

	@Autowired
	public void setCmsSpecialRCategoryDao(CmsSpecialRCategoryDao cmsSpecialRCategoryDao) {
		super.setEntityDao(cmsSpecialRCategoryDao);
		this.cmsSpecialRCategoryDao = cmsSpecialRCategoryDao;
	}

	@Override
	public List<CmsSpecialRCategory> loadBySpecialId(Long specialId) {
		if (specialId == null) {
			return new ArrayList<CmsSpecialRCategory>();
		}
		return cmsSpecialRCategoryDao.selectBySpecialId(specialId);
	}
	
	
	@Override
	public List<Long> loadCmsCateIdsBySpecialId(Long specialId) {
		List<CmsSpecialRCategory> list= cmsSpecialRCategoryDao.selectBySpecialId(specialId);
		if(list!=null&&!list.isEmpty()) {
			return list.stream().map(val->val.getCategoryId()).collect(Collectors.toList());
		}
		return Lists.newArrayList();
	}

}
