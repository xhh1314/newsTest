package com.hww.app.admin.service;

import java.util.List;

import com.hww.app.common.entity.AppGeoCountry;
import com.hww.app.common.vo.AppGeoCountryVo;
import com.hww.base.common.page.Pagination;

public interface GeoCountryService{


	List<AppGeoCountryVo> CountryList();

	Pagination PcountryList(AppGeoCountryVo vo);

	List<AppGeoCountryVo> CountryListFindByName(AppGeoCountryVo vo);

	AppGeoCountry findById(AppGeoCountryVo vo);
	
	/**
	 * 添加国家
	 * @param countryName
	 */
	public void saveCountry(String countryName,String longitude, String latitude, String radius);
	
	public void updateCountry(AppGeoCountryVo vo);
	
//	/**
//	 * 添加经度
//	 * @param longitude
//	 */
//	public void saveLongitude(String longitude);
//	
//	/**
//	 * 添加纬度
//	 * @param latitude
//	 */
//	public void saveLatitude(String latutude);
//	
//	/**
//	 * 添加半径
//	 * @param radius
//	 */
//	public void saveRadius(String radius);
	
	public void delCountry(AppGeoCountry vo);

}
