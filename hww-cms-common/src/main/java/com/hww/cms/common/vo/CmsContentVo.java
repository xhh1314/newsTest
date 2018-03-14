package com.hww.cms.common.vo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import com.hww.base.common.vo.AbsBaseVo;
import com.hww.cms.common.entity.CmsContent;
import com.hww.cms.common.entity.CmsContentType;
import com.hww.file.common.entity.FileInfo;
import com.hww.sns.common.vo.SnsPostVo;
import com.hww.sns.common.vo.SnsTopicVo;

public class CmsContentVo
	extends
		AbsBaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long contentId; // 内容id
	private String categoryName;// 内容类型名称
	private Long categoryId; // 内容类型id
	private String categoryIds; // 发布新闻可以勾选多个分类
	private String content;// 内容
	private CmsCategoryVo cmsCategoryVo;
	private CmsContentType contentType;
	private CmsContentDataVo cmsContentDataVo;// 内容详情

	private Long contenttypeId; // 内容类型id
	private String author; // 作者
	private String title; // 标题
	private String shortTitle; // 短标题
	private String tagIds; // 标签id字符串
	private String linkUrl; // 外部链接地址
	private String keywordIds; // 关键字id串
	private String summary; // 摘要
	private String description; // 描述
	private String editor; // 责任编辑
	private Integer auditStatus; // 审核状态
	private Timestamp releaseTime; // 发布时间
	private Integer priority; // 顺序

	private String attachmentIds; // 附件id串
	// private String attachmentUrls;

	private String thumbUrl; // 缩略图地址,以逗号分割
	private String thumbIds; // 缩略图Id串

	private String vedioUrl;
	private String videoLength;

	private Double longitude;// 经度
	private Double latitude;// 纬度
	private String location; // 位置
	private String otherCategoryIds; // 其他内容类型ids
	private Integer siteId;// 站点id
	private Short status;// 状态
	private Timestamp createTime;// 创建时间
	private Timestamp lastModifyTime;// 最后修改时间
	private List<CmsContent> cmsContentList; // 相关新闻列表
	private List<SnsTopicVo> snsTopicList; // 新闻爆料列表
	private List<SnsPostVo> SnsPostVoList; // 新闻评论列表
	private List<FileInfo> fileInfoList; // 新闻缩略图列表
	private boolean collectionFlag; // 新闻收藏状态 true为已收藏
	private Integer topicCounts; // 新闻爆料数
	private Integer newsPostCounts; // 新闻评论数
	private Integer newsCollectCounts;// 收藏数

	private Integer newsFollowCounts; // 新闻点赞数
	private String createTimeStr; // 创建时间字符串
	private String rangeNowTimeStr; // 距离当前时间字符串
	private String releaseTimeStr; // 发布时间字符串
	private Long memberId; // 内容id
	private Short top;// 是否置顶 0 不置顶 1 置顶
	private Integer auditStep;// 当前所属审核阶段
	private Integer auditStepResult;// 当前阶段审核状态 0审核不通过 1审核通过
	private Long auditFlowId;// 审核流程id
	private String remark;// 审核描述
	private Integer auditHisRecord;// 审核次数
	private Long originId;// 来源id
	private String originUrl;
	private Integer manuallySortNum;
	private Long srcCategoryId;

	private Long snsOrginId;

	public Long getSrcCategoryId() {
		return srcCategoryId;
	}

	public void setSrcCategoryId(Long srcCategoryId) {
		this.srcCategoryId = srcCategoryId;
	}

	public String getOriginUrl() {
		return originUrl;
	}

	public void setOriginUrl(String originUrl) {
		this.originUrl = originUrl;
	}

	public Long getOriginId() {
		return originId;
	}

	public void setOriginId(Long originId) {
		this.originId = originId;
	}

	public Integer getAuditHisRecord() {
		return auditHisRecord;
	}

	public void setAuditHisRecord(Integer auditHisRecord) {
		this.auditHisRecord = auditHisRecord;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getAuditFlowId() {
		return auditFlowId;
	}

	public void setAuditFlowId(Long auditFlowId) {
		this.auditFlowId = auditFlowId;
	}

	public Integer getAuditStep() {
		return auditStep;
	}

	public void setAuditStep(Integer auditStep) {
		this.auditStep = auditStep;
	}

	public Integer getAuditStepResult() {
		return auditStepResult;
	}

	public void setAuditStepResult(Integer auditStepResult) {
		this.auditStepResult = auditStepResult;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getKeywordIds() {
		return keywordIds;
	}

	public void setKeywordIds(String keywordIds) {
		this.keywordIds = keywordIds;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Timestamp getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Timestamp releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getAttachmentIds() {
		return attachmentIds;
	}

	public void setAttachmentIds(String attachmentIds) {
		this.attachmentIds = attachmentIds;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public String getThumbIds() {
		return thumbIds;
	}

	public void setThumbIds(String thumbIds) {
		this.thumbIds = thumbIds;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOtherCategoryIds() {
		return otherCategoryIds;
	}

	public void setOtherCategoryIds(String otherCategoryIds) {
		this.otherCategoryIds = otherCategoryIds;
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

	public List<CmsContent> getCmsContentList() {
		return cmsContentList;
	}

	public void setCmsContentList(List<CmsContent> cmsContentList) {
		this.cmsContentList = cmsContentList;
	}

	public List<SnsTopicVo> getSnsTopicList() {
		return snsTopicList;
	}

	public void setSnsTopicList(List<SnsTopicVo> snsTopicList) {
		this.snsTopicList = snsTopicList;
	}

	public List<SnsPostVo> getSnsPostVoList() {
		return SnsPostVoList;
	}

	public void setSnsPostVoList(List<SnsPostVo> snsPostVoList) {
		SnsPostVoList = snsPostVoList;
	}

	public List<FileInfo> getFileInfoList() {
		return fileInfoList;
	}

	public void setFileInfoList(List<FileInfo> fileInfoList) {
		this.fileInfoList = fileInfoList;
	}

	public boolean isCollectionFlag() {
		return collectionFlag;
	}

	public void setCollectionFlag(boolean collectionFlag) {
		this.collectionFlag = collectionFlag;
	}

	public Integer getTopicCounts() {
		return topicCounts;
	}

	public void setTopicCounts(Integer topicCounts) {
		this.topicCounts = topicCounts;
	}

	public Integer getNewsPostCounts() {
		return newsPostCounts;
	}

	public void setNewsPostCounts(Integer newsPostCounts) {
		this.newsPostCounts = newsPostCounts;
	}

	public Integer getNewsFollowCounts() {
		return newsFollowCounts;
	}

	public void setNewsFollowCounts(Integer newsFollowCounts) {
		this.newsFollowCounts = newsFollowCounts;
	}

	public Long getContenttypeId() {
		return contenttypeId;
	}

	public void setContenttypeId(Long contenttypeId) {
		this.contenttypeId = contenttypeId;
	}

	public CmsCategoryVo getCmsCategoryVo() {
		return cmsCategoryVo;
	}

	public void setCmsCategoryVo(CmsCategoryVo cmsCategoryVo) {
		this.cmsCategoryVo = cmsCategoryVo;
	}

	public CmsContentType getContentType() {
		return contentType;
	}

	public void setContentType(CmsContentType contentType) {
		this.contentType = contentType;
		if (contentType != null) {
			this.contenttypeId = contentType.getContentTypeId();
		}
	}

	public CmsContentDataVo getCmsContentDataVo() {
		return cmsContentDataVo;
	}

	public void setCmsContentDataVo(CmsContentDataVo cmsContentDataVo) {
		this.cmsContentDataVo = cmsContentDataVo;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getRangeNowTimeStr() {
		return rangeNowTimeStr;
	}

	public void setRangeNowTimeStr(String rangeNowTimeStr) {
		this.rangeNowTimeStr = rangeNowTimeStr;
	}

	public String getReleaseTimeStr() {
		return releaseTimeStr;
	}

	public void setReleaseTimeStr(String releaseTimeStr) {
		this.releaseTimeStr = releaseTimeStr;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Short getTop() {
		return top;
	}

	public void setTop(Short top) {
		this.top = top;
	}

	public Integer getNewsCollectCounts() {
		return newsCollectCounts;
	}

	public void setNewsCollectCounts(Integer newsCollectCounts) {
		this.newsCollectCounts = newsCollectCounts;
	}

	public String getVedioUrl() {
		return vedioUrl;
	}

	public void setVedioUrl(String vedioUrl) {
		this.vedioUrl = vedioUrl;
	}

	// public String getAttachmentUrls() {
	// return attachmentUrls;
	// }
	//
	// public void setAttachmentUrls(String attachmentUrls) {
	// this.attachmentUrls = attachmentUrls;
	// }

	public String getVideoLength() {
		return videoLength;
	}

	public void setVideoLength(String videoLength) {
		this.videoLength = videoLength;
	}

	public Integer getManuallySortNum() {
		return manuallySortNum;
	}

	public void setManuallySortNum(Integer manuallySortNum) {
		this.manuallySortNum = manuallySortNum;
	}

	public Long getSnsOrginId() {
		return snsOrginId;
	}

	public void setSnsOrginId(Long snsOrginId) {
		this.snsOrginId = snsOrginId;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(contentId,categoryId);

	}

	@Override
	public boolean equals(Object otherObject) {
		// TODO Auto-generated method stub
		if (this == otherObject) {
			return true;
		}

		if (null == otherObject) {
			return false;
		}

		if (getClass() != otherObject.getClass()) {
			return false;
		}

		CmsContentVo other = (CmsContentVo) otherObject;
		return this.getContentId().longValue()==other.getContentId().longValue();
	}

}
