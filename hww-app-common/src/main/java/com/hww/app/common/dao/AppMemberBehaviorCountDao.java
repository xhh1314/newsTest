package com.hww.app.common.dao;

import java.util.List;

import com.hww.app.common.dto.AppRecommConfigDto;
import com.hww.app.common.entity.AppMemberBehaviorCount;
import com.hww.base.common.dao.IBaseEntityDao;

public interface AppMemberBehaviorCountDao extends IBaseEntityDao<Long, AppMemberBehaviorCount>{


	AppMemberBehaviorCount loadByContentIdAndBevType(Long contentId, Integer bevType,Integer plateType);

	List<AppMemberBehaviorCount> loadByContentId(Long contentId,Integer plateType);
	
//	List<AppMemberBehaviorCount> loadByContentIdsAndBevType(List<Long> contentIds, Integer bevType,Integer plateType);
	

	
}
