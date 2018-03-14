package com.hww.cms.common.vo;

import java.sql.Timestamp;
import java.util.List;

import com.hww.base.common.vo.BaseTreeVo;
import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.entity.CmsContent;

public class CmsCategoryVo extends BaseTreeVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long categoryId; // 内容分类id
	private CmsCategory parent;
	private Long parentId; // 父分类id
	private String categoryName; // 内容名称
	private String keywords; // 关键词
	private String summary; // 摘要
	private String logo; // 专题logo图标url:[url1,url2]
	private Integer auditStatus;// 审核状态
	private Integer lft; // 分类节点左值
	private Integer rgt; // 分类节点右值
	private Integer priority; // 顺序
	private Short display; // 是否显示
	private Integer depth; // 深度
	private String description; // 描述
	private String outLink; // 外链- 当typeId为外部链接时
	private Short typeId; // 区分单页、外部链接、列表、专题
	private String allIDCheck; // 选中id列表
	private Integer siteId;// 站点id
	private Short status;// 状态
	private Timestamp createTime;// 创建时间
	private String creator; // 发布人
	private Timestamp lastModifyTime;// 最后修改时间
	private Integer pageNo; // 页码
	private Integer pageSize; // 页量
	private Long specialId;// 专题栏目id
	private List<CmsCategoryVo> cmsCategoryVos; // 子分类
	private String relatedCategoryId; // 子栏目关联新闻长串
	private List<String> logoUrls; // 可以直接访问的图片地址
	private List<CmsContentVo> cmsContentList;
	private String imei;
	private Long userId;
	private String specialName;

	private Long flowId;

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	public Long getSpecialId() {
		return specialId;
	}

	public void setSpecialId(Long specialId) {
		this.specialId = specialId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public CmsCategory getParent() {
		return parent;
	}

	public void setParent(CmsCategory parent) {
		if (parent != null) {
			this.parent = parent;
			this.parentId = parent.getCategoryId();
		}
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getLft() {
		return lft;
	}

	public void setLft(Integer lft) {
		this.lft = lft;
	}

	public Integer getRgt() {
		return rgt;
	}

	public void setRgt(Integer rgt) {
		this.rgt = rgt;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Short getDisplay() {
		return display;
	}

	public void setDisplay(Short display) {
		this.display = display;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOutLink() {
		return outLink;
	}

	public void setOutLink(String outLink) {
		this.outLink = outLink;
	}

	public Short getTypeId() {
		return typeId;
	}

	public void setTypeId(Short typeId) {
		this.typeId = typeId;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public String getAllIDCheck() {
		return allIDCheck;
	}

	public void setAllIDCheck(String allIDCheck) {
		this.allIDCheck = allIDCheck;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
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

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public List<CmsCategoryVo> getCmsCategoryVos() {
		return cmsCategoryVos;
	}

	public void setCmsCategoryVos(List<CmsCategoryVo> cmsCategoryVos) {
		this.cmsCategoryVos = cmsCategoryVos;
	}

	public List<String> getLogoUrls() {
		return logoUrls;
	}

	public void setLogoUrls(List<String> logoUrls) {
		this.logoUrls = logoUrls;
	}

	public String getRelatedCategoryId() {
		return relatedCategoryId;
	}

	public void setRelatedCategoryId(String relatedCategoryId) {
		this.relatedCategoryId = relatedCategoryId;
	}

	public List<CmsContentVo> getCmsContentList() {
		return cmsContentList;
	}

	public void setCmsContentList(List<CmsContentVo> cmsContentList) {
		this.cmsContentList = cmsContentList;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
