package com.hww.cms.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 内容分类类型
 * 
 * @author kongkenan
 *
 */
@Entity
@Table(name = "cms_category_type")
public class CmsCategoryType extends AbsBaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long categoryTypeId; // 内容分类类型
	private String categoryTypeName; // 内容分类名称

	@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name = "category_type_id")
	public Long getCategoryTypeId() {
		return categoryTypeId;
	}

	public void setCategoryTypeId(Long categoryTypeId) {
		this.categoryTypeId = categoryTypeId;
	}

	@Column(name = "category_type_name")
	public String getCategoryTypeName() {
		return categoryTypeName;
	}

	public void setCategoryTypeName(String categoryTypeName) {
		this.categoryTypeName = categoryTypeName;
	}

	@Override
	@Transient
	public Long getId() {
		return categoryTypeId;
	}
}