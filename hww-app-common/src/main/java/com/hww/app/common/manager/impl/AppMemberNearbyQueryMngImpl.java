package com.hww.app.common.manager.impl;

import com.hww.app.common.dao.AppMemberNearbyQueryDao;
import com.hww.app.common.entity.AppMemberNearbyQuery;
import com.hww.app.common.manager.AppMemberNearbyQueryMng;
import com.hww.app.common.vo.AppMemberNearbyVo;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AppMemberNearbyQueryMngImpl extends BaseEntityMngImpl<Long, AppMemberNearbyQuery, AppMemberNearbyQueryDao>
        implements AppMemberNearbyQueryMng {

    private AppMemberNearbyQueryDao queryDao;

    @Autowired
    public void setQueryDao(AppMemberNearbyQueryDao queryDao) {
        super.setEntityDao(queryDao);
        this.queryDao = queryDao;
    }

    @Override
	public List<AppMemberNearbyVo> nearPeoples(AppMemberNearbyVo vo) {
        return queryDao.nearPeoples(vo);
    }

    @Override
    public AppMemberNearbyVo queryMember(Long memberId) {
        return queryDao.findMember(memberId);
    }

}