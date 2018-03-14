package com.hww.app.admin.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hww.app.admin.service.GeoProvinceService;
import com.hww.app.common.dto.GeoProvinceDto;
import com.hww.app.common.entity.AppGeoProvince;
import com.hww.app.common.manager.GeoProvinceMng;
import com.hww.app.common.vo.AppGeoCountryVo;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;

@Service("geoProvinceService")
@Transactional
public class GeoProvinceServiceImpl implements GeoProvinceService {

	@Autowired
	private GeoProvinceMng geoProvinceMng;

	@Override
	public void saveProvince(GeoProvinceDto vo) {
		if(vo!=null){
			AppGeoProvince entity = BeanMapper.map(vo, AppGeoProvince.class);
			entity.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
			entity.setLatitude(Double.parseDouble(vo.getLatitude()));
			entity.setLongitude(Double.parseDouble(vo.getLongitude()));
			entity.setRadius(Integer.parseInt(vo.getRadius()));
			geoProvinceMng.save(entity);
		}
		
		
	}

	@Override
	public void updateProvince(GeoProvinceDto vo) {
			AppGeoProvince entity = geoProvinceMng.find(vo.getProvinceId());
			if(vo.getProvinceName()!=null){
				entity.setProvinceName(vo.getProvinceName());
				entity.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
				entity.setLatitude(Double.parseDouble(vo.getLatitude()));
				entity.setLongitude(Double.parseDouble(vo.getLongitude()));
				entity.setRadius(Integer.parseInt(vo.getRadius()));
			}
			geoProvinceMng.update(entity);
	}

	@Override
	public void delProvince(AppGeoProvince vo) {
		// TODO Auto-generated method stub
		geoProvinceMng.delete(vo);
	}

	@Override
	public AppGeoProvince findProviceById(Long proviceId) {
		return geoProvinceMng.find(proviceId);
		
	}

	@Override
	public List<AppGeoProvince> findByCountry(Long parentId) {
		Finder f=Finder.create("from AppGeoProvince where countryId=:countryId").setParam("countryId",parentId);
		return (List<AppGeoProvince>) geoProvinceMng.find(f);
	}


	
	
}
