package com.hww.app.common.dao.impl;

import com.hww.app.common.dao.AppMemberNearbyQueryDao;
import com.hww.app.common.entity.AppMemberNearbyQuery;
import com.hww.app.common.vo.AppMemberNearbyVo;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

@Repository
public class AppMemberNearbyQueryDaoImpl extends LocalEntityDaoImpl<Long, AppMemberNearbyQuery>
        implements AppMemberNearbyQueryDao {

    @Override
    public List<AppMemberNearbyVo> nearPeoples(AppMemberNearbyVo vo) {

      /*  Finder f = Finder.create("select " +
                " member_id as memberId," +
                " nick_name as nickName," +
                " avatar as avatar," +
                " sex as sex, " +
                " country_name as countryName," +
                " province_name as provinceName," +
                " city_name as cityName,"
                + "address as address," +
                " longitude,latitude, locate_time as locateTime," +
//                + "(round(6367000 * 2 * asin(sqrt(pow(sin(((latitude * pi()) / 180 - (:ulat * pi()) / 180) / 2), 2) + cos((:ulat * pi()) / 180) * cos((latitude * pi()) / 180) * pow(sin(((longitude * pi()) / 180 - (:ulon * pi()) / 180) / 2), 2))))) "
                " st_distance(point(longitude,latitude), point(:longitude,:latitude)) * 111195 " +
                " AS distance from app_member_nearby_query " +
                " where 1=1 " 
//                "  abs(longitude - :longitude) < 10 and abs(latitude - :latitude) < 10 "
                );*/
    	
    	Finder f=Finder.create("select * from (select "+
    	         "member_id as memberId,"+
    			"nick_name as nickName,"+
    	         "avatar as avatar,"+
    			"sex as sex,"+
    	         "country_name as countryName,"+
    			"province_name as provinceName,"+
    	         "city_name as cityName,"+
    			"address as address,"+
    			" longitude,latitude, locate_time as locateTime," +
              " st_distance(point(longitude,latitude), point(:longitude,:latitude)) * 111195 " +
              " AS distancex from app_member_nearby_query " +
              " where 1=1 " 
    			+") sass  where sass.distancex<1000000");
    	
    	
                if(vo.getMemberId()!=null) {
                	f.append(" and sass.memberId != :memberId");
                	f.setParam("memberId", vo.getMemberId());
                }
        // f.append(" group by member_id  ");
        if (vo.getRandom() != null && vo.getRandom()) {
            f.append(" order by rand() ");
        } else {
            f.append(" order by distancex asc");
        }
        f.append(" limit :pageStart,:pageSize");
        f.setParam("latitude", vo.getLatitude());
        f.setParam("longitude", vo.getLongitude());
        f.setParam("pageStart", (vo.getPageNo()==null?1:vo.getPageNo() - 1) * (vo.getPageSize()==null?10: vo.getPageSize()));
        f.setParam("pageSize", vo.getPageSize()==null?10: vo.getPageSize());
       List<AppMemberNearbyVo> data= (List<AppMemberNearbyVo>) findJoin(f, AppMemberNearbyVo.class);
        return data;
    }

    @Override
    public AppMemberNearbyVo findMember(Long memberId) {
        try {
            Finder f = Finder.create("from AppMemberNearbyQuery " +
                    " where memberId = :memberId");

            f.setParam("memberId", memberId);
            List<AppMemberNearbyQuery> vos = (List<AppMemberNearbyQuery>) find(f);
            if (vos == null) {
                return null;
            }
            if (vos.size() == 1) {
                AppMemberNearbyVo vo = new AppMemberNearbyVo();
                BeanUtils.copyProperties(vo, vos.get(0));
                return vo;
            } else if (vos.size() > 1) {
                for (AppMemberNearbyQuery vo : vos) {
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
