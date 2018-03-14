package com.hww.cms.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import com.hww.base.common.entity.IBaseTree;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * 内容分类
 */
@Entity
@Table(name = "cms_category")
public class CmsCategory extends AbsBaseEntity<Long> implements IBaseTree<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long categoryId; // 内容分类id
	private CmsCategory parent; // 父分类id
	private String categoryName; // 内容名称
	private String keywords; // 关键词
	private String summary; // 摘要
	private String logo; // 专题logo图标url
	private Integer auditStatus;// 审核状态
	private String creator; // 发布人
	private Long lft; // 分类节点左值
	private Long rgt; // 分类节点右值
	private Integer priority; // 顺序
	private Short display; // 是否显示
	private Integer depth; // 深度
	private String description; // 描述
	private String outLink; // 外链- 当typeId为外部链接时
	private Short typeId; // 区分单页、外部链接、列表、专题
	private String relatedCategoryId; // 关联新闻id长串
	private Long flowId;

	@Column(name = "flow_id")
	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(generator = "snowFlake")
	@GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name = "category_id")
	public Long getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@ManyToOne
	@JoinColumn(name = "parent_id")
	public CmsCategory getParent() {
		return this.parent;
	}

	public void setParent(CmsCategory parent) {
		this.parent = parent;
	}

	@Column(name = "category_name")
	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Column(name = "lft")
	public Long getLft() {
		return this.lft;
	}

	public void setLft(Long lft) {
		this.lft = lft;
	}

	@Column(name = "rgt")
	public Long getRgt() {
		return this.rgt;
	}

	public void setRgt(Long rgt) {
		this.rgt = rgt;
	}

	@Column(name = "priority")
	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(name = "display")
	public Short getDisplay() {
		return this.display;
	}

	public void setDisplay(Short display) {
		this.display = display;
	}

	@Column(name = "depth")
	public Integer getDepth() {
		return this.depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	@Column(name = "type_id")
	public Short getTypeId() {
		return typeId;
	}

	public void setTypeId(Short typeId) {
		this.typeId = typeId;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "out_link")
	public String getOutLink() {
		return outLink;
	}

	public void setOutLink(String outLink) {
		this.outLink = outLink;
	}

	@Column(name = "keywords")
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
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

	@Column(name = "auditstatus")
	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	@Column(name = "creator")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Override
	@Transient
	public String getLftName() {
		return DEF_LEFT_NAME;
	}

	@Override
	@Transient
	public String getRgtName() {
		return DEF_RIGHT_NAME;
	}

	@Override
	@Transient
	public String getParentName() {
		return DEF_PARENT_NAME;
	}

	@Override
	@Transient
	public Long getParentId() {
		CmsCategory parent = getParent();
		if (parent != null) {
			return parent.getTreeId();
		} else {
			return null;
		}
	}

	@Override
	@Transient
	public String getTreeCondition() {
		return "bean.siteId=" + getSiteId();
	}

	@Column(name = "status")
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "related_category_id")
	public String getRelatedCategoryId() {
		return relatedCategoryId;
	}

	public void setRelatedCategoryId(String relatedCategoryId) {
		this.relatedCategoryId = relatedCategoryId;
	}

	@Override
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return this.categoryId;
	}

	@Override
	@Transient
	public Long getTreeId() {
		if (parent != null) {
			return parent.getTreeId();
		} else {
			return null;
		}
	}

}