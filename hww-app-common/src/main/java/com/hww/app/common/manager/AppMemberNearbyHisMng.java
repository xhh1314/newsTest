package com.hww.app.common.manager;

import java.util.List;

import com.hww.app.common.dao.AppMemberNearbyHisDao;
import com.hww.app.common.entity.AppMemberNearbyHis;
import com.hww.app.common.vo.AppMemberNearbyVo;
import com.hww.base.common.manager.IBaseEntityMng;

public interface AppMemberNearbyHisMng extends IBaseEntityMng<Long, AppMemberNearbyHis, AppMemberNearbyHisDao> {


	List<AppMemberNearbyVo>  findMemberNearbyHis(Long memberId,int limitx);
}
