package com.hww.app.webservice.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hww.app.common.entity.AppVersion;
import com.hww.app.webservice.service.AppVersionService;
import com.hww.base.util.BeanUtils;

@RestController
@RequestMapping("/app/appversion")
public class AppVersionController {

	private static final Log log = LogFactory.getLog(AppVersionController.class);

	@Autowired
	private AppVersionService appVersionService;

	@Autowired
	private Environment env;

	@RequestMapping(value = "/versoncheck.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public AppVersion addHistoryFeign() {
		try {

			AppVersion appVersion = appVersionService.loadNewVersion();
			AppVersion target = new AppVersion();
			BeanUtils.copyProperties(target, appVersion);
			;
			target.setApkUrl(env.getProperty("fileservice.domain") + target.getApkUrl());// 添加配置file
																						 // ip地址
			return target;
		} catch (Exception e) {
			return null;
		}

	}

}
