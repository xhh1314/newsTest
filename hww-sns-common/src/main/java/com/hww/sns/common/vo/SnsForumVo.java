package com.hww.sns.common.vo;

import java.sql.Date;

import com.hww.base.common.vo.BaseTreeVo;
import com.hww.sns.common.entity.SnsForum;

//板块
public class SnsForumVo extends BaseTreeVo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long forumId;
	private String forumName;
	private SnsForum parent; //父分类
	private Long parentId;//父分类id
	
	private String title;
	private String description;
	
	private Integer topicTotal;//主题总数
	private Integer postTotal;//帖子总数
	
	private Date createTime;	//创建时间
	
	private String creator; //从session读取
	private Long userId;
	private Integer siteId;
	private Long lft;//如果不为空加载父分类
	private Long rgt;//如果部位空加载子分类
	private Short display;
	private Short status;
	private Integer pageNo;//当前页号
	private Integer auditFlow;
    private Integer showStatus;
	
	public Integer getAuditFlow() {
		return auditFlow;
	}
	public void setAuditFlow(Integer auditFlow) {
		this.auditFlow = auditFlow;
	}
	public Integer getShowStatus() {
		return showStatus;
	}
	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}
	public Long getForumId() {
		return forumId;
	}
	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}
	public String getForumName() {
		return forumName;
	}
	public void setForumName(String forumName) {
		this.forumName = forumName;
	}
	
	public SnsForum getParent() {
		return parent;
	}
	public void setParent(SnsForum parent) {
		this.parent = parent;
		if(parent!=null) {
			this.parentId = parent.getId();
		}
	}
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getTopicTotal() {
		return topicTotal;
	}
	public void setTopicTotal(Integer topicTotal) {
		this.topicTotal = topicTotal;
	}
	public Integer getPostTotal() {
		return postTotal;
	}
	public void setPostTotal(Integer postTotal) {
		this.postTotal = postTotal;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	
	public Long getLft() {
		return lft;
	}
	public void setLft(Long lft) {
		this.lft = lft;
	}
	public Long getRgt() {
		return rgt;
	}
	public void setRgt(Long rgt) {
		this.rgt = rgt;
	}
	
	public Short getDisplay() {
		return display;
	}
	public void setDisplay(Short display) {
		this.display = display;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	
	
	
}
