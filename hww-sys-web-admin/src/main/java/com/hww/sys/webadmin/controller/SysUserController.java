package com.hww.sys.webadmin.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.hww.sys.common.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hww.base.common.page.Pagination;
import com.hww.base.util.ArrayUtils;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.IPUtil;
import com.hww.base.util.StringUtils;
import com.hww.framework.web.mvc.controller.AbsBaseController;
import com.hww.sys.common.dto.SysGroupDto;
import com.hww.sys.common.dto.SysRoleDto;
import com.hww.sys.common.dto.SysUserDto;
import com.hww.sys.common.dto.SysUserRRoleDto;
import com.hww.sys.common.entity.SysUser;
import com.hww.sys.common.entity.SysUserRRole;
import com.hww.sys.common.manager.SysUserMng;
import com.hww.sys.common.manager.SysUserRRoleMng;
import com.hww.sys.common.util.SysUtils;
import com.hww.sys.common.vo.SysGroupCheckBoxVo;
import com.hww.sys.common.vo.SysRoleCheckBoxVo;
import com.hww.sys.common.vo.SysUserVo;
import com.hww.sys.webadmin.Constants;
import com.hww.sys.webadmin.service.SysGroupService;
import com.hww.sys.webadmin.service.SysRoleService;
import com.hww.sys.webadmin.service.SysUserRRoleService;
import com.hww.sys.webadmin.service.SysUserService;
import com.hww.ucenter.api.UcenterFeignClient;

