package com.hww.app.common.dao;

import com.hww.app.common.entity.AppMemberNearbyQuery;
import com.hww.app.common.vo.AppMemberNearbyVo;
import com.hww.base.common.dao.IBaseEntityDao;

import java.util.List;

public interface AppMemberNearbyQueryDao extends IBaseEntityDao<Long, AppMemberNearbyQuery> {

    List<AppMemberNearbyVo> nearPeoples(AppMemberNearbyVo vo);

    AppMemberNearbyVo findMember(Long memberId);
}