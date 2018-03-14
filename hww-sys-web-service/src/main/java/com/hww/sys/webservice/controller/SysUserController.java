package com.hww.sys.webservice.controller;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hww.base.util.R;
import com.hww.sys.common.dto.SysUserDto;
import com.hww.sys.webservice.service.SysGroupService;
import com.hww.sys.webservice.service.SysRoleService;
import com.hww.sys.webservice.service.SysUserService;


@Controller
@RequestMapping("/sys/user")
public class SysUserController{
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysGroupService sysGroupService;
	@Autowired
	private SysRoleService sysRoleService;
	
	
	
	@RequestMapping("find_user.do")
	@ResponseBody
	public SysUserDto findUser(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="username") String userName) {
		SysUserDto sysUserDto=sysUserService.findUserByName(userName);
		return sysUserDto;
	}
	@RequestMapping("saveUser.do")
	@ResponseBody
	public String saveUser(HttpServletRequest request, HttpServletResponse response, @RequestBody SysUserDto sysUserDto) {
		    sysUserService.saveUser(sysUserDto);
			return "success";
	}
	
	@RequestMapping("queryUserlist.do")
	@ResponseBody
	public List<SysUserDto> queryUserListByAdmin(){
		return sysUserService.queryUserList();
	}

	@RequestMapping("delUser.do")
	@ResponseBody
	public R delUserListByAdmin(SysUserDto dto){
		sysUserService.delUser(dto);
		return R.ok();
	}

	@RequestMapping("updateUserByAdmin.do")
	@ResponseBody
	public R updateUserByAdmin(@RequestBody SysUserDto dto){
		sysUserService.updateUserDefaultRole(dto.getUserId(), dto.getDefaultRole());
		return R.ok();
	}
	@RequestMapping("updateUserPassword.do")
	@ResponseBody
	public R updateUserPassword(@RequestBody SysUserDto dto) {
	 R r=null;
	try {
		sysUserService.updateUserPassword(dto);
		 r=R.ok();
	} catch (Exception e) {
		r=R.error();
	}
		return r;
		 
	 }
	
	
	
	
	
}
