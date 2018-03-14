package com.hww.app.webservice.service;

import com.hww.app.common.dto.AppBehaviorDto;
import com.hww.app.common.vo.AppMemberBehaviorVo;

import java.util.List;


public interface AppMemberBehaviorService {
	
	AppMemberBehaviorVo loadUserBehaviorForContentByType(Long memberId, Long contentId, Integer bevType,Integer bevValue,Integer plateType);
	
	List<Long> loadContentIdsByUserAndBevType(Long memberId, Integer bevType,Integer plateType);
	

    void createBehavior(AppBehaviorDto behaviorDto);
}
