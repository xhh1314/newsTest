package com.hww.ucenter.webadmin.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hww.base.util.R;
import com.hww.sys.api.SysFeignClient;
import com.hww.sys.common.dto.SysMenuDto;
import com.hww.sys.common.dto.SysRoleDto;
import com.hww.sys.common.dto.SysSiteDto;
import com.hww.sys.common.dto.SysUserDto;
import com.hww.ucenter.api.UcenterFeignClient;
import com.hww.ucenter.common.dto.MememberSnsDisableDto;
import com.hww.ucenter.common.dto.UCenterMemberDto;
import com.hww.ucenter.webadmin.service.SysAuthService;

@Service("sysAuthServiceImpl")
@Transactional
public class SysAuthServiceImpl implements SysAuthService{

	@Autowired
	SysFeignClient sysFeignClient;
	@Autowired
	UcenterFeignClient uCenterFeignClient;
	
	@Override
	public SysUserDto findUserByName(String username) {
		
		return sysFeignClient.findUser(username);
	}

	@Override
	public SysSiteDto findSiteById(Integer id) {
		
		return sysFeignClient.findSiteById(id);
	}

	@Override
	public List<SysSiteDto> list(SysSiteDto searchDto) {
		
		return sysFeignClient.findSitelist(searchDto);
	}

	@Override
	public List<SysMenuDto> findsMenusByRoles(Long roleId) {
		
		return sysFeignClient.findByRole(roleId);
	}

	@Override
	public List<SysRoleDto> findRole() {
		
		return sysFeignClient.allList();
	}

	
//	@Override
//	public List<SysUserDto> findUserList() {
//		// TODO Auto-generated method stub
//		return sysFeignClient.findUserList();
//	}

	

	@Override
	public UCenterMemberDto userInfoInFeginApi(Long memberId) {
	
		return uCenterFeignClient.userInfoInFeginApi(memberId);
	}

	@Override
	public R setMememberSnsDisabled(MememberSnsDisableDto dto) {
		return uCenterFeignClient.setMememberSnsDisabled(dto);
	}

}
