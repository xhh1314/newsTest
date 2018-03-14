package com.hww.app.common.manager.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hww.app.common.dao.AppCategoryRCmsCategoryDao;
import com.hww.app.common.entity.AppCategoryRCmsCategory;
import com.hww.app.common.manager.AppCategoryRCmsCategoryMng;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;


@Service("appCategoryRCmsCategoryMng")
@Transactional
public class AppCategoryRCmsCategoryMngImpl extends BaseEntityMngImpl<Long, AppCategoryRCmsCategory, AppCategoryRCmsCategoryDao> implements AppCategoryRCmsCategoryMng {

	AppCategoryRCmsCategoryDao appCategoryRCmsCategoryDao;

	public AppCategoryRCmsCategoryDao getAppCategoryRCmsCategoryDao() {
		return appCategoryRCmsCategoryDao;
	}

	@Autowired
	public void setAppCategoryDao(AppCategoryRCmsCategoryDao appCategoryRCmsCategoryDao) {
		super.setEntityDao(appCategoryRCmsCategoryDao);
		this.appCategoryRCmsCategoryDao = appCategoryRCmsCategoryDao;
	}

	@Override
	public List<AppCategoryRCmsCategory> findByCategoryId(Long columnId) {
		// TODO Auto-generated method stub
		return appCategoryRCmsCategoryDao.findByCategoryId(columnId);
	}
}
