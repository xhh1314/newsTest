package com.hww.sns.common.dao.impl;

import org.springframework.stereotype.Service;

import com.hww.sns.common.dao.SnsAuditDao;
import com.hww.sns.common.entity.SnsAudit;
@Service("snsAuditDao")
public class SnsAuditDaoImpl
	extends
		LocalDataBaseDaoImpl<Long, SnsAudit>
	implements
		SnsAuditDao
{

	
}
