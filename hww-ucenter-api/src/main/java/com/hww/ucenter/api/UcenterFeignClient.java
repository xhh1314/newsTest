package com.hww.ucenter.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.hww.base.util.R;
import com.hww.sys.common.dto.SysUserDto;
import com.hww.ucenter.common.dto.HUCenterFollowDto;
import com.hww.ucenter.common.dto.MememberSnsDisableDto;
import com.hww.ucenter.common.dto.UCenterMemberDto;
import com.hww.ucenter.common.dto.UserMessageDto;

import java.util.List;

@FeignClient(name = "hww-ucenter-web-service-consumer")
public interface UcenterFeignClient {

	@RequestMapping(value = "/ucenter/userInfoInFeginApi.do", method = RequestMethod.POST)
	UCenterMemberDto findById(@RequestParam(value = "memberId", required = false) Long memberId);

	@RequestMapping(value = "/ucenter/concern/isConcern.do/{memberId}/{toMemberId}", method = RequestMethod.POST)
	HUCenterFollowDto isConcern(@PathVariable("memberId") Long memberId, @PathVariable("toMemberId") Long toMemberId);

	@RequestMapping(value = "/ucenter/concern/concernUsersFeginApi.do/{memberId}", method = RequestMethod.POST)
	String concernUser(@PathVariable("memberId") Long memberId);

	@RequestMapping(value = "/ucenter/concern/concernUsersCountFeginApi.do/{memberId}", method = RequestMethod.POST)
	Integer concernUserCount(@PathVariable("memberId") Long memberId);

	@RequestMapping(value = "/ucenter/message/saveMsgFeginApi.do", method = RequestMethod.POST)
	UserMessageDto messageSave(@RequestBody UserMessageDto userMessageDto);

	// sys 创建用户同步-ucenterber里
	@RequestMapping(value = "/ucenter/user/userSaveInFeginApi.do")
	@ResponseBody
	Long userSaveInFeginApi(@RequestBody SysUserDto dto);

	@RequestMapping(value = "/ucenter/userInfoInFeginApi.do")
	UCenterMemberDto userInfoInFeginApi(@RequestParam(value = "memberId") Long memberId);

	@RequestMapping(value = "/ucenter/setMememberSnsDisabledFeginApi.do")
	R setMememberSnsDisabled(@RequestBody MememberSnsDisableDto dto);

	@RequestMapping(value = "deleteByCommonIdFeginApi.do", method = { RequestMethod.POST })
	R deleteByCommonIdFeginApi(@RequestParam("commonId") Long commonId);

	@RequestMapping(value = "/ucenter/concern/listAllFollowMemberId.do")
	List<Long> listAllFollowMemberId(@RequestParam("memberId") Long memberId);

}