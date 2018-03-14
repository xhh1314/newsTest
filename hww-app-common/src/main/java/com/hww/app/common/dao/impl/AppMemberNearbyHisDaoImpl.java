package com.hww.app.common.dao.impl;

import com.google.common.collect.Lists;
import com.hww.app.common.dao.AppMemberNearbyDao;
import com.hww.app.common.dao.AppMemberNearbyHisDao;
import com.hww.app.common.entity.AppMemberNearby;
import com.hww.app.common.entity.AppMemberNearbyHis;
import com.hww.app.common.vo.AppMemberNearbyVo;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppMemberNearbyHisDaoImpl extends LocalEntityDaoImpl<Long, AppMemberNearbyHis> implements AppMemberNearbyHisDao {

    @Override
    public List<AppMemberNearbyVo>  findMemberNearbyHis(Long memberId, int limitx) {
       
            Finder f = Finder.create("from AppMemberNearbyHis " +
                    " where memberId = :memberId order by createTime desc  limit:limitx ");
            f.setParam("memberId", memberId);
            f.setParam("limitx", limitx);
            List<AppMemberNearbyHis> voList = (List<AppMemberNearbyHis>) find(f);
            
            if (voList == null||voList.isEmpty()) {
                return Lists.newArrayList();
            }
            List<AppMemberNearbyVo> resList=Lists.newArrayList();
            for(AppMemberNearbyHis his: voList) {
            	AppMemberNearbyVo vo = new AppMemberNearbyVo();
                try {
					BeanUtils.copyProperties(vo, his);
				} catch (Exception e) {
				}
                resList.add(vo);
            }
            return resList;
    }
}
