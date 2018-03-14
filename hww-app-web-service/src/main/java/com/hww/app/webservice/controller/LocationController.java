package com.hww.app.webservice.controller;

import com.hww.app.common.dto.GeoCityDto;
import com.hww.app.common.dto.GeoCountryDto;
import com.hww.app.common.dto.GeoProvinceDto;
import com.hww.app.common.entity.AppGeoCity;
import com.hww.app.common.entity.AppGeoCountry;
import com.hww.app.common.entity.AppGeoProvince;
import com.hww.app.common.entity.AppNationalTelCode;
import com.hww.app.webservice.service.AppTelCodeService;
import com.hww.app.webservice.service.CountryService;
import com.hww.base.util.BeanUtils;
import com.hww.base.util.R;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/local")
public class LocationController {

    private static final Log log = LogFactory.getLog(LocationController.class);

    @Autowired
    private AppTelCodeService appTelCodeService;

    @Autowired
    private CountryService countryService;

    @RequestMapping(value = "countryList.do", method = {RequestMethod.POST})
    public R countryList() {
        try {
            List<AppGeoCountry> list = countryService.countryList();
            return R.ok().put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(1, "查询国家失败");
        }
    }

    @RequestMapping(value = "provinceList.do", method = { RequestMethod.POST})
    @ResponseBody
    public R countryList(GeoProvinceDto geoProvinceDto) {
//        Map<String, String> map = ValidatorUtils.validateEntity(geoProvinceDto);
//        if (!map.get("status").equals("200")) {
//            return R.error(1, map.get("message"));
//        }
        try {
            List<AppGeoProvince> list = countryService.provinceList(geoProvinceDto);
            return R.ok().put("data", list);
        } catch (Exception e) {

            e.printStackTrace();
            return R.error(1, "查询省份失败");
        }

    }

    @RequestMapping(value = "cityList.do", method = { RequestMethod.POST})
    @ResponseBody
    public R cityList(GeoCityDto geoCityDto) {
//        Map<String, String> map = ValidatorUtils.validateEntity(geoCityDto);
//        if (!map.get("status").equals("200")) {
//            return R.error(1, map.get("message"));
//        }
        try {
            List<AppGeoCity> list = countryService.cityList(geoCityDto);
            return R.ok().put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(1, "查询城市失败");
        }
    }

    @RequestMapping(value = "allInList.do", method = { RequestMethod.POST})
    @ResponseBody
    public R allInList() {
        try {
            GeoCountryDto countryDto = new GeoCountryDto();
            List<GeoCountryDto> countryList = new ArrayList<>();
            List<AppGeoCountry> countries = countryService.countryList();

            for (AppGeoCountry country : countries) {
                GeoCountryDto dto1 = new GeoCountryDto();
                GeoProvinceDto provinceDto = new GeoProvinceDto();
                provinceDto.setCountryId(country.getCountryId());
                List<GeoProvinceDto> provinceList = new ArrayList<>();
                List<AppGeoProvince> provinces = countryService.provinceList(provinceDto);
                for (AppGeoProvince province : provinces) {
                    GeoProvinceDto dto = new GeoProvinceDto();
                    GeoCityDto cityDto = new GeoCityDto();
                    cityDto.setProvinceId(province.getProvinceId());
                    List<GeoCityDto> cityList = new ArrayList<>();
                    List<AppGeoCity> cities = countryService.cityList(cityDto);
                    for (AppGeoCity city : cities) {
                        GeoCityDto dto2 = new GeoCityDto();
                        BeanUtils.copyProperties(dto2, city);
                        cityList.add(dto2);
                    }
                    BeanUtils.copyProperties(dto, province);
                    dto.setCites(cityList);
                    provinceList.add(dto);
                }
                BeanUtils.copyProperties(dto1, country);
                dto1.setProvinces(provinceList);
                countryList.add(dto1);
            }
            return R.ok().put("data", countryList);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(1, "查询城市失败");
        }
    }

    /**
     * @return data字段 返回国家手机号代码的集合
     */
    @RequestMapping(value = "countryCodeList.do", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R getNationalTelephoneCode() {
        List<AppNationalTelCode> appTelCodeVos = appTelCodeService.listNationalTelephoneCode();
        if (appTelCodeVos == null)
            return R.error(400, "没有查询到数据!");
        return R.ok().put("data", appTelCodeVos);
    }

}
