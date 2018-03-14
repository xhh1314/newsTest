package com.hww.app.admin.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hww.app.admin.service.GeoCountryService;
import com.hww.app.common.entity.AppGeoCountry;
import com.hww.app.common.manager.GeoCountryMng;
import com.hww.app.common.vo.AppGeoCountryVo;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.mysql.fabric.xmlrpc.base.Array;

@Service("geoCountryService")
@Transactional
public class GeoCountryServiceImpl implements GeoCountryService {

	@Autowired
	private GeoCountryMng geoCountryMng;
	
	

	@Override
	public List<AppGeoCountryVo> CountryList() {
		List<AppGeoCountry> entityist = geoCountryMng.countryList();
		List<AppGeoCountryVo> voList = new ArrayList<AppGeoCountryVo>();
		for(AppGeoCountry entity: entityist){
			AppGeoCountryVo vo = BeanMapper.map(entity, AppGeoCountryVo.class);
			voList.add(vo);
		}
		return voList;
	}
	@Override
	public List<AppGeoCountryVo> CountryListFindByName(AppGeoCountryVo from) {
		List<AppGeoCountry> entityist = geoCountryMng.CountryListFindByName(from);
		List<AppGeoCountryVo> voList = new ArrayList<AppGeoCountryVo>();
		if(entityist!=null){
			for(AppGeoCountry entity: entityist){
				AppGeoCountryVo vo = BeanMapper.map(entity, AppGeoCountryVo.class);
				voList.add(vo);
			}
		}
		return voList;
	}
	@Override
	public Pagination PcountryList(AppGeoCountryVo vo) {
		Finder hql = Finder.create("from AppGeoCountry");
		Pagination p = geoCountryMng.find(hql, vo.getPageNo(), 10);
		return p;
		
	}
	@Override
	public AppGeoCountry findById(AppGeoCountryVo vo) {
		return geoCountryMng.find(vo.getCountryId());
	}
	@Override
	public void saveCountry(String countryName,String longitude, String latitude, String radius) {
		AppGeoCountry entity=new AppGeoCountry();
		entity.setSiteId(1);
		entity.setCountryName(countryName);
		entity.setLongitude(longitude);
		entity.setLatitude(latitude);
		entity.setRadius(radius);
		entity.setStatus(Short.valueOf("1"));
		entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
		entity.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
		geoCountryMng.save(entity);
	}
	
//	@Override
//	public void saveLongitude(String longitude) {
//		AppGeoCountry entity=new AppGeoCountry();
//		entity.setLongitude(longitude);
//		geoCountryMng.save(entity);
//	}
//	
//	@Override
//	public void saveLatitude(String latitude) {
//		AppGeoCountry entity=new AppGeoCountry();
//		entity.setLongitude(latitude);
//		geoCountryMng.save(entity);
//	}
//	
//	@Override
//	public void saveRadius(String radius) {
//		AppGeoCountry entity=new AppGeoCountry();
//		entity.setLongitude(radius);
//		geoCountryMng.save(entity);
//	}
	
	@Override
	public void updateCountry(AppGeoCountryVo vo) {
		AppGeoCountry appGeoCountry= geoCountryMng.find(vo.getCountryId());
		if(vo.getCountryName()!=null){
			appGeoCountry.setCountryName(vo.getCountryName());
		}
		if(vo.getLatitude()!=null){
			appGeoCountry.setLatitude(vo.getLatitude());
		}
		if(vo.getLongitude()!=null){
			appGeoCountry.setLongitude(vo.getLongitude());
		}
		if(vo.getRadius()!=null) {
			appGeoCountry.setRadius(vo.getRadius());
		}
		appGeoCountry.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
		geoCountryMng.update(appGeoCountry);
		
	}
	@Override
	public void delCountry(AppGeoCountry vo) {
		geoCountryMng.delete(vo);
		
	}
}
