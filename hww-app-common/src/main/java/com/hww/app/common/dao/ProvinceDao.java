package com.hww.app.common.dao;

import com.hww.app.common.dto.GeoProvinceDto;
import com.hww.app.common.entity.AppGeoProvince;
import com.hww.base.common.dao.IBaseEntityDao;

import java.util.List;

public interface ProvinceDao extends IBaseEntityDao<Long, AppGeoProvince> {

    List<AppGeoProvince> provinceList(GeoProvinceDto geoProvinceDto);
}
