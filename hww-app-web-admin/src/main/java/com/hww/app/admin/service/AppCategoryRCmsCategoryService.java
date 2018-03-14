package com.hww.app.admin.service;

import java.util.List;

import com.hww.app.common.entity.AppCategoryRCmsCategory;

public interface AppCategoryRCmsCategoryService {
	public List<AppCategoryRCmsCategory> findByCategoryId(Long columnId);
}
