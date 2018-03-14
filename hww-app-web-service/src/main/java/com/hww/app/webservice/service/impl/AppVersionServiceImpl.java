package com.hww.app.webservice.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hww.app.common.entity.AppVersion;
import com.hww.app.common.manager.AppVersionMng;
import com.hww.app.webservice.service.AppVersionService;

@Service("appVersionService")
@Transactional
public class AppVersionServiceImpl
	implements
		AppVersionService {

	@Autowired
	private AppVersionMng appVersionMng;

	public AppVersion loadNewVersion() {

		AppVersion version = appVersionMng.loadNewVersion();
		if (version == null) {
			return null;
		}

		// version.setApkUrl(env.getProperty("fileservice.domain")+version.getApkUrl());//添加配置file
		// ip地址

		if (version.getStatus() == null || 1 != version.getStatus().intValue()) {
			return null;
		}
		return version;

	}

}
