package com.hww.app.common.manager.impl;

import com.hww.app.common.dao.ProvinceDao;
import com.hww.app.common.dto.GeoProvinceDto;
import com.hww.app.common.entity.AppGeoProvince;
import com.hww.app.common.manager.GeoProvinceMng;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.util.Finder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("geoProvinceMng")
@Transactional
public class GeoProvinceMngImpl extends BaseEntityMngImpl<Long, AppGeoProvince, ProvinceDao> implements GeoProvinceMng {

   
    private ProvinceDao provinceDao;
    
    public ProvinceDao getProvinceDao() {
		return provinceDao;
	}

    @Autowired
	public void setProvinceDao(ProvinceDao provinceDao) {
    	super.setEntityDao(provinceDao);
		this.provinceDao = provinceDao;
	}


	@Override
    public List<AppGeoProvince> provinceList(GeoProvinceDto geoProvinceDto) {
        return provinceDao.provinceList(geoProvinceDto);
    }

	
	@Override
	public Integer loadCountByContryId(Long contryId) {
		  Finder f = Finder.create("SELECT count(1) as cu");
	        f.append(" FROM app_geo_province ");
	        f.append(" where country_id = :contryId");
	        f.setParam("contryId", contryId);

	        List<Map<String, Object>> list = (List<Map<String, Object>>) provinceDao.findJoin(f, Map.class);
	        if(list==null) {
	        	return 0;
	        }
	        return Integer.parseInt(list.get(0).get("cu").toString());
	}
}
