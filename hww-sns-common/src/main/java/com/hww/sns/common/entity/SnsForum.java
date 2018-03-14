package com.hww.sns.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * 板块
 *
 */
@Entity
@Table(name = "sns_forum")
public class SnsForum
	extends
		AbsBaseEntity<Long> {
	private Long forumId;
	private String forumName;
	private SnsForum parent; //父分类

	private String title;
	private String description;
	private String rules;// 版规

	private Long topicTotal;// 主题总数
	private Long postTotal;// 帖子总数

	private String moderators;// 版主
	private String creator;//录入者

	private Integer auditFlow;//审核流程   0先发后审     1 先审后发
	
    private Integer showStatus;//是否可见 2新创建 1 审核通过 0 审核未通过
	@Id
    @GeneratedValue(generator = "snowFlake")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name = "forum_id")
	public Long getForumId() {
		return forumId;
	}

	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}

	@Column(name = "forum_name")
	public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	@ManyToOne
	@JoinColumn(name = "parent_id")
	@NotFound(action=NotFoundAction.IGNORE)
	public SnsForum getParent() {
		return parent;
	}

	public void setParent(SnsForum parent) {
		this.parent = parent;
	}


	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * @JoinTable(name = "sns_r_forum_keyword", joinColumns = {
	 * 
	 * @JoinColumn(name = "forum_id", referencedColumnName = "forum_id") },
	 * inverseJoinColumns = {
	 * 
	 * @JoinColumn(name = "keyword_id", referencedColumnName = "keyword_id") })
	 * 
	 * @ManyToMany public List<KeywordInfo> getKeywords() { return keywords; }
	 * 
	 * public void setKeywords(List<KeywordInfo> keywords) { this.keywords =
	 * keywords; }
	 */
	@Column(name = "rules")
	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	@Column(name = "topic_total")
	public Long getTopicTotal() {
		return topicTotal;
	}

	public void setTopicTotal(Long topicTotal) {
		this.topicTotal = topicTotal;
	}

	@Column(name = "post_total")
	public Long getPostTotal() {
		return postTotal;
	}

	public void setPostTotal(Long postTotal) {
		this.postTotal = postTotal;
	}

	@Column(name = "moderators")
	public String getModerators() {
		return moderators;
	}

	public void setModerators(String moderators) {
		this.moderators = moderators;
	}

	@Column(name = "creator")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Column(name = "audit_flow")
	public Integer getAuditFlow() {
		return auditFlow;
	}


	public void setAuditFlow(Integer auditFlow) {
		this.auditFlow = auditFlow;
	}

	@Column(name = "show_status")
	public Integer getShowStatus() {
		return showStatus;
	}


	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}

	@Override
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return forumId;
	}

}
