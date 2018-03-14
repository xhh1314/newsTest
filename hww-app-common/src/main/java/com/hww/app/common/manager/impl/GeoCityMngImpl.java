package com.hww.app.common.manager.impl;

import com.hww.app.common.dao.CityDao;
import com.hww.app.common.dto.GeoCityDto;
import com.hww.app.common.entity.AppGeoCity;
import com.hww.app.common.manager.GeoCityMng;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.util.Finder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("geoCityMng")
@Transactional
public class GeoCityMngImpl extends BaseEntityMngImpl<Long, AppGeoCity, CityDao> implements GeoCityMng {

    @Autowired
    private CityDao cityDao;
    
    public CityDao getCityDao() {
		return cityDao;
	}
    
    @Autowired
	public void setCityDao(CityDao cityDao) {
    	super.setEntityDao(cityDao);
		this.cityDao = cityDao;
	}



	@Override
    public List<AppGeoCity> cityList(GeoCityDto geoCityDto) {
        return cityDao.cityList(geoCityDto);
    }

	@Override
	public Integer loadCountByProvince(Long provinceId) {
		  Finder f = Finder.create("SELECT count(1) as cu");
	        f.append(" FROM app_geo_city ");
	        f.append(" where province_id = :provinceId");
	        f.setParam("provinceId", provinceId);

	        List<Map<String, Object>> list = (List<Map<String, Object>>) cityDao.findJoin(f, Map.class);
	        if(list==null) {
	        	return 0;
	        }
	        return Integer.parseInt(list.get(0).get("cu").toString());
	}

}
