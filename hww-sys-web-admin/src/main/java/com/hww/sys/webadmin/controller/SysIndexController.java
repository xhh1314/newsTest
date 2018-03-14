package com.hww.sys.webadmin.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hww.base.util.ArrayListUtils;
import com.hww.framework.web.Constants;
import com.hww.framework.web.mvc.controller.AbsBaseController;
import com.hww.framework.web.session.SessionProvider;
import com.hww.sys.common.dto.SysSiteDto;
import com.hww.sys.common.dto.SysUserDto;
import com.hww.sys.common.util.SysUtils;
import com.hww.sys.webadmin.dto.SysMenuDto;
import com.hww.sys.webadmin.service.SysGroupService;
import com.hww.sys.webadmin.service.SysMenuService;
import com.hww.sys.webadmin.service.SysRoleService;
import com.hww.sys.webadmin.service.SysSiteService;
import com.hww.sys.webadmin.service.SysUserService;

@Controller
public class SysIndexController extends AbsBaseController {
	@Resource
	SysMenuService sysMenuService;

	@Resource
	SysSiteService sysSiteService;

	@Resource
	SysRoleService sysRoleService;

	@Resource
	SysGroupService sysGroupService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SessionProvider sessionProvider;

	@RequestMapping("index.do")
	public String index(HttpServletRequest request,
			HttpServletResponse response, ModelMap model, Integer node) {

		SysUserDto user = (SysUserDto) sessionProvider.getAttribute(request,
				Constants.SESSION_USER);
		model.addAttribute("user", user);

		// SecurityUtils.getSubject().getPreviousPrincipals().getPrimaryPrincipal().
		// // 需要获得站点列表
		// List<SysSite> siteList = ((List<SysSite>)sysSiteService.find(1,
		// 10)).getList();
		// model.addAttribute("siteList", siteList);
		SysSiteDto site = (SysSiteDto) sessionProvider.getAttribute(request,
				Constants.SESSION_SITE);
		// 查询所有站点
		List<SysSiteDto> sites = sysSiteService.findAllSite();
		sessionProvider.setAttribute(request, response, "sites", sites);

		Integer siteId = SysUtils.getSiteId(request);
		Long userId = SysUtils.getUserId(request);

		if (user.getIsAdmin() == 1) {
			List<SysMenuDto> list = sysMenuService.getRetrievingFullTree(userId,
					siteId, null, null);

			ArrayList<SysMenuDto> modulteList = getMenu(list);

			sessionProvider.setAttribute(request, response, "modulteList",
					modulteList);
		} else {

			// TODO 查询所有的分组,并查询出分组的权限
			SysUserDto sysUserDto = sysUserService
					.findUserWithGroupById(userId);

			String groupIds = sysUserDto.getGroupIds();

			List<SysMenuDto> groupMenu = null;
			if (groupIds != null) {
				groupMenu = sysGroupService.findMenusByGroups(groupIds);
			}

			// TODO 查询出所有的角色并并查处所有角色的权限
			SysUserDto userDto = sysUserService.findUserWithRoleById(userId);

			List<SysMenuDto> roleMenu = null;
			String roleIds = userDto.getRoleIds();
			if (roleIds != null) {
				roleMenu = sysRoleService.findMenusByRoles(roleIds);
				//roleMenu = sysRoleService.findsMenusByRoles(userDto.getDefaultRole());
				
			}

			roleMenu.addAll(groupMenu);

			List<SysMenuDto> list = ArrayListUtils.removeDuplicateWithOrder(roleMenu);

			ArrayList<SysMenuDto> modulteList = getMenu(list);

			sessionProvider.setAttribute(request, response, "modulteList",
					modulteList);

		}
		return "index";

	}

	private ArrayList<SysMenuDto> getMenu(List<SysMenuDto> list) {
		ArrayList<SysMenuDto> modulteList = new ArrayList<>();

		if (list.size() > 0) {

			for (SysMenuDto sysMenuDto : list) {

				if (sysMenuDto.getMenuName().equals("根")) {
					continue;
				}

				if (sysMenuDto.getParentId() == 1) {

					modulteList.add(sysMenuDto);
				}
			}

		}

		if (modulteList.size() > 0) {
			for (SysMenuDto sysMenuDto : modulteList) {
				if (sysMenuDto.getDepth() != 0) {
					Long menuId = sysMenuDto.getMenuId();

					for (SysMenuDto allMenuDto : list) {
						if (allMenuDto.getParentId() == menuId) {
							sysMenuDto.getList().add(allMenuDto);
						}
					}
				}

			}

		}
		return modulteList;
	}

}
