package com.hww.ucenter.common.dao.impl;

import com.hww.base.common.util.Finder;
import com.hww.ucenter.common.dao.SignDao;
import com.hww.ucenter.common.dto.MySignDto;
import com.hww.ucenter.common.dto.MySignListByStreetDTO;
import com.hww.ucenter.common.dto.SignListCheckDTO;
import com.hww.ucenter.common.entity.UCenterSign;
import com.hww.ucenter.common.vo.MySignListByStreetVO;
import com.hww.ucenter.common.vo.SignCount;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.StringBufferInputStream;
import java.util.List;
import java.util.Map;

@Repository("signDao")
public class SignDaoImpl extends LocalEntityDaoImpl<Long, UCenterSign> implements SignDao {

    @Override
    public List<UCenterSign> signList(SignListCheckDTO dto) {
        Finder f = Finder.create("from Sign bean");

        f.append(" where bean.memberId = :memberId");
//        f.append(" and bean.country = :country");
//        f.append(" and bean.province = :province");
//        f.append(" and bean.city = :city");

        f.setParam("memberId", dto.getMemberId());
//        f.setParam("country", dto.getCountry());
//        f.setParam("province", dto.getProvince());
//        f.setParam("city", dto.getCity());

        List<UCenterSign> UCenterSigns = (List<UCenterSign>) find(f);
        return UCenterSigns;
    }

    @Override
    public List<MySignListByStreetVO> mySignListByStreet(MySignListByStreetDTO dto) {
        Finder f = Finder.create("SELECT :memberId, street, COUNT(sign_id) AS signNum");
        f.append(" FROM ucenter_sign");
        f.append(" WHERE member_id = :memberId");
        f.append(" ORDER BY create_time desc");
//        f.append(" AND DATE_FORMAT(create_time, '%Y-%m-%d') >= :oneMonthDate");
//        f.append(" AND DATE_FORMAT(create_time, '%Y-%m-%d') <= :currentDate");
        f.append(" GROUP BY street");

        f.setParam("memberId", dto.getMemberId());
//        f.setParam("oneMonthDate", dto.getOneMonthDate());
//        f.setParam("currentDate", dto.getCurrentDate());

        return (List<MySignListByStreetVO>) findJoin(f, MySignListByStreetVO.class);
    }

    @Override
    public String findSignLastCreateTime(MySignListByStreetVO item) {
        Finder f = Finder.create("SELECT DATE_FORMAT(create_time, '%m-%d %H:%i') createTime");
        f.append(" FROM ucenter_sign");
        f.append(" where street = :street");
        f.append(" and member_id = :memberId");
        f.append(" ORDER BY sign_id DESC");
        f.append(" limit 1");

        f.setParam("street", item.getStreet());
        f.setParam("memberId", item.getMemberId());

        List<Map<String, Object>> list = (List<Map<String, Object>>) findJoin(f, Map.class);


        return list.get(0).get("createTime").toString();
    }

    @Override
    public UCenterSign lastSign(UCenterSign UCenterSign) {

        Finder f = Finder.create("SELECT address FROM ucenter_sign where member_id = :memberId");
        f.append(" ORDER BY sign_id DESC");
        f.append(" limit 1");
        f.setParam("memberId", UCenterSign.getMemberId());

        List<UCenterSign> UCenterSigns = (List<UCenterSign>) findJoin(f, UCenterSign.class);
        System.out.println(UCenterSigns);
        if (UCenterSigns != null) {
            return UCenterSigns.get(0);
        }
        return null;
    }

    @Override
    public Integer signCount(UCenterSign UCenterSign) {

        Finder f = Finder.create("SELECT count(1) as cu FROM ucenter_sign where address = :address");
        f.append(" ORDER BY sign_id DESC");
        f.setParam("address", UCenterSign.getAddress());
        List<SignCount> signs = (List<SignCount>) findJoin(f, SignCount.class);
        if (signs != null && signs.size() > 0) {
            return signs.get(0).getCu();
        }
        return null;
    }

