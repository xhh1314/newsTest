package com.hww.app.common.manager.impl;

import com.hww.app.common.dao.CountryDao;
import com.hww.app.common.entity.AppGeoCountry;
import com.hww.app.common.manager.GeoCountryMng;
import com.hww.app.common.vo.AppGeoCountryVo;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("geoCountryMng")
@Transactional
public class GeoCountryMngImpl extends BaseEntityMngImpl<Long, AppGeoCountry, CountryDao> implements GeoCountryMng {

    private CountryDao countryDao;
    

    public CountryDao getCountryDao() {
    	
		return countryDao;
	}
    @Autowired
	public void setCountryDao(CountryDao countryDao) {
		super.setEntityDao(countryDao);
		this.countryDao = countryDao;
	}


	@Override
    public List<AppGeoCountry> countryList() {
        return countryDao.CountryList();
    }
	@Override
    public List<AppGeoCountry> CountryListFindByName(AppGeoCountryVo vo) {
        return countryDao.CountryListFindByName(vo);
    }

}
