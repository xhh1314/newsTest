package com.hww.app.common.dao.impl;

import com.hww.app.common.dao.CountryDao;
import com.hww.app.common.entity.AppGeoCountry;
import com.hww.app.common.vo.AppGeoCountryVo;
import com.hww.base.common.util.Finder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("countryDao")
public class CountryDaoImpl extends LocalEntityDaoImpl<Long, AppGeoCountry> implements CountryDao {

    @Override
    public List<AppGeoCountry> CountryList() {
        Finder f = Finder.create("from AppGeoCountry");
        List<AppGeoCountry> countries = (List<AppGeoCountry>) find(f);
        if (countries != null && countries.size() > 0) {
            return countries;
        }
        return null;
    }
    @Override
    public List<AppGeoCountry> CountryListFindByName(AppGeoCountryVo vo) {
        Finder f = Finder.create("from AppGeoCountry where 1=1");
        if(vo.getCountryName()!=null){
        	f.append(" and countryName like :countryName").setParam("countryName","%" + vo.getCountryName() + "%");
        }
        List<AppGeoCountry> countries = (List<AppGeoCountry>) find(f);
        if (countries != null && countries.size() > 0) {
            return countries;
        }
        return null;
    }

	

	
}