    @Override
    public List<UCenterSign> mySignList(MySignDto sign) {

        Finder f = Finder.create("SELECT sign_id as signId,address,latitude,longitude,create_time as createTime");
        f.append(" FROM ucenter_sign");
        f.append(" where member_id = :memberId");
        f.append(" AND DATE_FORMAT(create_time, '%Y-%m-%d') >= :oneMonthDate");
        f.append(" AND DATE_FORMAT(create_time, '%Y-%m-%d') <= :currentDate");
        f.append(" order by create_time desc");
        f.setParam("memberId", sign.getMemberId());
        f.setParam("oneMonthDate", sign.getOneMonthDate());
        f.setParam("currentDate", sign.getCurrentDate());
        return (List<UCenterSign>) findJoin(f, UCenterSign.class);
    }

    @Override
    public List<UCenterSign> mySign(MySignDto dto) {

        Finder f = Finder.create("SELECT sign_id as signId,address,latitude,longitude,create_time as createTime");
        f.append(" FROM ucenter_sign");
        f.append(" where member_id = :memberId");
        f.append(" AND DATE_FORMAT(create_time, '%Y-%m-%d') >= :oneMonthDate");
        f.append(" AND DATE_FORMAT(create_time, '%Y-%m-%d') <= :currentDate");
        f.append(" order by create_time desc");
        f.append(" limit :pageStart,:pageSize");
        f.setParam("memberId", dto.getMemberId());
        f.setParam("oneMonthDate", dto.getOneMonthDate());
        f.setParam("currentDate", dto.getCurrentDate());
        f.setParam("pageStart", 0);
        f.setParam("pageSize", 4);
        return (List<UCenterSign>) findJoin(f, UCenterSign.class);
    }

    @Override
    public Integer cuOneSignCount(MySignDto dto) {
        Finder f = Finder.create("SELECT count(1) as cu FROM ucenter_sign");
        f.append(" where member_id = :memberId");
        f.append(" AND DATE_FORMAT(create_time, '%Y-%m-%d') >= :oneMonthDate");
        f.append(" AND DATE_FORMAT(create_time, '%Y-%m-%d') <= :currentDate");
        f.append(" order by create_time desc");
        f.setParam("memberId", dto.getMemberId());
        f.setParam("oneMonthDate", dto.getOneMonthDate());
        f.setParam("currentDate", dto.getCurrentDate());
        List<SignCount> signs = (List<SignCount>) findJoin(f, SignCount.class);
        if (signs != null && signs.size() > 0) {
            return signs.get(0).getCu();
        }
        return null;
    }

    @Override
    public boolean isTodaySigned(MySignDto mySignDto) {
        Finder f = Finder.create("SELECT count(1) FROM UCenterSign");
        f.append(" where member_id = :memberId");
        f.setParam("memberId", mySignDto.getMemberId());
        List<UCenterSign> UCenterSigns = (List<UCenterSign>) find(f);
        return UCenterSigns.size() > 0;
    }
    
    @Override
    public List<Map> selectInMinits(Long memberId,int minutes,String address,Double lon,Double lat) {
        Finder f = Finder.create("select * from ucenter_sign");
        f.append(" where member_id = :memberId");
        f.append(" and  create_time >= CURRENT_TIMESTAMP - INTERVAL :minutes MINUTE ");
        
        if(StringUtils.hasText(address)) {
        	f.append(" and  address=:address ");
        	 f.setParam("address", address);
        }
        
        if(lon!=null&&lat!=null) {
        	f.append(" and  latitude=:latitude and longitude=:longitude ");
        	 f.setParam("lon", lon);
        	 f.setParam("lat", lat);
        }
        f.setParam("memberId", memberId);
        f.setParam("minutes", minutes);
        List<Map> list=(List<Map>) findJoin(f,Map.class);
        return list;
    }
    
    

}
