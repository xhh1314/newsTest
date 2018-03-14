package com.hww.app.common.dto;

import com.hww.base.common.dto.AbsBaseDto;


public class GeoCityDto extends AbsBaseDto<Long> {

    private Long cityId;
    private String cityName;
    private Long provinceId;
    private String cityNameEn;
    private String cityNameTw;
	private Double latitude;
	private Double longitude;
	private Integer radius;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityNameEn() {
        return cityNameEn;
    }

    public void setCityNameEn(String cityNameEn) {
        this.cityNameEn = cityNameEn;
    }

    public String getCityNameTw() {
        return cityNameTw;
    }

    public void setCityNameTw(String cityNameTw) {
        this.cityNameTw = cityNameTw;
    }
    
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Integer getRadius() {
		return radius;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
	}

	public String tocacheKey() {
		return "cityId=" + cityId + ", cityName=" + cityName + ", provinceId=" + provinceId
				+ ", cityNameEn=" + cityNameEn + ", cityNameTw=" + cityNameTw + "";
	}
    
	@Override
	public String toString() {
		return "GeoCityDto [cityId=" + cityId + ", cityName=" + cityName + ", provinceId=" + provinceId
				+ ", cityNameEn=" + cityNameEn + ", cityNameTw=" + cityNameTw + "]";
	}
    
    
}
