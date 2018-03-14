package com.hww.app.common.manager.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.util.Finder;
import com.hww.app.common.dao.AppVersionDao;
import com.hww.app.common.entity.AppVersion;
import com.hww.app.common.manager.AppVersionMng;

@Service("appVersionMng")
@Transactional
public class AppVersionMngImpl extends BaseEntityMngImpl<Long, AppVersion, AppVersionDao> implements AppVersionMng {

	AppVersionDao appVersionDao;

	public AppVersionDao getAppVersionDao() {
		return appVersionDao;
	}

	@Autowired
	public void setAppVersionDao(AppVersionDao appVersionDao) {
		super.setEntityDao(appVersionDao);
		this.appVersionDao = appVersionDao;
	}

	@Override
	public AppVersion loadNewVersion() {
		  	Finder f = Finder.create("from AppVersion bean");
			f.append(" where bean.status=1  order by createTime desc ");
			List<?> res=find(f);
			if(res==null||res.isEmpty()){
				return null;
			}
			AppVersion appVersion = (AppVersion) (res.get(0));
	        return appVersion;
	}
	
}
