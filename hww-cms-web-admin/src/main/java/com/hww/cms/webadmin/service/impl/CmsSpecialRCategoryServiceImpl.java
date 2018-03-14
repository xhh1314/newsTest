package com.hww.cms.webadmin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.util.CopyBean;
import com.hww.cms.common.entity.CmsSpecialRCategory;
import com.hww.cms.common.manager.CmsSpecialRCategoryMng;
import com.hww.cms.common.vo.CmsSpecialRCategoryVo;
import com.hww.cms.webadmin.service.CmsSpecialRCategoryService;

@Service("cmsSpecialRCategoryService")
@Transactional
public class CmsSpecialRCategoryServiceImpl implements CmsSpecialRCategoryService {

	
	@Autowired
	CmsSpecialRCategoryMng cmsSpecialRCategoryMngMng;
	
	
	@Override
	public void save(CmsSpecialRCategoryVo vo) {
		if(vo!=null){
			CmsSpecialRCategory entity = new CmsSpecialRCategory();
			CopyBean.copyNotNull(vo, entity);
			cmsSpecialRCategoryMngMng.save(entity);
		}
	}
}
