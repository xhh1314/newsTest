package com.hww.app.common.dto;

import com.hww.base.common.dto.AbsBaseDto;

import java.util.List;

public class GeoCountryDto extends AbsBaseDto<Long> {

    private Long countryId;
    private String countryName;
    private List<GeoProvinceDto> provinces;

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


    public List<GeoProvinceDto> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<GeoProvinceDto> provinces) {
        this.provinces = provinces;
    }
}
