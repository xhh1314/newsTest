//package com.hww.cms.webservice.feign;
//
//import com.hww.els.common.vo.SearchNewsVo;
//import com.hww.ucenter.common.dto.HUCenterFollowDto;
//import com.hww.ucenter.common.dto.HUCenterMessageDto;
//import com.hww.ucenter.common.dto.UCenterMemberDto;
//import com.hww.ucenter.common.dto.UserMessageDto;
//import com.hww.ucenter.common.entity.UCenterMessage;
//import com.hww.ucenter.common.entity.UCenterSign;
//import com.hww.ucenter.common.vo.UCenterMemberVo;
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@FeignClient(name = "hww-ucenter-web-service-consumer")
//public interface UserFeignClient {
//
//
//
////	@RequestMapping(value = "/ucenter/searchMembers.do")
////    List<UCenterMemberVo> searchMembers(@RequestBody SearchNewsVo vo);
//
////===================================================
//
////    @RequestMapping(value = "/ucenter/userInfoInFeginApi.do", method = RequestMethod.POST)
////    UCenterMemberDto findById(@RequestParam(value="memberId",required=false)Long memberId);
//
////    @RequestMapping(value = "/ucenter/concern/isConcern.do/{memberId}/{toMemberId}", method = RequestMethod.POST)
////    HUCenterFollowDto isConcern(@PathVariable("memberId") Long memberId, @PathVariable("toMemberId") Long toMemberId);
//    
////    @RequestMapping(value = "/ucenter/concernUser.do/{memberId}", method = RequestMethod.POST)
////    String concernUser(@PathVariable("memberId") Long memberId);
//    
////    @RequestMapping(value = "/ucenter/concernUserCount.do/{memberId}", method = RequestMethod.POST)
////    Integer concernUserCount(@PathVariable("memberId") Long memberId);
//  
////    @RequestMapping(value = "/ucenter/sign/oneCuSign.do/{memberId}", method = RequestMethod.GET)
////    List<UCenterSign> oneCuSign(@PathVariable("memberId") Long memberId);
//    
////    @RequestMapping(value = "/ucenter/myConcernCount.do/{memberId}", method = RequestMethod.POST)
////    Integer myConcernCount(@PathVariable("memberId") Long memberId);
//    
////    @RequestMapping(value = "/ucenter/message/save.do", method = RequestMethod.POST)
////    UserMessageDto messageSave(@RequestBody UserMessageDto userMessageDto);
//}