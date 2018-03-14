package com.hww.app.common.dao;

import com.hww.app.common.entity.AppMemberNearby;
import com.hww.app.common.vo.AppMemberNearbyVo;
import com.hww.base.common.dao.IBaseEntityDao;

public interface AppMemberNearbyDao extends IBaseEntityDao<Long, AppMemberNearby> {

    AppMemberNearbyVo findMember(Long memberId);
}
