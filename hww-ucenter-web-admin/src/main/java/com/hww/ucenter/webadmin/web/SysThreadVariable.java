package com.hww.ucenter.webadmin.web;

import com.hww.sys.common.dto.SysSiteDto;
import com.hww.sys.common.dto.SysUserDto;

/**
 * CMS线程变量
 */
public class SysThreadVariable {
	/**
	 * 当前用户线程变量
	 */
	private static ThreadLocal<SysUserDto> SysUserVariable = new ThreadLocal<SysUserDto>();
	/**
	 * 当前站点线程变量
	 */
	private static ThreadLocal<SysSiteDto> SysSiteVariable = new ThreadLocal<SysSiteDto>();

	/**
	 * 获得当前用户
	 *
	 * @return
	 */
	public static SysUserDto getUser() {
		return SysUserVariable.get();
	}

	/**
	 * 设置当前用户
	 *
	 * @param user
	 */
	public static void setUser(SysUserDto user) {
		SysUserVariable.set(user);
	}

	/**
	 * 移除当前用户
	 */
	public static void removeUser() {
		SysUserVariable.remove();
	}

	/**
	 * 获得当前站点
	 *
	 * @return
	 */
	public static SysSiteDto getSite() {
		return SysSiteVariable.get();
	}

	/**
	 * 设置当前站点
	 *
	 * @param site
	 */
	public static void setSite(SysSiteDto site) {
		SysSiteVariable.set(site);
	}

	/**
	 * 移除当前站点
	 */
	public static void removeSite() {
		SysSiteVariable.remove();
	}
}
