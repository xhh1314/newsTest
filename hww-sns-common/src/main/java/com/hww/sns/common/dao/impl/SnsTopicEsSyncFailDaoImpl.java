package com.hww.sns.common.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hww.base.common.util.Finder;
import com.hww.sns.common.dao.SnsTopicEsSyncFailDao;
import com.hww.sns.common.entity.SnsTopicEsSyncFail;


@Repository("snsTopicEsSyncFailDao")
public class SnsTopicEsSyncFailDaoImpl extends LocalDataBaseDaoImpl<Long, SnsTopicEsSyncFail> implements  SnsTopicEsSyncFailDao{

	
	public List<SnsTopicEsSyncFail> selectAllFialRecords() {
		Finder finder =Finder.create(" from SnsTopicEsSyncFail ");
		finder.append(" where isDealSuccess=:isDealSuccess").setParam("isDealSuccess", 0);
		return (List<SnsTopicEsSyncFail>) find(finder);
	}

	
	public void updateIsDealSuccess(Long id) {
		Finder finder=Finder.create(" update SnsTopicEsSyncFail ");
		finder.append(" set isDealSuccess=:isDealSuccess").setParam("isDealSuccess", 1);
		finder.append(" where id=:id").setParam("id", id);
		update(finder);
	}

}
