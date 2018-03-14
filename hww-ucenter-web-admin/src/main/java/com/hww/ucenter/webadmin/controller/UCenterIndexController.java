package com.hww.ucenter.webadmin.controller;

import com.hww.framework.web.mvc.controller.AbsBaseController;
import com.hww.framework.web.session.SessionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UCenterIndexController extends AbsBaseController {

	@Autowired
	private SessionProvider sessionProvider;

	@RequestMapping("index.do")
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		/*
		 * SysUser user = (SysUser) sessionProvider.getAttribute(request, SESSION_USER);
		 * model.addAttribute("user", user);
		 * 
		 * // 需要获得站点列表 List<SysSite> siteList = (List<SysSite>) SysSiteMng.find(1, 10)
		 * .getList(); model.addAttribute("siteList", siteList); SysSite site =
		 * (SysSite) sessionProvider.getAttribute(request, SESSION_SITE);
		 * 
		 * model.addAttribute("site", site);
		 */

		return "index";

	}

	@RequestMapping("console.do")
	public String console(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		return "console";

	}

}
