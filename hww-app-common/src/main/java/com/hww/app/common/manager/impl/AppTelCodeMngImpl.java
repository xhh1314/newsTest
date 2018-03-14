package com.hww.app.common.manager.impl;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hww.app.common.dao.AppCategoryDao;
import com.hww.app.common.dao.AppTelCodeDao;
import com.hww.app.common.entity.AppNationalTelCode;
import com.hww.app.common.manager.AppTelCodeMng;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
@Service("appTelCodeMng")
@Transactional
public class AppTelCodeMngImpl  extends  BaseEntityMngImpl<Long,AppNationalTelCode,AppTelCodeDao>  implements AppTelCodeMng{
	 @Autowired
	    private AppTelCodeDao appTelCodeDao;
	 @Autowired
		public void setAppCategoryDao(AppTelCodeDao appTelCodeDao) {
			super.setEntityDao(appTelCodeDao);
			this.appTelCodeDao = appTelCodeDao;
		}
		
	@Override
	public void addTelCode(AppNationalTelCode telCode) {
		appTelCodeDao.save(telCode);
	}

	@Override
	public void delTelCode(AppNationalTelCode telCode) {
		// TODO Auto-generated method stub
		appTelCodeDao.delete(telCode);
		
	}

	@Override
	public AppNationalTelCode queryOne(Long telId) {
		AppNationalTelCode telCode=	appTelCodeDao.find(telId);
		return telCode;
	}

	@Override
	public void updateTelCode(AppNationalTelCode telCode) {
		// TODO Auto-generated method stub
		appTelCodeDao.update(telCode);
	}

	@Override
	public Pagination telCodeList(Integer pageNo,Integer pageSize) {
		 Finder f = Finder.create("from AppNationalTelCode");
		
		 Pagination p=appTelCodeDao.find(f, pageNo, pageSize);
		
		
		return p;
	}

}
