package com.hww.cms.common.dao;

import java.util.List;

import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.cms.common.entity.CmsSpecialRCategory;


public interface CmsSpecialRCategoryDao extends IBaseEntityDao<Long, CmsSpecialRCategory> {

	List<CmsSpecialRCategory> selectBySpecialId(Long specialId );
}
