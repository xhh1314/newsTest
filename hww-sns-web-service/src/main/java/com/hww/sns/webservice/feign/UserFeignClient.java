//package com.hww.sns.webservice.feign;
//
//import com.hww.ucenter.common.dto.HUCenterFollowDto;
//import com.hww.ucenter.common.dto.HUCenterMessageDto;
//import com.hww.ucenter.common.dto.UCenterMemberDto;
//import com.hww.ucenter.common.dto.UserMessageDto;
//import com.hww.ucenter.common.entity.UCenterMessage;
//import com.hww.ucenter.common.entity.UCenterSign;
//import com.hww.ucenter.common.util.R;
//
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@FeignClient(name = "hww-ucenter-web-service-consumer")
//public interface UserFeignClient {
//
//    @RequestMapping(value = "/ucenter/userInfoInFeginApi.do", method = RequestMethod.POST)
//    UCenterMemberDto findById(@RequestParam(value="memberId",required=false)Long memberId);
//
//    @RequestMapping(value = "/ucenter/concern/isConcern.do/{memberId}/{toMemberId}", method = RequestMethod.POST)
//    HUCenterFollowDto isConcern(@PathVariable("memberId") Long memberId, @PathVariable("toMemberId") Long toMemberId);
//
//    @RequestMapping(value = "/ucenter/concern/concernUsersFeginApi.do/{memberId}", method = RequestMethod.POST)
//    String concernUser(@PathVariable("memberId") Long memberId);
//    
//    @RequestMapping(value = "/ucenter/concern/concernUsersCountFeginApi.do/{memberId}", method = RequestMethod.POST)
//    Integer concernUserCount(@PathVariable("memberId") Long memberId);
//  
//    
////    @RequestMapping(value = "/ucenter/message/save.do", method = RequestMethod.POST)
////    UCenterMessage messageSave(@RequestBody HUCenterMessageDto ucenterMessageDto);
//    
//    @RequestMapping(value = "/ucenter/message/saveMsgFeginApi.do", method = RequestMethod.POST)
//    UserMessageDto messageSave(@RequestBody UserMessageDto userMessageDto);
//    
//    
////    @RequestMapping(value = "deleteBySubjectIdFeginApi.do", method = {RequestMethod.POST})
////    R deleteBySubjectIdFeginApi(@RequestParam("subjectId")Long subjectId);
//    
////    @RequestMapping(value = "deleteByResourceIdFeginApi.do", method = {RequestMethod.POST})
////    R deleteByResourceIdFeginApi(@RequestParam("resourceId")Long resourceId);
//    
////    @RequestMapping(value = "deleteByCommonIdFeginApi.do", method = {RequestMethod.POST})
////    R deleteByCommonIdFeginApi(@RequestParam("commonId")Long commonId) ;
//    
//    
//}
