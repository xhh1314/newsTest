package com.hww.cms.common.dao.impl;
import org.springframework.stereotype.Repository;

import com.hww.cms.common.dao.CmsContentPushDao;
import com.hww.cms.common.entity.CmsContentPush;

@Repository("cmsContentPushDao")
public class CmsContentPushDaoImpl extends LocalDataBaseDaoImpl<Long, CmsContentPush> implements CmsContentPushDao {

	
}
