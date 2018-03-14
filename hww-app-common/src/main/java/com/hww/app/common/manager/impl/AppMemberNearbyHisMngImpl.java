package com.hww.app.common.manager.impl;

import com.hww.app.common.dao.AppMemberNearbyHisDao;
import com.hww.app.common.entity.AppMemberNearbyHis;
import com.hww.app.common.manager.AppMemberNearbyHisMng;
import com.hww.app.common.vo.AppMemberNearbyVo;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

@Service
@Transactional
public class AppMemberNearbyHisMngImpl extends BaseEntityMngImpl<Long, AppMemberNearbyHis, AppMemberNearbyHisDao>
        implements AppMemberNearbyHisMng {

    private AppMemberNearbyHisDao nearbyDao;

    @Autowired
    public void setNearbyDao(AppMemberNearbyHisDao nearbyDao) {
        super.setEntityDao(nearbyDao);
        this.nearbyDao = nearbyDao;
    }

    @Override
    public List<AppMemberNearbyVo>  findMemberNearbyHis(Long memberId,int limitx) {
        return nearbyDao.findMemberNearbyHis(memberId,limitx);
    }
}
