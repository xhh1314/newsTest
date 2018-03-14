package com.hww.cms.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

import javax.persistence.*;

/**
 * 专题表
 */
@Entity
@Table(name = "cms_special")
@JsonIgnoreProperties(value = "id")
public class CmsSpecial extends AbsBaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	private Long specialId;
	// 专题id
	/*
	 * private Long parentId;//父类专题id
	 */ private String specialName;// 专题名称
	private String keywords; // 关键词
	private String summary; // 摘要
	private String logo;
	// 专题logo图标url
	
	private Integer auditStatus;//审核状态
	private String creator; // 发布人
	private Integer sortNum;// 排序号
	private Integer siteId;// 站点id
	// private List<CmsSpecialContent> cmsSpecialContents;
	private Timestamp createTime;
	

	private CmsSpecial parent;
	private String categoryIds;
	private Short status; // 状态 0 禁用 1 可用
	private String specialUrl;
	
	
	private Integer recommPriority;
	

	@Id
	@GeneratedValue(generator = "snowFlake")
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name = "special_id")
	public Long getSpecialId() {
		return specialId;
	}

	public void setSpecialId(Long specialId) {
		this.specialId = specialId;
	}

	/*
	 * @Column(name = "parend_id") public Long getParentId() { return parentId; }
	 * 
	 * public void setParentId(Long parentId) { this.parentId = parentId; }
	 */
	@Column(name = "special_name")
	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	@Column(name = "keywords")
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Column(name = "status")
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "summary")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "logo")
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	/*
	 * @JoinColumn(name = "auditstatus")
	 * 
	 * @ManyToOne public Integer getAuditStatus() { return auditStatus; }
	 * 
	 * public void setAuditStatus(Integer auditStatus) { this.auditStatus =
	 * auditStatus; }
	 */

	@Column(name = "creator")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	/*
	 * @OneToMany //指定一对多关系
	 * 
	 * @Cascade(value={CascadeType.SAVE_UPDATE,CascadeType.PERSIST,CascadeType.MERGE
	 * }) //设定级联关系
	 * 
	 * @JoinColumn(name="special_id") //指定与本类主键所对应的外表的外键 public
	 * List<CmsSpecialContent> getCmsSpecialContents() { return cmsSpecialContents;
	 * }
	 * 
	 * public void setCmsSpecialContents(List<CmsSpecialContent> cmsSpecialContents)
	 * { this.cmsSpecialContents = cmsSpecialContents; }
	 */

	@Override
	@Transient
	public Long getId() {
		return specialId;
	}
	/*
	 * @Column(name = "parent_id") public C getParentId() { return parent; }
	 * 
	 * public void setParentId(Long parentId) { this.parent = parentId; }
	 */

	@Column(name = "sort_num")
	public Integer getSortNum() {
		return sortNum;
	}

	@ManyToOne
	@JoinColumn(name = "parent_id")
	public CmsSpecial getParent() {
		return parent;
	}

	public void setParent(CmsSpecial parent) {
		this.parent = parent;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	@Column(name = "site_id")
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	@Column(name = "create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}
	@Column(name = "special_url")
	public String getSpecialUrl() {
		return specialUrl;
	}

	public void setSpecialUrl(String specialUrl) {
		this.specialUrl = specialUrl;
	}
	@Column(name = "auditstatus")
	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}


	@Column(name = "recomm_priority")
	public Integer getRecommPriority() {
		return recommPriority;
	}

	
	public void setRecommPriority(Integer recommPriority) {
		this.recommPriority = recommPriority;
	}

	
}
