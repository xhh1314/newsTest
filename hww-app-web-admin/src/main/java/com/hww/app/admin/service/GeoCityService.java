package com.hww.app.admin.service;

import java.util.List;

import com.hww.app.common.dto.GeoCityDto;
import com.hww.app.common.entity.AppGeoCity;


public interface GeoCityService{
	public void saveGeoCity(GeoCityDto vo);
	public void updateGeoCity(GeoCityDto vo);
	public void delGeoCity(AppGeoCity vo);
	
	public AppGeoCity findGeoCityById(Long cityId);
	
	public List<AppGeoCity> findByProvice(Long parentId);
}
