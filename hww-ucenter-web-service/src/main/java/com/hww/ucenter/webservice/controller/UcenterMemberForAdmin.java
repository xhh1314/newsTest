package com.hww.ucenter.webservice.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hww.sys.common.dto.SysUserDto;
import com.hww.ucenter.common.entity.UCenterMember;
import com.hww.ucenter.common.vo.UCenterMemberVo;
import com.hww.ucenter.webservice.service.UCenterMemberService;
@Controller
@RequestMapping("/ucenter/user")
public class UcenterMemberForAdmin {
	
	
	
	
	
	
	 @Autowired
	    private UCenterMemberService UCenterMemberService;
	 @RequestMapping(value = "userSaveInFeginApi.do", method = {RequestMethod.GET, RequestMethod.POST})
	    @ResponseBody
	    public Long userSaveInFeginApi(@RequestBody SysUserDto dto) {
		 if(dto.getUserId()==null) {
	    	UCenterMember member=new UCenterMember();
	    	member.setEmail(dto.getEmail());
	    	member.setPassword(dto.getPassword());
	    	member.setStatus(Short.valueOf("1"));
	    	member.setSnsDisabled(0);
	    	member.setSiteId(1);
	    	member.setPhoneNo("0");
	    	member.setCountryId(Long.valueOf("1"));
	    	member.setCountryName("中国");
	    	member.setCreateTime(new Timestamp(System.currentTimeMillis()));
	    	member.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
	    	if(dto.getRealName()!=null) {
	    	   member.setMemberName(dto.getRealName());
	    	}
	    	member.setNickName(dto.getUsername());
	    	Long userId=UCenterMemberService.saveUser(member);
			return userId;
		 }else {
			 UCenterMemberVo vo=UCenterMemberService.getUCenterMemberById(dto.getUserId());
			 if(dto.getEmail()!=null) {
			 vo.setEmail(dto.getEmail());
			 }
			 vo.setNickName(dto.getUsername());
			 vo.setPassword(dto.getPassword());
			 vo.setStatus(dto.getStatus());
			 UCenterMemberService.updateUCenterMember(vo);
			 
			 return dto.getUserId();
		 }
	    	
	    }

}
