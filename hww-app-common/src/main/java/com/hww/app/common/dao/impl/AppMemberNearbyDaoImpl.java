package com.hww.app.common.dao.impl;

import com.hww.app.common.dao.AppMemberNearbyDao;
import com.hww.app.common.entity.AppMemberNearby;
import com.hww.app.common.vo.AppMemberNearbyVo;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppMemberNearbyDaoImpl extends LocalEntityDaoImpl<Long, AppMemberNearby> implements AppMemberNearbyDao {

    @Override
    public AppMemberNearbyVo findMember(Long memberId) {
        try {
            Finder f = Finder.create("from AppMemberNearby " +
                    " where memberId = :memberId");
            f.setParam("memberId", memberId);
            List<AppMemberNearby> vos = (List<AppMemberNearby>) find(f);
            if (vos == null) {
                return null;
            }
            if (vos.size() == 1) {
                AppMemberNearbyVo vo = new AppMemberNearbyVo();
                BeanUtils.copyProperties(vo, vos.get(0));
                return vo;
            } else if (vos.size() > 1) {
                for (AppMemberNearby vo : vos) {
                    delete(vo.getNearbyId());
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
