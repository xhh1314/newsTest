package com.hww.app.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * AbstractAppCategoryRCmsCategory entity provides the base persistence
 * definition of the AppCategoryRCmsCategory entity. @author MyEclipse
 * Persistence Tools
 */
@Entity
@Table(name = "app_category_r_cms_category")
public class AppCategoryRCmsCategory extends AbsBaseEntity<Long> {

	// Fields

	private Long columnCategoryId;
	private Long columnId;
	private Long categoryId;

	@Id
	@Column(name = "column_category_id", unique = true, nullable = false)
    @GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	public Long getColumnCategoryId() {
		return this.columnCategoryId;
	}

	public void setColumnCategoryId(Long columnCategoryId) {
		this.columnCategoryId = columnCategoryId;
	}

	@Column(name = "column_id", nullable = false)
	public Long getColumnId() {
		return this.columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	@Column(name = "category_id", nullable = false)
	public Long getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return columnCategoryId;
	}

}