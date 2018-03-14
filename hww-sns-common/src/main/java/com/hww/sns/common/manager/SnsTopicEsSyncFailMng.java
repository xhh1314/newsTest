package com.hww.sns.common.manager;

import java.util.List;

import com.hww.base.common.manager.IBaseEntityMng;


import com.hww.sns.common.dao.SnsTopicEsSyncFailDao;
import com.hww.sns.common.entity.SnsTopicEsSyncFail;

public interface SnsTopicEsSyncFailMng extends IBaseEntityMng<Long, SnsTopicEsSyncFail, SnsTopicEsSyncFailDao> {
	
	List<SnsTopicEsSyncFail> loadAllFailRecord();
	
	void updateIsDealSuccess(Long id);
	
	
}
