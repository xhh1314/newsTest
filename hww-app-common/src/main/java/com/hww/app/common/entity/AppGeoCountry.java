package com.hww.app.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * AbstractAppGeoCountry entity provides the base persistence definition of the
 * AppGeoCountry entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "app_geo_country")
public class AppGeoCountry extends AbsBaseEntity<Long> {

	private Long countryId;
	private String countryName;
	private Integer siteId;
	private Short status;
	private Timestamp createTime;
	private Timestamp lastModifyTime;
	private String latitude;
	private String longitude;
	private String radius;
	@Transient
	private Integer isHaveChild;
	

	@Id
    @GeneratedValue(generator = "snowFlake")
	@Column(name = "country_id", unique = true, nullable = false)
	@GenericGenerator(name="snowFlake", strategy="com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	public Long getCountryId() {
		return this.countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	@Column(name = "country_name", length = 32)
	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
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
	
	@Column(name = "longitude")
	public String getLatitude() {
		return latitude;
	}

	@Column(name = "radius")
	public String getRadius() {
		return radius;
	}

	public void setRadius(String radius) {
		this.radius = radius;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Column(name = "latitude")
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	@Override
	@Transient
	public Long getId() {
		return countryId;
	}

	public Integer getIsHaveChild() {
		return isHaveChild;
	}

	public void setIsHaveChild(Integer isHaveChild) {
		this.isHaveChild = isHaveChild;
	}
	
	

}