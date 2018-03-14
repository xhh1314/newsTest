package com.hww.app.common.dao;

import com.hww.app.common.entity.AppGeoCountry;
import com.hww.app.common.vo.AppGeoCountryVo;
import com.hww.base.common.dao.IBaseEntityDao;

import java.util.List;


public interface CountryDao extends IBaseEntityDao<Long, AppGeoCountry> {


	List<AppGeoCountry> CountryList();

	List<AppGeoCountry> CountryListFindByName(AppGeoCountryVo vo);

}
