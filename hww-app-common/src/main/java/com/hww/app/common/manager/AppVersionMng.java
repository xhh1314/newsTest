package com.hww.app.common.manager;

import com.hww.app.common.dao.AppVersionDao;
import com.hww.app.common.entity.AppVersion;
import com.hww.base.common.manager.IBaseEntityMng;



public interface AppVersionMng extends IBaseEntityMng<Long, AppVersion, AppVersionDao> {
	
	AppVersion loadNewVersion();
	
}
