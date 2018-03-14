package com.hww.cms.webadmin.util;

import com.hww.base.common.page.Pagination;
import com.hww.base.util.StringUtils;
import com.hww.framework.web.Constants;
import com.hww.framework.web.session.SessionProvider;
import com.hww.framework.web.util.CookieUtils;
import com.hww.sys.api.SysFeignClient;
import com.hww.sys.common.dto.SysSiteDto;
import com.hww.sys.common.util.SysUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取站点ID
 */
@Component
public class SiteIdUtil {

	@Autowired
	static SessionProvider sessionProvider;

	static SysFeignClient sysFeignClient;
	private static final Logger log = Logger.getLogger(SiteIdUtil.class);

	public static SysSiteDto getSiteId(HttpServletRequest request, HttpServletResponse response, Session session) {
		SysSiteDto site = getByParams(request, response);
		if (site == null) {
			site = (SysSiteDto) session.getAttribute(Constants.SESSION_SITE);
		}
		if (site == null) {
			site = (SysSiteDto) sessionProvider.getAttribute(request, Constants.SESSION_SITE);
		}
		if (site == null) {
			site = getByCookie(request);
		}
		if (site == null) {
			site = getByDomain(request);
		}
		if (site == null) {
			site = getByDefault();
		}
		if (site == null) {
			throw new RuntimeException("cannot get site!");
		} else {
			return site;
		}
	}

	public static SysSiteDto getByParams(HttpServletRequest request, HttpServletResponse response) {
		SysSiteDto site = SysUtils.getSite(request);
		if (site != null) {
			return site;
		}
		String p = request.getParameter(Constants.SITE_PARAM);
		if (!StringUtils.isBlank(p)) {
			try {
				Integer siteId = Integer.parseInt(p);
				site = sysFeignClient.findSiteById(siteId);
				if (site != null) {
					// 若使用参数选择站点，则应该把站点保存至cookie中才好。
					CookieUtils.addCookie(request, response, Constants.SITE_COOKIE, site.getSiteId().toString(), null,
							null);
					return site;
				}
			} catch (NumberFormatException e) {
				log.warn("param site id format exception", e);
			}
		}
		return null;
	}

	private Integer getSiteIdByCookie(HttpServletRequest request) {
		Cookie cookie = CookieUtils.getCookie(request, Constants.SITE_COOKIE);
		if (cookie != null) {
			String v = cookie.getValue();
			if (!StringUtils.isBlank(v)) {
				try {
					Integer siteId = Integer.parseInt(v);
					return siteId;
				} catch (NumberFormatException e) {
					log.warn("cookie site id format exception", e);
				}
			}
		}
		return null;
	}

	public static SysSiteDto getByCookie(HttpServletRequest request) {
		Cookie cookie = CookieUtils.getCookie(request, Constants.SITE_COOKIE);
		if (cookie != null) {
			String v = cookie.getValue();
			if (!StringUtils.isBlank(v)) {
				try {
					Integer siteId = Integer.parseInt(v);
					return sysFeignClient.findSiteById(siteId);
				} catch (NumberFormatException e) {
					log.warn("cookie site id format exception", e);
				}
			}
		}
		return null;
	}

	public static SysSiteDto getByDomain(HttpServletRequest request) {
		String domain = request.getServerName();
		if (!StringUtils.isBlank(domain)) {
			SysSiteDto search = new SysSiteDto();
			search.setDomain(domain);
			SysSiteDto sysSiteDto=new SysSiteDto();
			List<SysSiteDto> p = sysFeignClient.findSitelist(sysSiteDto);
			if (p != null && p.size() > 0) {
				return (SysSiteDto) p.get(0);
			}
		}
		return null;
	}

	public static SysSiteDto getByDefault() {
		SysSiteDto sysSiteDto=new SysSiteDto();
		List<SysSiteDto> p = sysFeignClient.findSitelist(sysSiteDto);
		if (p != null && p.size() > 0) {
			return (SysSiteDto) p.get(0);
		} else {
			return null;
		}
	}
}
