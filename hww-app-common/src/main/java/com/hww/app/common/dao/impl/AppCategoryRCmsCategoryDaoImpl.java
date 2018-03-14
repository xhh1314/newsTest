package com.hww.app.common.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hww.app.common.dao.AppCategoryRCmsCategoryDao;
import com.hww.app.common.entity.AppCategoryRCmsCategory;
import com.hww.base.common.util.Finder;

@Repository("appCategoryRCmsCategoryDao")
public class AppCategoryRCmsCategoryDaoImpl extends LocalEntityDaoImpl<Long, AppCategoryRCmsCategory> implements AppCategoryRCmsCategoryDao {

	@Override
	public List<AppCategoryRCmsCategory> findByCategoryId(Long columnId) {
		Finder f=Finder.create("from AppCategoryRCmsCategory");
		f.append(" where 1=1");
		if(columnId!=null) {
			f.append(" and columnId=:ColumnIdP").setParam("ColumnIdP", columnId);
			return (List<AppCategoryRCmsCategory>) find(f);
		}
		return new ArrayList<AppCategoryRCmsCategory>();
	}


}