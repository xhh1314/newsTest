package com.hww.app.common.manager;

import com.hww.app.common.dao.CountryDao;
import com.hww.app.common.entity.AppGeoCountry;
import com.hww.app.common.vo.AppGeoCountryVo;
import com.hww.base.common.manager.IBaseEntityMng;

import java.util.List;

public interface GeoCountryMng extends IBaseEntityMng<Long, AppGeoCountry, CountryDao> {

    List<AppGeoCountry> countryList();

	List<AppGeoCountry> CountryListFindByName(AppGeoCountryVo vo);

}
