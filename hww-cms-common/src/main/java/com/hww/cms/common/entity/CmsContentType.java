package com.hww.cms.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 内容类型表
 */
@Entity
@Table(name = "cms_content_type")
@JsonIgnoreProperties(value = {"id"})
public class CmsContentType extends AbsBaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long contentTypeId; // 内容类型id
	private String contentTypeName; // 内容类型名称

	@Id
	@Column(name = "content_type_id")
    @GeneratedValue(generator = "snowFlake")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	public Long getContentTypeId() {
		return this.contentTypeId;
	}

	public void setContentTypeId(Long contentTypeId) {
		this.contentTypeId = contentTypeId;
	}

	@Column(name = "content_type_name")
	public String getContentTypeName() {
		return this.contentTypeName;
	}

	public void setContentTypeName(String contentTypeName) {
		this.contentTypeName = contentTypeName;
	}

	@Override
	@Transient
	public Long getId() {
		return contentTypeId;
	}
}