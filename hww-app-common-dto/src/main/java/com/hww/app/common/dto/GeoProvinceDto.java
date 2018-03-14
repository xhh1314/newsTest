package com.hww.app.common.dto;

import com.hww.base.common.dto.AbsBaseDto;

import java.util.List;


public class GeoProvinceDto extends AbsBaseDto<Long> {

    private Long provinceId;
    private String provinceName;
    private Long countryId;
    private String provinceNameEn;
    private String provinceNameTw;
	private String latitude;
	private String longitude;
	private String radius;
    private List<GeoCityDto> cites;

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getProvinceNameEn() {
        return provinceNameEn;
    }

    public void setProvinceNameEn(String provinceNameEn) {
        this.provinceNameEn = provinceNameEn;
    }

    public String getProvinceNameTw() {
        return provinceNameTw;
    }

    public void setProvinceNameTw(String provinceNameTw) {
        this.provinceNameTw = provinceNameTw;
    }

    public List<GeoCityDto> getCites() {
        return cites;
    }

    public void setCites(List<GeoCityDto> cites) {
        this.cites = cites;
    }

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getRadius() {
		return radius;
	}

	public void setRadius(String radius) {
		this.radius = radius;
	}
    
}
