package com.hww.cms.common.dto;

import com.hww.base.common.dto.AbsBaseDto;

import java.sql.Timestamp;

public class CmsContentDto
        extends
        AbsBaseDto<Long> {

    public static final String PARAM_CONTENT_ID = "contentId";
    public static final String PARAM_CATEGORY_ID = "categoryId";
    public static final String PARAM_TITLE = "title";
    public static final String PARAM_SHORT_TITLE = "shortTitle";
    public static final String PARAM_DESCRIPTION = "description";
    public static final String PARAM_CONTENT_TYPE_ID = "contentTypeId";
    public static final String PARAM_LINK_URL = "linkUrl";
    public static final String PARAM_EDITOR = "editor";
    public static final String PARAM_RELEASE_TIME = "releaseTime";
    public static final String PARAM_SRC_CATEGORY_ID = "srcCategoryId";

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long contentId; //id
    private Integer auditstatus; //审核状态
    private Long categoryId; // 内容分类id
    private String title; // 标题
    private String shortTitle; // 短标题
    private String summary; //摘要
    private Integer parentId; // 父分类id
    private String content; //内容
    private String categoryName; // 内容名称
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
    private Timestamp lastModifyTime;// 最后修改时间
    private Integer pageNo; // 页码
    private Integer pageSize; //每页条数
    private Integer auditHisRecord;// 审核次数

	public Integer getAuditHisRecord() {
		return auditHisRecord;
	}

	public void setAuditHisRecord(Integer auditHisRecord) {
		this.auditHisRecord = auditHisRecord;
	}

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }


    public Integer getAuditstatus() {
        return auditstatus;
    }

    public void setAuditstatus(Integer auditstatus) {
        this.auditstatus = auditstatus;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
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

    public Integer getPageNo() {
        if (pageNo != null) {
            return pageNo;
        } else {
            return 1;
        }

    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        if (pageSize != null) {
            return pageSize;
        } else {
            return 20;
        }

    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
