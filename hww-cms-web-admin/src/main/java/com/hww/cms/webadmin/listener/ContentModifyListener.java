package com.hww.cms.webadmin.listener;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hww.base.common.listener.ModifyListenerAdapter;
import com.hww.cms.common.dao.CmsContentDao;
import com.hww.cms.common.entity.CmsContent;

public class ContentModifyListener
	extends
		ModifyListenerAdapter<CmsContent> {
	@Autowired
	CmsContentDao cmsContentDao;

	@Override
	public void afterSave(CmsContent entity) {
		// TODO Auto-generated method stub
		super.afterSave(entity);

	}

	@Override
	public Map<String, Object> preChange(CmsContent entity) {
		// TODO Auto-generated method stub
		return super.preChange(entity);
	}

	@Override
	public void afterChange(CmsContent entity, Map<String, Object> map) {
		// TODO Auto-generated method stub
		super.afterChange(entity, map);

	}

}
