//package com.hww.ucenter.webadmin.fegin;
//
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.hww.base.util.R;
//import com.hww.ucenter.common.dto.MememberSnsDisableDto;
//import com.hww.ucenter.common.dto.UCenterMemberDto;
//
//@FeignClient(name = "hww-ucenter-web-service-consumer")
//public interface UCenterFeignClient {
//	 @RequestMapping(value = "/ucenter/userInfoInFeginApi.do")
//	 public UCenterMemberDto userInfoInFeginApi(@RequestParam(value = "memberId") Long memberId);
//	 @RequestMapping(value = "/ucenter/setMememberSnsDisabledFeginApi.do")
//	 public R setMememberSnsDisabled(@RequestBody MememberSnsDisableDto dto);
//}	
