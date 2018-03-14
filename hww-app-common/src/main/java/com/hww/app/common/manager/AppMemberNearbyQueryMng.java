package com.hww.app.common.manager;

import com.hww.app.common.dao.AppMemberNearbyQueryDao;
import com.hww.app.common.entity.AppMemberNearbyQuery;
import com.hww.app.common.vo.AppMemberNearbyVo;
import com.hww.base.common.manager.IBaseEntityMng;

import java.util.List;

public interface AppMemberNearbyQueryMng extends IBaseEntityMng<Long, AppMemberNearbyQuery, AppMemberNearbyQueryDao> {

    List<AppMemberNearbyVo> nearPeoples(AppMemberNearbyVo vo);

    AppMemberNearbyVo queryMember(Long memberId);

}
