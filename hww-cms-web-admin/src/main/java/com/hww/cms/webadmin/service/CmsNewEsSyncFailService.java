package com.hww.cms.webadmin.service;

import com.hww.cms.common.entity.CmsNewEsSyncFail;
import com.hww.sns.common.entity.SnsTopicEsSyncFail;

public interface CmsNewEsSyncFailService {

	void save(CmsNewEsSyncFail cmsNewEsSyncFail);
	
	
	void doAync();
	
	

	
}
