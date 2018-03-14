package com.hww.app.admin.service;

import java.util.List;

import com.hww.app.common.dto.GeoProvinceDto;
import com.hww.app.common.entity.AppGeoProvince;

public interface GeoProvinceService{
	public void saveProvince(GeoProvinceDto vo);
	public void updateProvince(GeoProvinceDto vo);
	public void delProvince(AppGeoProvince vo);
	
	public AppGeoProvince findProviceById(Long proviceId);
	
	public List<AppGeoProvince> findByCountry(Long parentId);
}
