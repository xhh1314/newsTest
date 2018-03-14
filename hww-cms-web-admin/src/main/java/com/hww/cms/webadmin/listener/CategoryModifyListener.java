package com.hww.cms.webadmin.listener;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hww.base.common.listener.ModifyListenerAdapter;
import com.hww.cms.common.entity.CmsCategory;
import com.hww.framework.web.util.PropertyUtils;

public class CategoryModifyListener
	extends
		ModifyListenerAdapter<CmsCategory> {

	@Autowired
	PropertyUtils propertyUtils;

	@Override
	public void afterChange(CmsCategory entity, Map<String, Object> map) {
		// TODO Auto-generated method stub
		super.afterChange(entity, map);
	}

}
