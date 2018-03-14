package com.hww.app.common.manager.impl;

import com.hww.app.common.dao.AppMemberNearbyDao;
import com.hww.app.common.entity.AppMemberNearby;
import com.hww.app.common.manager.AppMemberNearbyMng;
import com.hww.app.common.vo.AppMemberNearbyVo;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AppMemberNearbyMngImpl extends BaseEntityMngImpl<Long, AppMemberNearby, AppMemberNearbyDao>
        implements AppMemberNearbyMng {

    private AppMemberNearbyDao nearbyDao;

    @Autowired
    public void setNearbyDao(AppMemberNearbyDao nearbyDao) {
        super.setEntityDao(nearbyDao);
        this.nearbyDao = nearbyDao;
    }

    @Override
    public AppMemberNearbyVo queryMember(Long memberId) {
        return nearbyDao.findMember(memberId);
    }
}
