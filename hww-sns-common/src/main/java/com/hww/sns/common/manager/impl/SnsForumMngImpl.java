package com.hww.sns.common.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.sns.common.dao.SnsForumDao;
import com.hww.sns.common.entity.SnsForum;
import com.hww.sns.common.manager.SnsForumMng;
@Service("snsForumMng")
public class SnsForumMngImpl
	extends
		BaseEntityMngImpl<Long, SnsForum, SnsForumDao>
    implements
    	SnsForumMng
{
	 
	SnsForumDao snsForumDao;

	@Autowired
	public void setSnsForumDao(SnsForumDao snsForumDao) {
		super.setEntityDao(snsForumDao);
		this.snsForumDao = snsForumDao;
	}
	
}
