package com.hww.sys.webservice.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hww.sys.common.dto.SysMenuDto;
import com.hww.sys.common.dto.SysRoleDto;
import com.hww.sys.webservice.service.SysRoleService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/sys/role")
public class SysRoleController{
	@Resource
	private SysRoleService sysRoleService;
	
	@ApiOperation(value = "角色", notes ="")
	@RequestMapping(value = "v_all_list.do", method = RequestMethod.POST)
	@ResponseBody
	public List<SysRoleDto> allList() {
		return sysRoleService.allList();
	}
	@RequestMapping("menu_list.do")
	@ResponseBody
	public List<SysMenuDto> findByRole(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="roleId") Long roleId){
		List<SysMenuDto> sysMenuDto= sysRoleService.findsMenusByRoles(roleId);
		return sysMenuDto;
	}
	
	
	
	@RequestMapping("roleListByUserId.do")
	@ResponseBody
	public List<SysRoleDto> findRoleByUserId(@RequestParam(value="userId") Long userId){
		List<SysRoleDto> sysRoleDto= sysRoleService.findByUserId(userId);
		return sysRoleDto;
	}
	
}
