package com.hww.sns.common.dao;

import java.util.List;

import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.sns.common.entity.SnsTopicEsSyncFail;


public interface SnsTopicEsSyncFailDao extends IBaseEntityDao<Long, SnsTopicEsSyncFail>{
	
	List<SnsTopicEsSyncFail> selectAllFialRecords();
	
	void updateIsDealSuccess(Long contentId);
	
	

}
