package com.hww.cms.common.manager.impl;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.cms.common.dao.CmsContentDao;
import com.hww.cms.common.dao.CmsContentPushDao;
import com.hww.cms.common.entity.CmsContentPush;
import com.hww.cms.common.manager.CmsContentPushMng;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("cmsContentPushMng")
public class CmsContentPushMngImpl
	extends
		BaseEntityMngImpl<Long, CmsContentPush, CmsContentPushDao>
	implements CmsContentPushMng {
	
	CmsContentPushDao cmsContentPushDao;
	@Autowired
	public void setCmsContentDao(CmsContentPushDao cmsContentPushDao) {
		super.setEntityDao(cmsContentPushDao);
		this.cmsContentPushDao = cmsContentPushDao;
	}
}
