package com.hww.cms.common.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.cms.common.dao.CmsNewEsSyncFailDao;
import com.hww.cms.common.entity.CmsNewEsSyncFail;
import com.hww.cms.common.manager.CmsNewEsSyncFailMng;


@Service("cmsNewEsSyncFailMngImpl")
public class CmsNewEsSyncFailMngImpl extends
     BaseEntityMngImpl<Long, CmsNewEsSyncFail, CmsNewEsSyncFailDao> implements CmsNewEsSyncFailMng{

	CmsNewEsSyncFailDao cmsNewEsSyncFailDao;
	
	 @Autowired
	    public void setCmsContentTypeDao(CmsNewEsSyncFailDao cmsNewEsSyncFailDao){
	        super.setEntityDao(cmsNewEsSyncFailDao);
	        this.cmsNewEsSyncFailDao = cmsNewEsSyncFailDao;
	    }
	
		@Override
		public List<CmsNewEsSyncFail> loadAllFailRecord(){
			List<CmsNewEsSyncFail>  lists=cmsNewEsSyncFailDao.selectAllFialRecords();
			return (lists==null||lists.isEmpty())?Lists.newArrayList():lists;
		}


		@Override
		public void updateIsDealSuccess(Long id) {
			
			cmsNewEsSyncFailDao.updateIsDealSuccess(id);
		}


}
