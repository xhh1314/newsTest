package com.hww.ucenter.webadmin.controller;

import com.hww.base.util.R;
import com.hww.framework.web.Constants;
import com.hww.framework.web.mvc.controller.AbsBaseController;
import com.hww.framework.web.session.SessionProvider;
import com.hww.sys.api.SysFeignClient;
import com.hww.sys.common.dto.SysRoleDto;
import com.hww.sys.common.dto.SysUserDto;
import com.hww.ucenter.webadmin.config.CasConfig;
import com.hww.ucenter.webadmin.security.UserRealm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class UCenterLoginController extends AbsBaseController {
	public static final String CAPTCHA_IMAGE_FORMAT = "jpeg";

	private static final Logger log = LoggerFactory.getLogger(UCenterLoginController.class);

	@Resource
	private SessionProvider session;

	@Autowired
	CasConfig casConfig;
	
	@Resource
	private SysFeignClient  sysFeignClient;

	@RequestMapping(value = "login.do", method = RequestMethod.GET)
	public String loginGet(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		log.info("打开登陆页面:" + casConfig.getLocalServerLoginUrl());
		return "redirect:" + casConfig.getLocalServerLoginUrl();
	}

	@RequestMapping(value = "logout.do")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		// 清除客户端--session
		// session.logout(request, response);
		// shiro退出java.lang.IllegalStateException: getAttribute: Session already
		// invalidated
		// SecurityUtils.getSubject().logout();
		// 重定向
		// WebUtils.issueRedirect(request, response, casConfig.getCasServerLogoutUrl());
		// cas-logout
		return "redirect:" + casConfig.getCasServerLogoutUrl();
	}

	/**
	 * 单点登出cas回调地址
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "singleLogout.do")
	public void singleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 清除客户端--session
		session.logout(request, response);
		// shiro退出java.lang.IllegalStateException: getAttribute: Session already
		// invalidated
		SecurityUtils.getSubject().logout();
		// 重定向
		WebUtils.issueRedirect(request, response, casConfig.getCasServerLogoutUrl());
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
