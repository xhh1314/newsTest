package com.hww.sns.common.dao.impl;

import org.springframework.stereotype.Service;

import com.hww.framework.common.dao.impl.JpaDaoImpl;
import com.hww.sns.common.dao.SnsForumDao;
import com.hww.sns.common.entity.SnsForum;
@Service("snsForumDao")
public class SnsForumDaoImpl
	extends
		LocalDataBaseDaoImpl<Long, SnsForum>
	implements
		SnsForumDao
{

}
