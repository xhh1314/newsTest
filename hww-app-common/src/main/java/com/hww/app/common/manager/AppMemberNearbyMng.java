package com.hww.app.common.manager;

import com.hww.app.common.dao.AppMemberNearbyDao;
import com.hww.app.common.entity.AppMemberNearby;
import com.hww.app.common.vo.AppMemberNearbyVo;
import com.hww.base.common.manager.IBaseEntityMng;

public interface AppMemberNearbyMng extends IBaseEntityMng<Long, AppMemberNearby, AppMemberNearbyDao> {


    AppMemberNearbyVo queryMember(Long memberId);
}
