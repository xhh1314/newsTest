package com.hww.app.admin.service;

import com.hww.app.common.entity.AppVersion;
import com.hww.app.common.vo.AppVersionVo;
import com.hww.base.common.page.Pagination;

public interface AppVersionService {
	
	 Pagination  appVersionlist(AppVersionVo appVersionVo);
	 
	 AppVersion queryOne(Long id);
	 
	 void delAppVersion(Long id);

	 void addAppVersion(AppVersion appVersion);
	 
	 void updateAppVersion(AppVersionVo appVersionVo);
	 
	
	
	

}
