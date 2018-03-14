package com.hww.app.common.manager;

import com.hww.app.common.dao.CityDao;
import com.hww.app.common.dto.GeoCityDto;
import com.hww.app.common.entity.AppGeoCity;
import com.hww.base.common.manager.IBaseEntityMng;

import java.util.List;

public interface GeoCityMng extends IBaseEntityMng<Long, AppGeoCity, CityDao> {

    List<AppGeoCity> cityList(GeoCityDto geoCityDto);
    
    Integer loadCountByProvince(Long provinceId);
}
