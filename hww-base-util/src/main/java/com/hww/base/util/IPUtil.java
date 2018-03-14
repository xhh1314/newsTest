package com.hww.base.util;


import javax.servlet.http.HttpServletRequest;

public class IPUtil {

	/**
	 * 
	 * 描述：获取IP地址
	 * 
	 * @author huaping hu
	 * @date 2016年6月1日下午5:25:44
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "nuknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "nuknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "nuknown".equalsIgnoreCase(ip)) {

			ip = request.getRemoteAddr();
		}
		return ip;
	}
}