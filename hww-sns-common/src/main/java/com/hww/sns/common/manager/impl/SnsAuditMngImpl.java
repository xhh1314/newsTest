package com.hww.sns.common.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.sns.common.dao.SnsAuditDao;
import com.hww.sns.common.entity.SnsAudit;
import com.hww.sns.common.manager.SnsAuditMng;
@Service("snsAuditMng")
public class SnsAuditMngImpl
	extends
		BaseEntityMngImpl<Long, SnsAudit, SnsAuditDao>
	implements
		SnsAuditMng
{
	 
	SnsAuditDao snsAuditDao;

	@Autowired
	public void setSnsTopicDao(SnsAuditDao snsAuditDao) {
		super.setEntityDao(snsAuditDao);
		this.snsAuditDao = snsAuditDao;
	}
	
}
