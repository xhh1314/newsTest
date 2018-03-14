package com.hww.app.common.manager;

import com.hww.app.common.dao.AppMemberBehaviorCountDao;
import com.hww.app.common.dto.AppBehaviorCountDto;
import com.hww.app.common.entity.AppMemberBehaviorCount;
import com.hww.base.common.manager.IBaseEntityMng;

import java.util.List;

public interface AppMemberBehaviorCountMng extends IBaseEntityMng<Long, AppMemberBehaviorCount, AppMemberBehaviorCountDao> {

//	AppBehaviorCountDto loadByContentIdAndBevType(Long contentId,Integer bevType,Integer plateType);
	
	List<AppBehaviorCountDto> loadByContentId(Long contentId,Integer plateType);
	
//	List<AppBehaviorCountDto> loadByContentIdsAndBevType(List<Long> contentIds,Integer bevType,Integer plateType);

	
	void addBehaviorCount(Long contentId,Integer bevType,Integer plateType,int count);
	
	
	
	
}
