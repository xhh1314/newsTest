package com.hww.app.webservice.service;

import java.util.Map;

import com.hww.app.common.dto.AppBehaviorCountDto;


public interface AppMemberBehaviorCountService {
	
	
	Map<String,Integer> behaviorCount(Long contentId,Integer plateType);

	void addBehaviorCount(Long contentId, Integer bevType,Integer plateType, int count);
}
