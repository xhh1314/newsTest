package com.hww.cms.common.vo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.hww.base.common.vo.AbsBaseVo;
import com.hww.cms.common.entity.CmsContent;
import com.hww.cms.common.entity.CmsOrigin;
import com.hww.file.common.entity.FileInfo;
import com.hww.file.common.vo.FileImgVo;
import com.hww.sns.common.vo.SnsPostVo;
import com.hww.sns.common.vo.SnsTopicVo;

public class CmsContentDataVo extends AbsBaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tagIds; // 标签id字符串
	private String keywordIds; // 关键字id串
	private String thumbUrl; // 缩略图地址
	private Long contentId;
	private String originUrl;
	private Long contenttypeId; // 内容类型id
	private CmsOrigin origin;// 来源
	private Long srcCategoryId;// 原始分类id
	private Integer userId;// 系统原始录入人 责任编辑
	private String title;// 标题
	private String shortTitle;// 短标题
	private String description;// 描述
	private String author;// 原文作者
	private String content;// 内容
	private List<Map> atlasContent;
	private String summary; // 摘要
	
	private Timestamp releaseTime; // 发布时间
	private Long snsTopicId;// 评论主题id
//	//新闻
//	图文 attachmentIds 封面图片id
//	图集 attachmentIds 图集id串 逗号分割
//	视频 attachmentIds 封面图片id    thumbIds 视频id
	private String attachmentIds;
	private String attachmentUrls;
	
	private String thumIds;//  thumbIds 视频id
	private String vedioUrl;
	private String videoLength;
	
	private boolean collectionFlag; // 新闻收藏状态 true为已收藏
	private boolean followFlag; //新闻点赞状态 true为已点赞
	private Integer topicCounts; // 新闻爆料数
	private Integer newsPostCounts; // 新闻评论数
	private Integer newsFollowCounts; // 新闻点赞数
	private Integer newsCollectCounts;//收藏数
	
	private String createTimeStr; // 创建时间字符串
	private String rangeNowTimeStr; // 距离当前时间字符串
	private String releaseTimeStr; // 发布时间字符串
	private CmsCategoryVo cmsCategoryVo; // 新闻内容分类vo
	private List<SnsPostVo> snsPostVoList; // 新闻评论列表list
	private List<FileInfo> fileInfoList; // 文件列表list
	private List<FileImgVo> fileImgVoList; // (图片列表list|图集)
	private List<CmsContent> cmsContentList; // 相关新闻列表list
	private List<SnsTopicVo> snsTopicList; // 新闻爆料列表list
	private Integer newPostFollowCounts; //新闻评论点赞数

	private Double longitude;// 经度
	private Double latitude;// 纬度
	private String linkUrl; // 外部链接地址
	
	
	private Long snsOrginId;
	
	
	public String getOriginUrl() {
		return originUrl;
	}

	public void setOriginUrl(String originUrl) {
		this.originUrl = originUrl;
	}

	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public String getKeywordIds() {
		return keywordIds;
	}

	public void setKeywordIds(String keywordIds) {
		this.keywordIds = keywordIds;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public CmsOrigin getOrigin() {
		return origin;
	}

	public void setOrigin(CmsOrigin origin) {
		this.origin = origin;
	}

	public Long getSrcCategoryId() {
		return srcCategoryId;
	}

	public void setSrcCategoryId(Long srcCategoryId) {
		this.srcCategoryId = srcCategoryId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Timestamp releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Long getSnsTopicId() {
		return snsTopicId;
	}

	public void setSnsTopicId(Long snsTopicId) {
		this.snsTopicId = snsTopicId;
	}

	public String getAttachmentIds() {
		return attachmentIds;
	}

	public void setAttachmentIds(String attachmentIds) {
		this.attachmentIds = attachmentIds;
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

	public CmsCategoryVo getCmsCategoryVo() {
		return cmsCategoryVo;
	}

	public void setCmsCategoryVo(CmsCategoryVo cmsCategoryVo) {
		this.cmsCategoryVo = cmsCategoryVo;
	}

	public List<SnsPostVo> getSnsPostVoList() {
		return snsPostVoList;
	}

	public void setSnsPostVoList(List<SnsPostVo> snsPostVoList) {
		this.snsPostVoList = snsPostVoList;
	}

	public List<FileInfo> getFileInfoList() {
		return fileInfoList;
	}

	public void setFileInfoList(List<FileInfo> fileInfoList) {
		this.fileInfoList = fileInfoList;
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

	public List<FileImgVo> getFileImgVoList() {
		return fileImgVoList;
	}

	public void setFileImgVoList(List<FileImgVo> fileImgVoList) {
		this.fileImgVoList = fileImgVoList;
	}

	public boolean isFollowFlag() {
		return followFlag;
	}

	public void setFollowFlag(boolean followFlag) {
		this.followFlag = followFlag;
	}

	public Integer getNewPostFollowCounts() {
		return newPostFollowCounts;
	}

	public void setNewPostFollowCounts(Integer newPostFollowCounts) {
		this.newPostFollowCounts = newPostFollowCounts;
	}

	public Long getContenttypeId() {
		return contenttypeId;
	}

	public void setContenttypeId(Long contenttypeId) {
		this.contenttypeId = contenttypeId;
	}

	public Integer getNewsCollectCounts() {
		return newsCollectCounts;
	}

	public void setNewsCollectCounts(Integer newsCollectCounts) {
		this.newsCollectCounts = newsCollectCounts;
	}

	public String getThumIds() {
		return thumIds;
	}

	public void setThumIds(String thumIds) {
		this.thumIds = thumIds;
	}

	public String getVedioUrl() {
		return vedioUrl;
	}

	public void setVedioUrl(String vedioUrl) {
		this.vedioUrl = vedioUrl;
	}

	public List<Map> getAtlasContent() {
		return atlasContent;
	}

	public void setAtlasContent(List<Map> atlasContent) {
		this.atlasContent = atlasContent;
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

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getAttachmentUrls() {
		return attachmentUrls;
	}

	public void setAttachmentUrls(String attachmentUrls) {
		this.attachmentUrls = attachmentUrls;
	}

	public String getVideoLength() {
		return videoLength;
	}

	public void setVideoLength(String videoLength) {
		this.videoLength = videoLength;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Long getSnsOrginId() {
		return snsOrginId;
	}

	public void setSnsOrginId(Long snsOrginId) {
		this.snsOrginId = snsOrginId;
	}
	
}