@Controller
@RequestMapping("/user")
public class SysUserController extends AbsBaseController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysGroupService sysGroupService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysUserRRoleService sysUserRRoleService;
	@Resource
	SysUserRRoleMng sysUserRRoleMng;
	@Autowired
	SysUserMng sysUserMng;
	@Autowired
	UcenterFeignClient uCenterFeignClient;

	@RequestMapping(value = "index.do")
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "user/index";
	}

	@RequestMapping("v_list.do")
	public String list(HttpServletRequest request, SysUserVo vo, ModelMap model) {
		Integer siteId = SysUtils.getSiteId(request);
		SysUserDto searchDto = new SysUserDto();
		searchDto.setSiteId(siteId);
		Pagination p = sysUserService.page(searchDto, vo.getPageNo(), 10);

		if (p != null && p.getList() != null) {
			List<SysUserVo> userVoList = BeanMapper.mapList(p.getList(), SysUserVo.class);
			p.setList(userVoList);
		}

		model.addAttribute("page", p);
		model.addAttribute("form", vo);
		return "user/list";
	}

	@RequestMapping("v_add.do")
	public String add(HttpServletRequest request) {
		return "user/add";
	}

	@RequestMapping("v_edit.do")
	public String edit(HttpServletRequest request, HttpServletResponse response, SysUserVo vo, ModelMap model) {
		SysUserDto userDto = sysUserService.findUserById(vo.getUserId());
		SysUserVo userVo = BeanMapper.map(userDto, SysUserVo.class);
		model.addAttribute("entity", userVo);
		return "user/edit";
	}

	@RequestMapping("o_save.do")
	@ResponseBody
	public Boolean save(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute("form") SysUserVo form, BindingResult errors, String password) {

		if (errors.hasErrors()) {
			return false;
		}

		Integer siteId = SysUtils.getSiteId(request);
		SysUserDto userDto = BeanMapper.map(form, SysUserDto.class);
		// 判断用户名是否存在
		SysUserDto user = sysUserService.findUserByName(userDto.getUsername());
		if (Objects.nonNull(user)) {
			return false;
		}
		if (form.getIsAllCategory() == null) {
			userDto.setIsAllCategory((short) 1);
		}
		userDto.setSiteId(siteId);
		userDto.setPassword(password);
		userDto.setEmail(form.getEmail());
		// 角色id
		userDto.setDefaultRole(userDto.getDefaultRole());
		String ipAddress = IPUtil.getIpAddress(request);
		userDto.setRegisterIp(ipAddress);
		userDto.setStatus((short) 1);// 已激活

		try {
			sysUserService.saveUser(userDto);
			SysUserDto userByName = sysUserService.findUserByName(userDto.getUsername());
			SysUserRRoleDto userRRoleDto = new SysUserRRoleDto();
			userRRoleDto.setRoleId(userByName.getDefaultRole());
			userRRoleDto.setUserId(userByName.getUserId());
			sysUserRRoleService.saveSysUserRRole(userRRoleDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@RequestMapping("o_update.do")
	@ResponseBody
	@Transactional
	public Boolean update(HttpServletRequest request, HttpServletResponse response, ModelMap model, @Valid SysUserVo vo,
			BindingResult result, String password) {
		if (result.hasErrors()) {
			return false;
		}

		SysUserDto userDto = BeanMapper.map(vo, SysUserDto.class);
		if (userDto != null) {
			userDto.setEmail(vo.getEmail());
			userDto.setDefaultRole(vo.getDefaultRole());
			SysUser entity = sysUserMng.find(vo.getUserId());
			entity.setEmail(userDto.getEmail());
			entity.setUsername(userDto.getUsername());
			entity.setDefaultRole(userDto.getDefaultRole());
			entity.setStatus(userDto.getStatus());
			if (userDto.getIsSelfAdmin() == null) {
				userDto.setIsSelfAdmin((short) 0);
			}
			if (userDto.getIsAdmin() == null) {
				userDto.setIsAdmin((short) 0);
			}

			if (userDto.getIsAllCategory() == null) {
				userDto.setIsAllCategory((short) 0);
			}

			if (userDto.getIsSelfAdmin() != entity.getIsSelfAdmin()) {
				entity.setIsSelfAdmin(userDto.getIsSelfAdmin());
			}

			if (userDto.getIsAdmin() != entity.getIsAdmin()) {
				entity.setIsAdmin(userDto.getIsAdmin());
			}

			if (userDto.getIsAllCategory() != entity.getIsAllCategory()) {
				entity.setIsAllCategory(userDto.getIsAllCategory());
			}
			uCenterFeignClient.userSaveInFeginApi(userDto);
			sysUserMng.update(entity);
		
			
			
			// sysUserService.updateUser(userDto);
			if (userDto.getDefaultRole() != null) {
				// 跟新角色用户表
				SysUserRRole sysUserRRole = sysUserRRoleMng.find(userDto.getUserId());
				sysUserRRole.setRoleId(userDto.getDefaultRole());
				sysUserRRoleMng.update(sysUserRRole);
			}
			return true;
		} else {
			return false;
		}

		// model.addAttribute("entity", entity);
		// return "user/edit";
	}

	@RequestMapping(value = "v_role_checkbox.do")
	public String rolecheckbox(HttpServletRequest request, HttpServletResponse response, SysUserVo vo, ModelMap model) {
		Integer siteId = SysUtils.getSiteId(request);
		SysRoleDto roleSearchDto = new SysRoleDto();
		roleSearchDto.setSiteId(siteId);
		List<SysRole> roles=sysRoleService.allList();
		List<SysRoleDto> allRoleList = BeanMapper.mapList(roles,SysRoleDto.class);
		SysUserDto userDto = sysUserService.findUserWithRoleById(vo.getUserId());
		String selectedRoleIds = userDto.getRoleIds();
		Set<Long> selectedRoleIdsSet = null;
		if (StringUtils.isNoneBlank(selectedRoleIds)) {
			String[] roleIds = selectedRoleIds.split(",");
			if (roleIds != null) {
				selectedRoleIdsSet = new HashSet<Long>(roleIds.length);
				for (String roleId : roleIds) {
					if (StringUtils.isNumeric(roleId)) {
						selectedRoleIdsSet.add(Long.parseLong(roleId));
					}
				}
			}
		}
		List<SysRoleCheckBoxVo> rolelist = new ArrayList<SysRoleCheckBoxVo>(allRoleList.size());
		for (SysRoleDto aSysRole : allRoleList) {
			if (selectedRoleIdsSet != null && selectedRoleIdsSet.contains(aSysRole.getRoleId())) {
				rolelist.add(new SysRoleCheckBoxVo(aSysRole.getRoleId(), aSysRole.getRoleName(), true));
			} else {
				rolelist.add(new SysRoleCheckBoxVo(aSysRole.getRoleId(), aSysRole.getRoleName(), false));
			}
		}

		model.addAttribute("form", vo);
		model.addAttribute("entity", userDto);
		model.addAttribute("rolelist", rolelist);
		return "user/rolecheckbox";

	}

	@RequestMapping("o_update_role.do")
	@ResponseBody
	public Boolean updaterole(HttpServletRequest request, HttpServletResponse response, Long userId, Long[] roleIds) {

		SysUserDto userDto = new SysUserDto();
		userDto.setUserId(userId);
		StringBuffer roleIdes = new StringBuffer();
		for (Long roleId : roleIds) {
			roleIdes.append(roleId);
			roleIdes.append(",");
		}
		userDto.setRoleIds(roleIdes.toString());
		sysUserService.updateUser(userDto);
		return true;
	}

	@RequestMapping("v_group_checkbox.do")
	public String groupcheckbox(HttpServletRequest request, HttpServletResponse response, SysUserVo vo,
			ModelMap model) {
		Integer siteId = SysUtils.getSiteId(request);
		SysGroupDto groupSearchDto = new SysGroupDto();
		groupSearchDto.setSiteId(siteId);
		List<SysGroupDto> allGroupList = (List<SysGroupDto>) sysGroupService.list(groupSearchDto, 1, 50).getList();
		SysUserDto userDto = sysUserService.findUserWithGroupById(vo.getUserId());
		String selectedGroupIds = userDto.getGroupIds();
		Set<Long> selectedGroupIdsSet = null;
		if (StringUtils.isNoneBlank(selectedGroupIds)) {
			String[] groupIds = selectedGroupIds.split(",");
			if (groupIds != null) {
				selectedGroupIdsSet = new HashSet<Long>(groupIds.length);
				for (String groupId : groupIds) {
					if (StringUtils.isNumeric(groupId)) {
						selectedGroupIdsSet.add(Long.parseLong(groupId));
					}
				}
			}

		}

		List<SysGroupCheckBoxVo> grouplist = new ArrayList<SysGroupCheckBoxVo>(allGroupList.size());
		for (SysGroupDto aSysGroup : allGroupList) {
			if (selectedGroupIdsSet != null && selectedGroupIdsSet.contains(aSysGroup.getGroupId())) {
				grouplist.add(new SysGroupCheckBoxVo(aSysGroup.getGroupId(), aSysGroup.getGroupName(), true));
			} else {
				grouplist.add(new SysGroupCheckBoxVo(aSysGroup.getGroupId(), aSysGroup.getGroupName(), false));
			}
		}

		model.addAttribute("form", vo);
		model.addAttribute("entity", userDto);
		model.addAttribute("grouplist", grouplist);
		return "user/groupcheckbox";
	}

	@RequestMapping("o_update_group.do")
	@ResponseBody
	public Boolean updategroup(HttpServletRequest request, HttpServletResponse response, Long userId, Long[] groupIds) {

		SysUserDto userDto = new SysUserDto();
		userDto.setUserId(userId);
		StringBuffer groupIdes = new StringBuffer();
		if (groupIds != null && groupIds.length > 0) {
			for (Long groupId : groupIds) {
				groupIdes.append(groupId);
				groupIdes.append(",");
			}
			userDto.setGroupIds(groupIdes.toString());
		}
		sysUserService.updateUser(userDto);
		return true;
	}

	@RequestMapping("o_delete.do")
	public String delete(HttpServletRequest request, HttpServletResponse response, SysUserVo vo) {
		Collection<Long> userIds = null;
		if (vo != null) {
			if (StringUtils.isNotBlank(vo.getAllIDCheck())) {
				String[] ids = StringUtils.split(vo.getAllIDCheck(), ",");
				if (ArrayUtils.isNotEmpty(ids)) {
					userIds = new ArrayList<Long>(ids.length);
				}
				for (String str : ids) {
					if (StringUtils.isNumeric(str)) {
						userIds.add(Long.parseLong(str));
					}
				}
			}
			if (vo.getSiteId() != null) {
				userIds = new ArrayList<Long>(1);
				userIds.add(vo.getUserId());
			}
		}
		if (userIds != null) {
			sysUserService.updateUserStatusByIds(Constants.USER_STATUS_DELETED, userIds);
		}
		return "redirect:v_list.do";
	}
}
