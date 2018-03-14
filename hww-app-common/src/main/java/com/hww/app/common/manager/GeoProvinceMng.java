package com.hww.app.common.manager;

import com.hww.app.common.dao.ProvinceDao;
import com.hww.app.common.dto.GeoProvinceDto;
import com.hww.app.common.entity.AppGeoProvince;
import com.hww.base.common.manager.IBaseEntityMng;

import java.util.List;

public interface GeoProvinceMng extends IBaseEntityMng<Long, AppGeoProvince, ProvinceDao> {

    List<AppGeoProvince> provinceList(GeoProvinceDto geoProvinceDto);
    
    Integer loadCountByContryId(Long contryId);
}
