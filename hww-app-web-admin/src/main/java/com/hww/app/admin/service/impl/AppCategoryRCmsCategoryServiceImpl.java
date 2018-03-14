package com.hww.app.admin.service.impl;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hww.app.admin.service.AppCategoryRCmsCategoryService;
import com.hww.app.common.entity.AppCategoryRCmsCategory;
import com.hww.app.common.manager.AppCategoryRCmsCategoryMng;


@Service("appCategoryRCmsCategoryService")
@Transactional
public class AppCategoryRCmsCategoryServiceImpl implements AppCategoryRCmsCategoryService {

	@Autowired
	private AppCategoryRCmsCategoryMng appCategoryRCmsCategoryMng;

	@Override
	public List<AppCategoryRCmsCategory> findByCategoryId(Long columnId) {
		// TODO Auto-generated method stub
		return appCategoryRCmsCategoryMng.findByCategoryId(columnId);
	}

}
