/*
package com.hww.ucenter.webadmin.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import com.hww.base.common.page.Pagination;
import com.hww.base.util.StringUtils;
import com.hww.framework.web.Constants;
import com.hww.framework.web.session.SessionProvider;
import com.hww.framework.web.util.CookieUtils;
import com.hww.sys.common.dto.SysSiteDto;
import com.hww.sys.common.dto.SysUserDto;
import com.hww.sys.common.util.SysUtils;
import com.hww.ucenter.webadmin.service.SysSiteService;

@Component
public class AdminContextInterceptor extends HandlerInterceptorAdapter {
	private static final Logger log = Logger.getLogger(AdminContextInterceptor.class);

	public static final String PERMISSION_MODEL = "_permission_key";

	private String[] excludeUrls;
	private String loginUrl;
	private String returnUrl;
	private boolean auth;

	@Autowired
	private SessionProvider sessionProvider;

	@Autowired
	private SysSiteService sysSiteService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getRequestURI();
		String uri = getURI(request);
		log.info(uri);
		if (exclude(uri)) {
			return true;
		}

		Subject subject = SecurityUtils.getSubject();
		boolean loginPass = subject.isAuthenticated();
		Session session = subject.getSession();
		// 获得用户
		SysUserDto user = null;
		if (loginPass) {
			user = getUser(request, response, session);
		}

		// 用户为null跳转到登陆页面
		if (user == null) {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path;

			// response.sendRedirect(basePath + loginUrl);
			java.io.PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<script>");
			out.println("window.open ('" + basePath + loginUrl + "','_top')");
			out.println("</script>");
			out.println("</html>");
			out.close();
			return false;
		}

		boolean permPass = subject.isPermitted(uri);
		if (!permPass) {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path;

			// response.sendRedirect(basePath + loginUrl);
			java.io.PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<script>");
			out.println("window.open ('" + basePath + loginUrl + "','_top')");
			out.println("</script>");
			out.println("</html>");
			out.close();
			return false;
		}

		// 此时用户可以为null
		setUser(request, response, user);

		// 获得站点
		Integer oldSiteId = getSiteIdByCookie(request);
		SysSiteDto site = getSite(request, response, session);
		setSite(request, response, oldSiteId, site);

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("admincontextInterceptor");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
	}

	private void setUser(HttpServletRequest request, HttpServletResponse response, SysUserDto user) {
		SysUtils.setUser(request, user);
	}

	private SysUserDto getUser(HttpServletRequest request, HttpServletResponse response, Session session) {
		SysUserDto user = (SysUserDto) session.getAttribute(Constants.SESSION_USER);
		if (user == null) {
			user = (SysUserDto) sessionProvider.getAttribute(request, Constants.SESSION_USER);
		}
		return user;
	}

	*/
/**
	 * 按参数、cookie、域名、默认。
	 *
	 * @param request
	 * @return 不会返回null，如果站点不存在，则抛出异常。
	 *//*

	private SysSiteDto getSite(HttpServletRequest request, HttpServletResponse response, Session session) {
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

	private void setSite(HttpServletRequest request, HttpServletResponse response, Integer oldSiteId, SysSiteDto site) {
		System.out.println("oldSite:" + oldSiteId + "   site:" + site.getSiteId());
		if (sessionProvider == null) {
			System.out.println("sessionProvider为空");
		}
		if (!site.getSiteId().equals(oldSiteId)) {
			sessionProvider.setAttribute(request, response, Constants.SESSION_SITE, site);
		}
		SysUtils.setSite(request, site);
		SysSiteDto s = SysUtils.getSite(request);
	}

	private SysSiteDto getByParams(HttpServletRequest request, HttpServletResponse response) {
		SysSiteDto site = SysUtils.getSite(request);
		if (site != null) {
			return site;
		}
		String p = request.getParameter(Constants.SITE_PARAM);
		if (!StringUtils.isBlank(p)) {
			try {
				Integer siteId = Integer.parseInt(p);
				site = sysSiteService.findSiteById(siteId);
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

	private SysSiteDto getByCookie(HttpServletRequest request) {
		Cookie cookie = CookieUtils.getCookie(request, Constants.SITE_COOKIE);
		if (cookie != null) {
			String v = cookie.getValue();
			if (!StringUtils.isBlank(v)) {
				try {
					Integer siteId = Integer.parseInt(v);
					return sysSiteService.findSiteById(siteId);
				} catch (NumberFormatException e) {
					log.warn("cookie site id format exception", e);
				}
			}
		}
		return null;
	}

	private SysSiteDto getByDomain(HttpServletRequest request) {
		String domain = request.getServerName();
		if (!StringUtils.isBlank(domain)) {
			SysSiteDto search = new SysSiteDto();
			search.setDomain(domain);
			Pagination p = sysSiteService.list(search, 1, 1);
			if (p.getList().size() > 0) {
				return (SysSiteDto) p.getList().get(0);
			}
		}
		return null;
	}

	private SysSiteDto getByDefault() {
		Pagination p = sysSiteService.list(null, 1, 1);
		if (p.getList().size() > 0) {
			return (SysSiteDto) p.getList().get(0);
		} else {
			return null;
		}
	}

	private boolean exclude(String uri) {
		if (excludeUrls != null) {
			for (String exc : excludeUrls) {
				if (exc.equals(uri)) {
					return true;
				}
			}
		}
		return false;
	}

	private void createJsessionId(HttpServletRequest request, HttpServletResponse response) {
		String JSESSIONID = request.getSession().getId();// 获取当前JSESSIONID
		// （不管是从主域还是二级域访问产生）
		Cookie cookie = new Cookie("JSESSIONID", JSESSIONID);
		// cookie.setDomain(site.getBaseDomain()); //
		// 关键在这里，将cookie设成主域名访问，确保不同域之间都能获取到该cookie的值，从而确保session统一
		response.addCookie(cookie); // 将cookie返回到客户端
	}

	*/
/**
	 * 获得第三个路径分隔符的位置
	 *
	 * @param request
	 * @throws IllegalStateException
	 *//*

	private static String getURI(HttpServletRequest request) throws IllegalStateException {
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		String ctxPath = helper.getOriginatingContextPath(request);
		int start = 0, i = 0, count = 0;
		if (!StringUtils.isBlank(ctxPath)) {
			count++;
		}
		while (i < count && start != -1) {
			start = uri.indexOf('/', start + 1);
			i++;
		}
		if (start <= 0) {
			throw new IllegalStateException("admin access path not like '/admincp/...' pattern: " + uri);
		}
		return uri.substring(start);
	}

	public String[] getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(String[] excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public boolean isAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

}
*/
