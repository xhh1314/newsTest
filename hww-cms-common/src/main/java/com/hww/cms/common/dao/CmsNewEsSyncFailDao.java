package com.hww.cms.common.dao;

import java.util.List;

import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.cms.common.entity.CmsNewEsSyncFail;

public interface CmsNewEsSyncFailDao extends IBaseEntityDao<Long, CmsNewEsSyncFail>{
	List<CmsNewEsSyncFail> selectAllFialRecords();
	
	void updateIsDealSuccess(Long contentId);
	
	
	

}
