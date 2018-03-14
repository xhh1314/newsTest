//package com.hww.app.webservice.feign;
//
//import com.hww.ucenter.common.dto.HUCenterFollowDto;
//import com.hww.ucenter.common.dto.UCenterMemberDto;
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.*;
//
//
//
//@FeignClient(name = "hww-ucenter-web-service-consumer")
//public interface UserFeignClient {
//
//    @RequestMapping(value = "/ucenter/userInfoInFeginApi.do", method = RequestMethod.POST)
//    UCenterMemberDto findById(@RequestParam(value = "memberId", required = false) Long memberId);
//
////    @RequestMapping(value = "/ucenter/searchMembers.do", method = RequestMethod.POST)
////    List<UCenterMemberVo> searchMembers(@RequestBody AppSearchDto search);
//
//    @RequestMapping(value = "/ucenter/concern/isConcern.do/{memberId}/{toMemberId}", method = RequestMethod.POST)
//    HUCenterFollowDto isConcern(@PathVariable("memberId") Long memberId, @PathVariable("toMemberId") Long toMemberId);
//
//}
