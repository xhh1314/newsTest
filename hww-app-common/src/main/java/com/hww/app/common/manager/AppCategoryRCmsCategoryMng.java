package com.hww.app.common.manager;

import java.util.List;

import com.hww.app.common.dao.AppCategoryRCmsCategoryDao;
import com.hww.app.common.entity.AppCategoryRCmsCategory;
import com.hww.base.common.manager.IBaseEntityMng;

public interface AppCategoryRCmsCategoryMng extends IBaseEntityMng<Long, AppCategoryRCmsCategory, AppCategoryRCmsCategoryDao> {
	public List<AppCategoryRCmsCategory> findByCategoryId(Long columnId);
}
