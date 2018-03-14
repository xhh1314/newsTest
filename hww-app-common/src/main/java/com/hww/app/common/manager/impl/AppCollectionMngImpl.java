package com.hww.app.common.manager.impl;

import com.hww.app.common.dao.AppCollectionDao;

import com.hww.app.common.entity.AppCollection;

import com.hww.app.common.manager.AppCollectionMng;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;

import org.springframework.stereotype.Service;


@Service("appCollectionMngImpl")
public class AppCollectionMngImpl extends BaseEntityMngImpl<Long, AppCollection, AppCollectionDao> 
	implements AppCollectionMng {
	
	
}
