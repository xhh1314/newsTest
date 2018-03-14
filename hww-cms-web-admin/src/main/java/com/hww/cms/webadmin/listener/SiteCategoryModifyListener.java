/*package com.hww.cms.webadmin.listener;

import org.springframework.beans.factory.annotation.Autowired;

import com.hww.base.common.listener.ModifyListenerAdapter;
import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.manager.CmsCategoryMng;
import com.hww.sys.common.entity.SysSite;

public class SiteCategoryModifyListener
	extends
		ModifyListenerAdapter<SysSite> {
	@Autowired
	CmsCategoryMng cmsCategoryMng;

	@Override
	public void afterSave(SysSite entity) {
		// TODO Auto-generated method stub
		super.afterSave(entity);
		CmsCategory rootCategory = new CmsCategory();
		rootCategory.setParent(null);
		rootCategory.setCategoryName("栏目");
		rootCategory.setSiteId(entity.getSiteId());
		rootCategory.setLft(Long.parseLong("1"));
		rootCategory.setRgt(Long.parseLong("2"));
		rootCategory.setDisplay((short) 1);
		rootCategory.setStatus((short) 1);
		cmsCategoryMng.save(rootCategory);

	}

}
*/