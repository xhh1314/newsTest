package com.hww.cms.common.manager;

import java.util.List;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.cms.common.dao.CmsSpecialDao;
import com.hww.cms.common.dao.CmsSpecialRCategoryDao;
import com.hww.cms.common.entity.CmsSpecial;
import com.hww.cms.common.entity.CmsSpecialRCategory;

public interface CmsSpecialRCategoryMng extends IBaseEntityMng<Long, CmsSpecialRCategory, CmsSpecialRCategoryDao> {
	
	List<CmsSpecialRCategory> loadBySpecialId(Long specialId );

	List<Long> loadCmsCateIdsBySpecialId(Long specialId);
	
}
