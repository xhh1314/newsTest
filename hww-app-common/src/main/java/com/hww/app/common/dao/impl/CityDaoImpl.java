package com.hww.app.common.dao.impl;

import com.hww.app.common.dao.CityDao;
import com.hww.app.common.dto.GeoCityDto;
import com.hww.app.common.entity.AppGeoCity;
import com.hww.base.common.util.Finder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("cityDao")
public class CityDaoImpl extends LocalEntityDaoImpl<Long, AppGeoCity> implements CityDao {

    @Override
    public List<AppGeoCity> cityList(GeoCityDto geoCityDto) {
        Finder f = Finder.create("from AppGeoCity bean");
        f.append(" where bean.provinceId=:provinceId");
        if(geoCityDto.getProvinceId()!=null){
        	 f.setParam("provinceId", geoCityDto.getProvinceId());
        }else{
        	 f.setParam("provinceId", -1L);
        }
        if(geoCityDto.getCityName()!=null){
        	f.append(" and cityName like :cityName").setParam("cityName","%" + geoCityDto.getCityName() + "%");
        }
        if(geoCityDto.getCityNameEn()!=null){
        	f.append(" and cityNameEn like :cityNameEn").setParam("cityNameEn","%" + geoCityDto.getCityNameEn() + "%");
        }
        List<AppGeoCity> geoCities = (List<AppGeoCity>) find(f);
        if (geoCities != null && geoCities.size() > 0) {
            return geoCities;
        }
        return null;
    }

}
