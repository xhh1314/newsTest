package com.hww.sys.webadmin.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hww.framework.web.Constants;
import com.hww.framework.web.mvc.controller.AbsBaseController;
import com.hww.framework.web.session.SessionProvider;
import com.hww.sys.common.dto.SysUserDto;
import com.hww.sys.common.vo.SysLoginVo;
import com.hww.sys.webadmin.config.CasConfig;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

@Controller
public class SysLoginController
	extends
		AbsBaseController {
	public static final String CAPTCHA_IMAGE_FORMAT = "jpeg";

	private static final Logger log = LoggerFactory.getLogger(SysLoginController.class);

	@Resource
	private SessionProvider session;
	
	@Autowired
	CasConfig casConfig;

	@Resource
	private ImageCaptchaService captchaService;

	@RequestMapping(value = "login.do", method = RequestMethod.GET)
	public String loginGet(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		log.info("打开登陆页面:"+casConfig.getLocalServerLoginUrl());
		return "redirect:"+casConfig.getLocalServerLoginUrl();
	}

	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public String loginPost(HttpServletRequest request, HttpServletResponse response, SysLoginVo vo) {
		log.info("进行登陆操作");

		Boolean catpcha = false;
		try {
			catpcha = captchaService.validateResponseForID(session.getSessionId(request, response), vo.getCaptcha());
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (!catpcha) {
			log.info("sssss-------------" + session.getSessionId(request, response));
			return "login";
		}
		UsernamePasswordToken token = new UsernamePasswordToken(vo.getUsername(), vo.getPassword());
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
		} catch (UnknownAccountException uae) {

			log.info("username wasn't in the system.");

		} catch (IncorrectCredentialsException ice) {

			log.info("password didn't match.");

		} catch (LockedAccountException lae) {

			log.info("account for that username is locked - can't login.");

		} catch (AuthenticationException ae) {

			log.info("unexpected condition.");
			ae.printStackTrace();

		}
		/*
		 * SysUser user = SysUserMng.findUniqueByProperty("username",
		 * form.getUsername()); if (user != null) { if
		 * (user.getDisabled().shortValue() == 1)// 禁用 { // 如果已经禁用，则退出登录。
		 * sessionProvider.logout(request, response); } else { if
		 * (pwdEncoder.isPasswordValid(user.getPassword(), form.getPassword()))
		 * { CookieUtils.cancleCookie(request, response, SITE_COOKIE, null);
		 * sessionProvider.setAttribute(request, response, SESSION_USER, user);
		 * SysSite site = user.getSysSite(); site.getSiteName();
		 * sessionProvider.setAttribute(request, response, SESSION_SITE, site);
		 * UsernamePasswordToken token = new UsernamePasswordToken(
		 * user.getUsername(), form.getPassword()); Subject subject =
		 * SecurityUtils.getSubject(); subject.login(token); return
		 * "redirect:index.do"; } } }
		 */
		return "index.do";

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

	@GetMapping(value = "captcha.svl")
	public void captcha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		byte[] captchaChallengeAsJpeg = null;
		// the output stream to render the captcha image as jpeg into
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			// get the session id that will identify the generated captcha.
			// the same id must be used to validate the response, the session id
			// is a good candidate!

			String captchaId = session.getSessionId(request, response);
			BufferedImage challenge = captchaService.getImageChallengeForID(captchaId, request.getLocale());
			// Jimi.putImage("image/jpeg", challenge, jpegOutputStream);
			ImageIO.write(challenge, CAPTCHA_IMAGE_FORMAT, jpegOutputStream);
		} catch (IllegalArgumentException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		} catch (CaptchaServiceException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		// catch (JimiException e) {
		// response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		// return;
		// }

		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

		// flush it in the response
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/" + CAPTCHA_IMAGE_FORMAT);

		ServletOutputStream responseOutputStream = response.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);
		responseOutputStream.flush();
		responseOutputStream.close();
	}
}
