package com.hww.cms.common.manager;

import java.util.List;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.cms.common.dao.CmsNewEsSyncFailDao;
import com.hww.cms.common.entity.CmsNewEsSyncFail;

public interface CmsNewEsSyncFailMng extends IBaseEntityMng<Long, CmsNewEsSyncFail, CmsNewEsSyncFailDao> {
	
	List<CmsNewEsSyncFail> loadAllFailRecord();
	
	void updateIsDealSuccess(Long id);
}
