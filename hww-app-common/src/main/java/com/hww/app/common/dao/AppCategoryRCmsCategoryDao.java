package com.hww.app.common.dao;

import java.util.List;

import com.hww.app.common.entity.AppCategoryRCmsCategory;
import com.hww.base.common.dao.IBaseEntityDao;

public interface AppCategoryRCmsCategoryDao extends IBaseEntityDao<Long, AppCategoryRCmsCategory> {
	
	public List<AppCategoryRCmsCategory> findByCategoryId(Long columnId);

}