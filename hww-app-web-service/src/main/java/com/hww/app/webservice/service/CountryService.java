package com.hww.app.webservice.service;

import com.hww.app.common.dto.GeoCityDto;
import com.hww.app.common.dto.GeoProvinceDto;
import com.hww.app.common.entity.AppGeoCity;
import com.hww.app.common.entity.AppGeoCountry;
import com.hww.app.common.entity.AppGeoProvince;
import com.hww.els.common.vo.SearchNewsVo;
import com.hww.ucenter.common.vo.UCenterMemberVo;

import java.util.List;

public interface CountryService {

    List<AppGeoCity> cityList(GeoCityDto geoCityDto);

    List<AppGeoCountry> countryList();

    List<AppGeoProvince> provinceList(GeoProvinceDto geoProvinceDto);

    List<UCenterMemberVo> searchMembers(SearchNewsVo vo);
}
