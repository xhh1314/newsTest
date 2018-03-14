package com.hww.app.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import com.hww.base.common.entity.IBaseTree;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * AbstractAppCategory entity provides the base persistence definition of the
 * AppCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "app_category")
public class AppCategory extends AbsBaseEntity<Long> implements IBaseTree<Long> {

	// Fields

	private Long columnId;
	private Integer specialType;
	private String columnName;
	private Long columnTypeId;
	private String columnDesc;
	private Integer sort;
	private Short status;
	private Integer siteId;
	private Timestamp createTime;
	private Timestamp lastModifyTime;
	private AppCategory parent;
	private String logo;
	private Short isDefault;
	private Short isCustom;
	private Integer recommNum;
	private Integer isDisplay;
	

	@Id
	@Column(name = "column_id", unique = true, nullable = false)
    @GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	public Long getColumnId() {
		return this.columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}
	
	@Column(name = "special_type", length = 100)
	public Integer getSpecialType() {
		return specialType;
	}

	public void setSpecialType(Integer specialType) {
		this.specialType = specialType;
	}

	@Column(name = "column_name", length = 100)
	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	@Column(name = "column_type_id")
	public Long getColumnTypeId() {
		return this.columnTypeId;
	}

	public void setColumnTypeId(Long columnTypeId) {
		this.columnTypeId = columnTypeId;
	}

	@Column(name = "column_desc", length = 100)
	public String getColumnDesc() {
		return this.columnDesc;
	}

	public void setColumnDesc(String columnDesc) {
		this.columnDesc = columnDesc;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "status")
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "site_id")
	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "last_modify_time", length = 19)
	public Timestamp getLastModifyTime() {
		return this.lastModifyTime;
	}

	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}


	@ManyToOne
	@JoinColumn(name = "parent_id")
	public AppCategory getParent() {
		return this.parent;
	}

	public void setParent(AppCategory parent) {
		this.parent = parent;
	}

	@Column(name = "logo")
	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Column(name = "is_default")
	public Short getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(Short isDefault) {
		this.isDefault = isDefault;
	}

	@Column(name = "is_custom")
	public Short getIsCustom() {
		return this.isCustom;
	}

	public void setIsCustom(Short isCustom) {
		this.isCustom = isCustom;
	}

	@Column(name = "recomm_num")
	public Integer getRecommNum() {
		return this.recommNum;
	}

	public void setRecommNum(Integer recommNum) {
		this.recommNum = recommNum;
	}


	@Override
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return columnId;
	}

	@Override
	@Transient
	public String getLftName() {
		// TODO Auto-generated method stub
		return DEF_LEFT_NAME;
	}

	@Override
	@Transient
	public String getRgtName() {
		// TODO Auto-generated method stub
		return DEF_RIGHT_NAME;
	}

	@Override
	@Transient
	public String getParentName() {
		// TODO Auto-generated method stub
		return DEF_PARENT_NAME;
	}

	@Override
	@Transient
	public Long getLft() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transient
	public void setLft(Long lft) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transient
	public Long getRgt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transient
	public void setRgt(Long rgt) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transient
	public Long getParentId() {
		AppCategory parent = getParent();
		if (parent != null) {
			return parent.getTreeId();
		} else {
			return null;
		}
	}
	@Column(name = "is_display")
	public Integer getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}

	@Override
	@Transient
	public Long getTreeId() {

		return columnId;
	}

	@Override
	@Transient
	public String getTreeCondition() {
		return "bean.siteId=" + getSiteId();
	}

}