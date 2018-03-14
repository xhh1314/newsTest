package com.hww.cms.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

/**
 * 内容
 */
@Entity
@Table(name = "cms_content_data")
public class CmsContentData extends AbsBaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tagIds; // 标签id字符串
	private String keywordIds; // 关键字id串
	private String thumbUrl; // 缩略图地址
	private Long contentId;
	private CmsOrigin origin;// 来源
	private String originUrl;
	private Long srcCategoryId;// 原始分类id
	private Long userId;// 系统原始录入人 责任编辑
	private String title;// 标题
	private String shortTitle;// 短标题
	private String description;// 描述
	private String author;// 原文作者
	private String content;// 内容
	private Timestamp releaseTime; // 发布时间
	private Long snsTopicId;// 评论主题id
	private String attachmentIds;
	private String thumbIds;// 缩略图
	
	private Long snsOrginId;

	@Id
    @GeneratedValue(generator="snowFlake")
    @GenericGenerator(name="snowFlake", strategy="com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name = "content_id")
	public Long getContentId() {
		return this.contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	
	@Column(name = "origin_url")
	public String getOriginUrl() {
		return originUrl;
	}

	public void setOriginUrl(String originUrl) {
		this.originUrl = originUrl;
	}

	@ManyToOne
	@JoinColumn(name = "origin_id")
	public CmsOrigin getOrigin() {
		return this.origin;
	}

	public void setOrigin(CmsOrigin origin) {
		this.origin = origin;
	}

	@Column(name = "src_category_id")
	public Long getSrcCategoryId() {
		return this.srcCategoryId;
	}

	public void setSrcCategoryId(Long srcCategoryId) {
		this.srcCategoryId = srcCategoryId;
	}

	@Column(name = "user_id")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "title")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "short_title")
	public String getShortTitle() {
		return this.shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "author")
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "content", columnDefinition = "longtext")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "release_time")
	public Timestamp getReleaseTime() {
		return this.releaseTime;
	}

	public void setReleaseTime(Timestamp releaseTime) {
		this.releaseTime = releaseTime;
	}

	@Column(name = "sns_topic_id")
	public Long getSnsTopicId() {
		return snsTopicId;
	}

	public void setSnsTopicId(Long snsTopicId) {
		this.snsTopicId = snsTopicId;
	}

	@Override
	@Transient
	public Long getId() {
		return contentId;
	}

	@Column(name = "attachment_ids")
	public String getAttachmentIds() {
		return attachmentIds;
	}

	public void setAttachmentIds(String attachmentIds) {
		this.attachmentIds = attachmentIds;
	}

	@Column(name = "thumb_ids")
	public String getThumbIds() {
		return thumbIds;
	}

	public void setThumbIds(String thumbIds) {
		this.thumbIds = thumbIds;
	}

	@Column(name = "tag_ids")
	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	@Column(name = "keywordIds")
	public String getKeywordIds() {
		return keywordIds;
	}

	public void setKeywordIds(String keywordIds) {
		this.keywordIds = keywordIds;
	}

	@Column(name = "thumb_url")
	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	@Column(name = "sns_orgin_id")
	public Long getSnsOrginId() {
		return snsOrginId;
	}

	public void setSnsOrginId(Long snsOrginId) {
		this.snsOrginId = snsOrginId;
	}
	
	
}