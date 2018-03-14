package com.hww.app.admin.config;

import org.springframework.context.ConfigurableApplicationContext;

public class AppCxtUtil {

	public static ConfigurableApplicationContext ctx;

	public static ConfigurableApplicationContext getCtx() {
		return ctx;
	}

	public static void setCtx(ConfigurableApplicationContext ctx) {
		AppCxtUtil.ctx = ctx;
	}
	
	
}
