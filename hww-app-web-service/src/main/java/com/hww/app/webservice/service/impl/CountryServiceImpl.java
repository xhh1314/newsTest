package com.hww.app.webservice.service.impl;

import com.google.common.collect.Lists;
import com.hww.app.common.dto.GeoCityDto;
import com.hww.app.common.dto.GeoProvinceDto;
import com.hww.app.common.entity.AppGeoCity;
import com.hww.app.common.entity.AppGeoCountry;
import com.hww.app.common.entity.AppGeoProvince;
import com.hww.app.common.manager.GeoCityMng;
import com.hww.app.common.manager.GeoCountryMng;
import com.hww.app.common.manager.GeoProvinceMng;
import com.hww.app.webservice.service.CountryService;
import com.hww.els.common.vo.SearchNewsVo;
import com.hww.ucenter.common.vo.UCenterMemberVo;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service("countryService")
@Transactional
public class CountryServiceImpl implements CountryService {

    @Resource
    private GeoCountryMng geoCountryMng;

    @Resource
    private GeoCityMng cityMng;

    @Resource
    private GeoProvinceMng geoProvinceMng;

    @Override
    public List<UCenterMemberVo> searchMembers(SearchNewsVo vo) {
        return null;
    }
    
    @Scheduled(fixedDelay = 1000*60*10)
	   @CacheEvict(value = "app_countryList",allEntries=true)
	   public void countryList_delete_from_cache() {
	   }
    @Cacheable(value = "app_countryList")
    @Override
    public List<AppGeoCountry> countryList() {
    	 List<AppGeoCountry>  list=geoCountryMng.countryList();
    	 if(list!=null&&!list.isEmpty()) {
    		 for(AppGeoCountry c: list) {
    			 Integer count= geoProvinceMng.loadCountByContryId(c.getCountryId());
    			 c.setIsHaveChild(count==null?0:count);
    		 }
    	 }
        return list==null?Lists.newArrayList():list;
    }
    
    @Scheduled(fixedDelay = 1000*60*10)
	   @CacheEvict(value = "app_provinceList",allEntries=true)
	   public void provinceList_delete_from_cache() {
	   }
   @Cacheable(value = "app_provinceList",key="'provinceList_'+#geoProvinceDto.countryId")
    @Override
    public List<AppGeoProvince> provinceList(GeoProvinceDto geoProvinceDto) {
    	 List<AppGeoProvince>  list = geoProvinceMng.provinceList(geoProvinceDto);
    	 
    	 for(AppGeoProvince c: list) {
			 Integer count= cityMng.loadCountByProvince(c.getProvinceId());
			 c.setIsHaveChild(count==null?0:count);
		 }
    	 
        return list==null?Lists.newArrayList():list;
    }
   
   @Scheduled(fixedDelay = 1000*60*10)
   @CacheEvict(value = "app_cityList",allEntries=true)
   public void cityList_delete_from_cache() {
   }
    @Cacheable(value = "app_cityList",key="'cityList_'+#geoCityDto.provinceId+'_'+#geoCityDto.cityNameEn")
    @Override
    public List<AppGeoCity> cityList(GeoCityDto geoCityDto) {
    	List<AppGeoCity> list=cityMng.cityList(geoCityDto);
        return list==null?Lists.newArrayList():list;
    }
 
    
   

}
