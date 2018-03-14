package com.hww.app.common.dao.impl;

import com.hww.app.common.dao.ProvinceDao;
import com.hww.app.common.dto.GeoProvinceDto;
import com.hww.app.common.entity.AppGeoProvince;
import com.hww.base.common.util.Finder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("provinceDao")
public class ProvinceDaoImpl extends LocalEntityDaoImpl<Long, AppGeoProvince> implements ProvinceDao {

    @Override
    public List<AppGeoProvince> provinceList(GeoProvinceDto geoProvinceDto) {
        Finder f = Finder.create("from AppGeoProvince bean");
        f.append(" where bean.countryId=:countryId");
        if(geoProvinceDto.getCountryId()!=null){
        	f.setParam("countryId", geoProvinceDto.getCountryId());
        }else{
        	f.setParam("countryId", -1L);
        }
        if(geoProvinceDto.getProvinceName()!=null){
        	f.append(" and provinceName like :provinceName").setParam("provinceName","%" + geoProvinceDto.getProvinceName() + "%");
        }
        if(geoProvinceDto.getProvinceNameEn()!=null){
        	f.append(" and provinceNameEn like :provinceNameEn").setParam("provinceNameEn","%" + geoProvinceDto.getProvinceNameEn()+ "%");
        }
        List<AppGeoProvince> provinces = (List<AppGeoProvince>) find(f);
        if (provinces != null && provinces.size() > 0) {
            return provinces;
        }
        return null;
    }

}
