package com.hww.app.common.manager;

import com.hww.app.common.dao.AppMemberBehaviorDao;
import com.hww.app.common.entity.AppMemberBehavior;
import com.hww.app.common.vo.AppMemberBehaviorVo;
import com.hww.base.common.manager.IBaseEntityMng;

import java.util.List;

public interface AppMemberBehaviorMng extends IBaseEntityMng<Long, AppMemberBehavior, AppMemberBehaviorDao> {
	
	
	AppMemberBehaviorVo loadUserBehaviorForContentByType(Long memberId, Long contentId, Integer bevType,Integer bevValue,Integer plateType);
	
	List<AppMemberBehaviorVo> loadContentIdsByUserAndBevType(Long memberId, Integer bevType,Integer plateType);


	void updateBevValue(Long behaviorId, Integer bevValue);

    boolean getIsUserInLikedCollection(Long contentId, Integer plateType,Long memberId);
}
