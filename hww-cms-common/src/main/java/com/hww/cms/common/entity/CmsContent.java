package com.hww.cms.common.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hww.base.common.entity.AbsBaseEntity;

/**
 * 内容
 */
@Entity
@Table(name = "cms_content")
@JsonIgnoreProperties(value = { "id" })
@IdClass(value = ContentUionPKID.class)
public class CmsContent extends AbsBaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long contentId; // 内容id
	private Long categoryId;
	private String author; // 作者
	private String title; // 标题
	private String shortTitle; // 短标题
	private String tagIds; // 标签id字符串
	private CmsContentType contentType; // 内容类型id
	private String linkUrl; // 外部链接地址
	private String keywordIds; // 关键字id串
	private String summary; // 摘要
	private String description; // 描述
	private String editor; // 责任编辑
	private Timestamp releaseTime; // 发布时间
	private Integer priority; // 顺序
	private String attachmentIds; // 附件id串
	private String thumbIds; // 缩略图Id串
	private Double longitude;// 经度
	private Double latitude;// 纬度
	private Short top; // 是否置顶 0 不置顶 1 置顶
	private String location; // 位置
	private Integer auditStatus; // 审核状态0:未审核1:审核通过2:审核不通过
	private String aboutNewIds; // 相关新闻id长串
	private Long contenttypeId; // 内容类型id
	private String thumbUrl;
	private Integer auditStep;//当前所属审核阶段
	private Integer auditStepResult;// 当前阶段审核状态 0审核不通过 1审核通过
	private Long auditFlowId;//审核流程id
	private String remark;//审核描述
	private Integer auditHisRecord;//审核次数
	private Integer manuallySortNum;
	private String videoLength;
	
	private Long snsOrginId;

	
	@Column(name="video_length")
	public String getVideoLength() {
		return videoLength;
	}

	public void setVideoLength(String videoLength) {
		this.videoLength = videoLength;
	}

	@Column(name="audit_his_record")
	public Integer getAuditHisRecord() {
		return auditHisRecord;
	}

	public void setAuditHisRecord(Integer auditHisRecord) {
		this.auditHisRecord = auditHisRecord;
	}

	@Column(name = "thumb_url")
	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	@Id
	//@GeneratedValue(generator = "snowFlake")
   //@GenericGenerator(name = "snowFlake", strategy = "com.hww.cms.common.SnowFlakeIdGenerator")
	@Column(name = "content_id")
	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	@Id
	@Column(name = "category_id")
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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

	@Column(name = "link_url")
	public String getLinkUrl() {
		return this.linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	@Column(name = "summary")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "editor")
	public String getEditor() {
		return this.editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	@Column(name = "release_time")
	public Timestamp getReleaseTime() {
		return this.releaseTime;
	}

	public void setReleaseTime(Timestamp releaseTime) {
		this.releaseTime = releaseTime;
	}

	@Column(name = "thumb_ids")
	public String getThumbIds() {
		return thumbIds;
	}

	public void setThumbIds(String thumbIds) {
		this.thumbIds = thumbIds;
	}

	@Column(name = "attachment_ids")
	public String getAttachmentIds() {
		return attachmentIds;
	}

	public void setAttachmentIds(String attachmentIds) {
		this.attachmentIds = attachmentIds;
	}

	@Column(name = "tag_ids")
	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	@Transient
	@ManyToOne
	@JoinColumn(name = "content_type_id")
	public CmsContentType getContentType() {
		return contentType;
	}

	public void setContentType(CmsContentType contentType) {
		this.contentType = contentType;
	}

	@Column(name = "keyword_ids")
	public String getKeywordIds() {
		return keywordIds;
	}

	public void setKeywordIds(String keywordIds) {
		this.keywordIds = keywordIds;
	}

	@Column(name = "priority")
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(name = "longitude")
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude")
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "author")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "top")
	public Short getTop() {
		return top;
	}

	public void setTop(Short top) {
		this.top = top;
	}

	@Override
	@Transient
	public Long getId() {
		return contentId;
	}

	@Column(name = "location")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "auditstatus")
	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	@Column(name = "about_new_ids")
	public String getAboutNewIds() {
		return aboutNewIds;
	}

	public void setAboutNewIds(String aboutNewIds) {
		this.aboutNewIds = aboutNewIds;
	}
	@Column(name = "content_type_id")
    public Long getContenttypeId() {
        return contenttypeId;
    }

    public void setContenttypeId(Long contenttypeId) {
        this.contenttypeId = contenttypeId;
    }
    @Column(name = "audit_step")
	public Integer getAuditStep() {
		return auditStep;
	}

	public void setAuditStep(Integer auditStep) {
		this.auditStep = auditStep;
	}
	@Column(name = "audit_step_result")
	public Integer getAuditStepResult() {
		return auditStepResult;
	}

	public void setAuditStepResult(Integer auditStepResult) {
		this.auditStepResult = auditStepResult;
	}
	@Column(name = "audit_flow_id")
	public Long getAuditFlowId() {
		return auditFlowId;
	}

	public void setAuditFlowId(Long auditFlowId) {
		this.auditFlowId = auditFlowId;
	}
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "manually_sort_num")
	public Integer getManuallySortNum() {
		return manuallySortNum;
	}

	public void setManuallySortNum(Integer manuallySortNum) {
		this.manuallySortNum = manuallySortNum;
	}
	@Column(name = "sns_orgin_id")
	public Long getSnsOrginId() {
		return snsOrginId;
	}

	public void setSnsOrginId(Long snsOrginId) {
		this.snsOrginId = snsOrginId;
	}

}