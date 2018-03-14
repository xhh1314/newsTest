package com.hww.cms.common.vo;

import java.sql.Timestamp;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hww.base.common.vo.IBaseVo;

@JsonIgnoreProperties(value = {"createTime", "lastModifyTime"})
public class CmsColumnVo implements IBaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long columnId; // 前台栏目id
	private String columnName; // 栏目名称
	private String columnDesc; // 栏目描述
	private Integer sort; // 排序
	private Short status;// 状态
	private Timestamp createTime;// 创建时间
	private Timestamp lastModifyTime;// 最后修改时间
	private Long parentId; //父id
	private String logo; //栏目logo
	private List<CmsColumnVo> cmsColumnVoList; //我的频道+更多频道+海外频道
	private List<CmsColumnVo> cmsDefaultVoList; //推荐频道
	private CmsColumnVo parent;
	private Short isDefault;
	private Short isCustom;
	public Short getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Short isDefault) {
		this.isDefault = isDefault;
	}

	private String categoryIds;
	
	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	private String checkedCategoryIds;
	
	public String getCheckedCategoryIds() {
		return checkedCategoryIds;
	}

	public void setCheckedCategoryIds(String checkedCategoryIds) {
		this.checkedCategoryIds = checkedCategoryIds;
	}

	public CmsColumnVo getParent() {
		return parent;
	}

	public void setParent(CmsColumnVo parent) {
		this.parent = parent;
	}

	private Integer siteId;
	
	
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Long getColumnId() {
		return columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnDesc() {
		return columnDesc;
	}

	public void setColumnDesc(String columnDesc) {
		this.columnDesc = columnDesc;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<CmsColumnVo> getCmsColumnVoList() {
		return cmsColumnVoList;
	}

	public void setCmsColumnVoList(List<CmsColumnVo> cmsColumnVoList) {
		this.cmsColumnVoList = cmsColumnVoList;
	}

	public List<CmsColumnVo> getCmsDefaultVoList() {
		return cmsDefaultVoList;
	}

	public void setCmsDefaultVoList(List<CmsColumnVo> cmsDefaultVoList) {
		this.cmsDefaultVoList = cmsDefaultVoList;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Short getIsCustom() {
		return isCustom;
	}

	public void setIsCustom(Short isCustom) {
		this.isCustom = isCustom;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CmsColumnVo that = (CmsColumnVo) o;
		if (columnId != null ? !columnId.equals(that.columnId) : that.columnId != null) return false;
		if (columnName != null ? !columnName.equals(that.columnName) : that.columnName != null) return false;
		if (columnDesc != null ? !columnDesc.equals(that.columnDesc) : that.columnDesc != null) return false;
		if (sort != null ? !sort.equals(that.sort) : that.sort != null) return false;
		if (status != null ? !status.equals(that.status) : that.status != null) return false;
		if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
		if (lastModifyTime != null ? !lastModifyTime.equals(that.lastModifyTime) : that.lastModifyTime != null)
			return false;
		if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
		if (logo != null ? !logo.equals(that.logo) : that.logo != null) return false;
		if (cmsColumnVoList != null ? !cmsColumnVoList.equals(that.cmsColumnVoList) : that.cmsColumnVoList != null)
			return false;
		return cmsDefaultVoList != null ? cmsDefaultVoList.equals(that.cmsDefaultVoList) : that.cmsDefaultVoList == null;
	}
}
