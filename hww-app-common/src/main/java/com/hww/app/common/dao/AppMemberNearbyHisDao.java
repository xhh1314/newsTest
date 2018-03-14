package com.hww.app.common.dao;

import java.util.List;

import com.hww.app.common.entity.AppMemberNearbyHis;
import com.hww.app.common.vo.AppMemberNearbyVo;
import com.hww.base.common.dao.IBaseEntityDao;

public interface AppMemberNearbyHisDao extends IBaseEntityDao<Long, AppMemberNearbyHis> {

	List<AppMemberNearbyVo>  findMemberNearbyHis(Long memberId, int limitx);
}
