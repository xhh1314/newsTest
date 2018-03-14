package com.hww.cms.common.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hww.base.common.util.Finder;
import com.hww.cms.common.dao.CmsSpecialRCategoryDao;
import com.hww.cms.common.entity.CmsSpecial;
import com.hww.cms.common.entity.CmsSpecialRCategory;

@Repository("cmsSpecialRCategoryDao")
public class CmsSpecialRCategoryDaoImpl extends LocalDataBaseDaoImpl<Long, CmsSpecialRCategory>
implements CmsSpecialRCategoryDao {

	@Override
	public List<CmsSpecialRCategory> selectBySpecialId(Long specialId) {
		Finder finder = Finder.create("from CmsSpecialRCategory where  specialId=:specialId ");
		finder.setParam("specialId", specialId);
		List<CmsSpecialRCategory> cmsSpecialList = (List<CmsSpecialRCategory>) find(finder);
		return cmsSpecialList;
	}

}
