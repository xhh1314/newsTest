package com.hww.app.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * AbstractAppGeoPrivince entity provides the base persistence definition of the
 * AppGeoPrivince entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "app_geo_province")
public class AppGeoProvince extends AbsBaseEntity<Long> {

	private Long provinceId;
	private String provinceName;
	private Long countryId;
	private Integer siteId;
	private Short status;
	private Timestamp createTime;
	private Timestamp lastModifyTime;
	private String provinceNameEn;
	private String provinceNameTw;
	private Double latitude;
	private Double longitude;
	private Integer radius;
	@Transient
	private Integer isHaveChild;

	@Id
	@Column(name = "province_id", unique = true, nullable = false)
    @GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	public Long getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	@Column(name = "province_name", length = 32)
	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	@Column(name = "country_id")
	public Long getCountryId() {
		return this.countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
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

	@Column(name = "province_name_en", length = 32)
	public String getProvinceNameEn() {
		return this.provinceNameEn;
	}

	public void setProvinceNameEn(String provinceNameEn) {
		this.provinceNameEn = provinceNameEn;
	}

	@Column(name = "province_name_tw", length = 32)
	public String getProvinceNameTw() {
		return this.provinceNameTw;
	}

	public void setProvinceNameTw(String provinceNameTw) {
		this.provinceNameTw = provinceNameTw;
	}

	public Integer getIsHaveChild() {
		return isHaveChild;
	}

	public void setIsHaveChild(Integer isHaveChild) {
		this.isHaveChild = isHaveChild;
	}

	@Override
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return provinceId;
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