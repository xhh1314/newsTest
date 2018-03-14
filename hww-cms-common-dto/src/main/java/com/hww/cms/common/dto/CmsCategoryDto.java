package com.hww.cms.common.dto;

import com.hww.base.common.dto.IBaseDto;

public class CmsCategoryDto
        implements
        IBaseDto<Integer> {
    private Long categoryId;
    private Long parentId;
    private String categoryName;
    private Integer siteId;
    private Integer priority = 0;
    private Short display;
    private String xtype;
    private String url;
    private Integer depth;
    private Short status;
    private Boolean inOrOut = false;
    private String allIDCheck;
    private String description;
    private String link;
    private Integer pageNo;
    private Long flowId;

    public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public String getXtype() {
        return xtype;
    }

    public void setXtype(String xtype) {
        this.xtype = xtype;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Boolean getInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(Boolean inOrOut) {
        this.inOrOut = inOrOut;
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


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }


}
