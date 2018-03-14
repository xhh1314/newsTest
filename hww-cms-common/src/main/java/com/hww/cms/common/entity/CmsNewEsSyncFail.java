package com.hww.cms.common.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import com.hww.base.common.entity.IBaseEntity;
@Entity
@Table(name = "cms_news_es_sync_fail")
public class CmsNewEsSyncFail    implements IBaseEntity<Long>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long contentId;
	private Timestamp createTime;
	private Integer failWhat;
	private Integer isDealSuccess;
	
	 public CmsNewEsSyncFail() {
			super();
	}
	 public CmsNewEsSyncFail(Long newsId, Integer failWhat) {
			super();
			this.contentId = newsId;
			this.failWhat = failWhat;
	}
	
 public CmsNewEsSyncFail(Long newsId, Integer failWhat, Integer isDealSuccess) {
		super();
		this.contentId = newsId;
		this.failWhat = failWhat;
		this.isDealSuccess = isDealSuccess;
	}
 
 
    @Id
    @GeneratedValue(generator = "snowFlake")
  //@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="snowFlake", strategy="com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name="id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="content_id")
	public Long getContentId() {
		return contentId;
	}
	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}
	@Column(name="create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	@Column(name="fail_what")
	public Integer getFailWhat() {
		return failWhat;
	}
	public void setFailWhat(Integer failWhat) {
		this.failWhat = failWhat;
	}
	@Column(name="is_deal_success")
	public Integer getIsDealSuccess() {
		return isDealSuccess;
	}
	public void setIsDealSuccess(Integer isDealSuccess) {
		this.isDealSuccess = isDealSuccess;
	}
	

}
