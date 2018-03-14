package com.hww.app.common.dao;

import com.hww.app.common.dto.GeoCityDto;
import com.hww.app.common.entity.AppGeoCity;
import com.hww.base.common.dao.IBaseEntityDao;

import java.util.List;

public interface CityDao extends IBaseEntityDao<Long, AppGeoCity> {

    List<AppGeoCity> cityList(GeoCityDto geoCityDto);
}
