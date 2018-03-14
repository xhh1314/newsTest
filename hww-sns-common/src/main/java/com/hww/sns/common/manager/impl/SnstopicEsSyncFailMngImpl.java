package com.hww.sns.common.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;

import com.hww.sns.common.dao.SnsTopicEsSyncFailDao;
import com.hww.sns.common.entity.SnsTopicEsSyncFail;
import com.hww.sns.common.manager.SnsTopicEsSyncFailMng;

@Service("snstopicEsSyncFailMng")
public class SnstopicEsSyncFailMngImpl extends
     BaseEntityMngImpl<Long, SnsTopicEsSyncFail, SnsTopicEsSyncFailDao> implements SnsTopicEsSyncFailMng{

	SnsTopicEsSyncFailDao snstopicEsSyncFailDao;
	
	@Autowired
    public void setSnsTopicEsSyncFailDao(SnsTopicEsSyncFailDao snstopicEsSyncFailDao) {
        super.setEntityDao(snstopicEsSyncFailDao);
        this.snstopicEsSyncFailDao = snstopicEsSyncFailDao;
    }
	
	@Override
	public List<SnsTopicEsSyncFail> loadAllFailRecord(){
		List<SnsTopicEsSyncFail>  lists=snstopicEsSyncFailDao.selectAllFialRecords();
		return (lists==null||lists.isEmpty())?Lists.newArrayList():lists;
	}


	@Override
	public void updateIsDealSuccess(Long id) {
		
		snstopicEsSyncFailDao.updateIsDealSuccess(id);
	}
	

}
