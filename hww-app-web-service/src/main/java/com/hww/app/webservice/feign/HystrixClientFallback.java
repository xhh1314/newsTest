//package com.hww.app.webservice.feign;
//
//import com.hww.ucenter.common.dto.HUCenterFollowDto;
//import com.hww.ucenter.common.dto.UCenterMemberDto;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class HystrixClientFallback implements UserFeignClient {
//
//    @Override
//    public UCenterMemberDto findById(Long id) {
//        UCenterMemberDto user = new UCenterMemberDto();
//        user.setMemberId(id);
//        return user;
//    }
//
////    @Override
////    public List<UCenterMemberVo> searchMembers(AppSearchDto search) {
////
////        return new ArrayList<>();
////    }
//
//    @Override
//    public HUCenterFollowDto isConcern(Long memberId, Long toMemberId) {
//        HUCenterFollowDto dto = new HUCenterFollowDto();
//        dto.setConcernType(0);
//        return dto;
//    }
//}
