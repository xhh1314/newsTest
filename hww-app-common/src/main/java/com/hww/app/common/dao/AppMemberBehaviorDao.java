package com.hww.app.common.dao;

import com.hww.app.common.entity.AppMemberBehavior;
import com.hww.base.common.dao.IBaseEntityDao;

import java.util.List;

public interface AppMemberBehaviorDao extends IBaseEntityDao<Long, AppMemberBehavior> {


    List<Long> listMemberIdsOfContentLiked(Long ContentId,Integer plateType, Integer behaviorType);

    Integer getUserBehaviorCountValue(Long contentId, Integer behaviorType, Integer plateType, Long memberId);
}
