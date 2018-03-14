package com.hww.app.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * AbstractAppGeoCity entity provides the base persistence definition of the
 * AppGeoCity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "app_geo_city")
public class AppGeoCity extends AbsBaseEntity<Long> {

	// Fields

	private Long cityId;
	private Long provinceId;
	private String cityName;
	private Integer siteId;
	private Short status;
	private Timestamp createTime;
	private Timestamp lastModifyTime;
	private String cityNameEn;
	private String cityNameTw;
	private Double latitude;
	private Double longitude;
	private Integer radius;


	@Id
	@Column(name = "city_id", unique = true, nullable = false)
    @GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	public Long getCityId() {
		return this.cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	@Column(name = "province_id")
	public Long getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	@Column(name = "city_name", length = 32)
	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Column(name = "site_id")
	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	@Column(name = "status")
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "create_time", nullable = false, length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "last_modify_time", nullable = false, length = 19)
	public Timestamp getLastModifyTime() {
		return this.lastModifyTime;
	}

	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	@Column(name = "city_name_en", length = 32)
	public String getCityNameEn() {
		return this.cityNameEn;
	}

	public void setCityNameEn(String cityNameEn) {
		this.cityNameEn = cityNameEn;
	}

	@Column(name = "city_name_tw", length = 32)
	public String getCityNameTw() {
		return this.cityNameTw;
	}

	public void setCityNameTw(String cityNameTw) {
		this.cityNameTw = cityNameTw;
	}

	@Override
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return cityId;
	}


	@Column(name = "latitude")
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "longitude")
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "radius")
	public Integer getRadius() {
		return radius;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
	}

}