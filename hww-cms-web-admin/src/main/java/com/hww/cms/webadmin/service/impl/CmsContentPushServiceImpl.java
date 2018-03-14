package com.hww.cms.webadmin.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hww.cms.common.manager.CmsContentPushMng;
import com.hww.cms.webadmin.service.CmsContentPushService;


@Service("cmsContentPushService")
@Transactional
public class CmsContentPushServiceImpl implements CmsContentPushService{


	@Autowired
	private CmsContentPushMng cmsContentPushMng;
	
	
	
}
