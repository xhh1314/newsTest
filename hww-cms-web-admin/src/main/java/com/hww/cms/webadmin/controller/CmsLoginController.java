package com.hww.cms.webadmin.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hww.base.util.R;
import com.hww.cms.webadmin.config.CasConfig;
import com.hww.cms.webadmin.security.UserRealm;
import com.hww.framework.web.Constants;
import com.hww.framework.web.mvc.controller.AbsBaseController;
import com.hww.framework.web.session.SessionProvider;
import com.hww.sys.api.SysFeignClient;
import com.hww.sys.common.dto.SysRoleDto;
import com.hww.sys.common.dto.SysUserDto;

@Controller
public class CmsLoginController
	extends
		AbsBaseController {
	public static final String CAPTCHA_IMAGE_FORMAT = "jpeg";

	private static final Logger log = LoggerFactory.getLogger(CmsLoginController.class);

	@Resource
	private SessionProvider session;
	
	@Resource
	private SysFeignClient  sysFeignClient;
	
	@Autowired
	CasConfig casConfig;

	@RequestMapping(value = "login.do", method = RequestMethod.GET)
	public String loginGet(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		SysUserDto user = (SysUserDto) SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_USER);
		if(user==null) {
			log.info("打开登陆页面:"+casConfig.getLocalServerLoginUrl());
			return "redirect:"+casConfig.getLocalServerLoginUrl();
		}
		return "redirct:index.do";
	}

	@RequestMapping(value = "logout.do")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		//清除客户端--session
		//session.logout(request, response);
		//shiro退出java.lang.IllegalStateException: getAttribute: Session already invalidated
		//SecurityUtils.getSubject().logout();
		//重定向
        //WebUtils.issueRedirect(request, response, casConfig.getCasServerLogoutUrl());
		//cas-logout
		return "redirect:"+casConfig.getCasServerLogoutUrl();
	}
	
	@RequestMapping(value = "replaceRoleToLogin.do", method = RequestMethod.GET)
	public String replaceRoleToLogin(HttpServletRequest request, HttpServletResponse response, String roleId) {
		SysUserDto user = (SysUserDto) SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_USER);
		user.setDefaultRole(Long.parseLong(roleId));
		user.setCreateTime(null);
		user.setLastLoginTime(null);
		user.setLastModifyTime(null);
		sysFeignClient.updateUserByAdmin(user);
		//session.logout(request, response);
	    RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();  
	    UserRealm realm = (UserRealm)rsm.getRealms().iterator().next();  
	    realm.clearAuthz(); 
		return "redirect:"+casConfig.getLocalServerLoginUrl();
	}
	
	
	@RequestMapping(value = "loadRoleList.do", method = RequestMethod.POST)
	@ResponseBody
	public R loadRoleList(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		SysUserDto user = (SysUserDto) SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_USER);
		List<SysRoleDto> roleList = sysFeignClient.findRoleByUserId(user.getUserId());
		if(roleList == null) {
			R.error("当前用户没有角色");
		}
		return R.ok("操作成功").put("roleList", roleList);
	}
}
