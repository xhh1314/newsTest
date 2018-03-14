package com.hww.app.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * AbstractAppUserRCategory entity provides the base persistence definition of
 * the AppUserRCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "app_user_r_category")
public class AppUserRCategory extends AbsBaseEntity<Long> {

	// Fields

	private Long userColumnId;
	private Long userId;
	private Long columnId;
	private String imei;
	private Integer sort;

	// Constructors

	/** default constructor */
	public AppUserRCategory() {
	}

	/** minimal constructor */
	public AppUserRCategory(Long userColumnId) {
		this.userColumnId = userColumnId;
	}

	/** full constructor */
	public AppUserRCategory(Long userColumnId, Long userId,
			Long columnId, String imei, Integer sort) {
		this.userColumnId = userColumnId;
		this.userId = userId;
		this.columnId = columnId;
		this.imei = imei;
		this.sort = sort;
	}

	// Property accessors
	@Id
	@Column(name = "user_column_id", unique = true, nullable = false)
    @GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	public Long getUserColumnId() {
		return this.userColumnId;
	}

	public void setUserColumnId(Long userColumnId) {
		this.userColumnId = userColumnId;
	}

	@Column(name = "user_id")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "column_id")
	public Long getColumnId() {
		return this.columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	@Column(name = "imei", length = 64)
	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return userColumnId;
	}

}