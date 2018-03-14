package com.hww.cms.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

 
@Entity
@Table(name = "cms_special_r_category")
@JsonIgnoreProperties(value = "id")
public class CmsSpecialRCategory extends AbsBaseEntity<Long> {
	
	private Long rId ;//关联表id
	private Long specialId;//专题表id
	private Long categoryId;//新闻类别表id
	protected Integer siteId;// 站点id
    protected Short status;// 状态
	
	@Override
    @Transient
    public Long getId() {
        return rId;
    }

	@Id
	@GeneratedValue(generator = "snowFlake")
	@GenericGenerator(name="snowFlake", strategy="com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name = "r_id")
	public Long getrId() {
		return rId;
	}

	public void setrId(Long rId) {
		this.rId = rId;
	}
	@Column(name = "special_id")
	public Long getSpecialId() {
		return specialId;
	}

	public void setSpecialId(Long specialId) {
		this.specialId = specialId;
	}
	@Column(name = "category_id")
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	  @Override
	    @Column(name = "create_time")
	    public Timestamp getCreateTime() {
	        return createTime;
	    }

	    @Override
	    public void setCreateTime(Timestamp createTime) {
	        this.createTime = createTime;
	    }

	    @Override
	    @Column(name = "site_id")
	    public Integer getSiteId() {
	        return siteId;
	    }

	    @Override
	    public void setSiteId(Integer siteId) {
	        this.siteId = siteId;
	    }

	    @Override
	    @Column(name = "status")
	    public Short getStatus() {
	        return status;
	    }

	    @Override
	    public void setStatus(Short status) {
	        this.status = status;
	    }

	    @Override
	    @Column(name = "last_modify_time")
	    public Timestamp getLastModifyTime() {
	        return lastModifyTime;
	    }

	    @Override
	    public void setLastModifyTime(Timestamp lastModifyTime) {
	        this.lastModifyTime = lastModifyTime;
	    }


}
