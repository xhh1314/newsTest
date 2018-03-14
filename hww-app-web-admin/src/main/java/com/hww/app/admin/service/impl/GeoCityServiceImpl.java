package com.hww.app.admin.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hww.app.admin.service.GeoCityService;
import com.hww.app.common.dto.GeoCityDto;
import com.hww.app.common.entity.AppGeoCity;
import com.hww.app.common.manager.GeoCityMng;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;

@Service("geoCityService")
@Transactional
public class GeoCityServiceImpl implements GeoCityService {

	@Autowired
	private GeoCityMng geoCityMng;

	@Override
	public void saveGeoCity(GeoCityDto dto) {
		if(dto!=null){
			AppGeoCity entity = BeanMapper.map(dto, AppGeoCity.class);
			entity.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
			entity.setLatitude(dto.getLatitude());
			entity.setLongitude(dto.getLongitude());
			entity.setRadius(dto.getRadius());
			geoCityMng.save(entity);
		}
	}

	@Override
	public void updateGeoCity(GeoCityDto dto) {
			AppGeoCity entity = geoCityMng.find(dto.getCityId());
			if(dto.getCityName()!=null){
				entity.setCityName(dto.getCityName());
			}
			entity.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
			entity.setLatitude(dto.getLatitude());
			entity.setLongitude(dto.getLongitude());
			entity.setRadius(dto.getRadius());
			geoCityMng.update(entity);
	}
	public void delGeoCity(AppGeoCity vo) {
		// TODO Auto-generated method stub
		geoCityMng.delete(vo);
	}

	@Override
	public AppGeoCity findGeoCityById(Long cityId) {
		// TODO Auto-generated method stub
		return geoCityMng.find(cityId);
	}

	@Override
	public List<AppGeoCity> findByProvice(Long parentId) {
		Finder f=Finder.create("from AppGeoCity where provinceId=:provinceId").setParam("provinceId",parentId);
		return (List<AppGeoCity>) geoCityMng.find(f);
	}
}
