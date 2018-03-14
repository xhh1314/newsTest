package com.hww.els.common.entity;

import java.io.Serializable;
import java.util.Date;
public class ESContent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String title;
	private String shortTitle;
	private String summary;
	private String content;
	private String keywords;
	private Integer priority;
	private Integer auditStatus ;
	
	private Double longitude;
	
	private Double latitude;
	
	private String geolocation;
	
	private String address;
	
	private Integer top ;
	
	private Date createTime;
	
	private Long createTimeLong;
	
	
	private Date releaseTime;

	private Long releaseTimeLong;
	
	private Integer status;//
//	private Long cloumnId ;//新闻频道id
//	private Long specialId ;//新闻专题id
	private Long memberId;
	
	private Long relatednewsId;//爆料关联的新闻id
	
	private Integer contentType ;// 2图文 5图集  6视频
	
	private Integer topicType ;// 0爆料 1新鲜事 ，2 评论

	private Integer  plateType;//0 新闻 1 topic 2 post
	
	private Integer anonymous;//topic
	private Integer publi;//topic
	private Integer showStatus;//topic可见性
	
	public Integer getShowStatus() {
		return showStatus;
	}
	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Integer getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
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
	public String getGeolocation() {
		return geolocation;
	}
	public void setGeolocation(String geolocation) {
		this.geolocation = geolocation;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getTop() {
		return top;
	}
	public void setTop(Integer top) {
		this.top = top;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getRelatednewsId() {
		return relatednewsId;
	}
	public void setRelatednewsId(Long relatednewsId) {
		this.relatednewsId = relatednewsId;
	}
	public Integer getContentType() {
		return contentType;
	}
	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}
	public Integer getPlateType() {
		return plateType;
	}
	public void setPlateType(Integer plateType) {
		this.plateType = plateType;
	}
	public Integer getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(Integer anonymous) {
		this.anonymous = anonymous;
	}
	public Integer getPubli() {
		return publi;
	}
	public void setPubli(Integer publi) {
		this.publi = publi;
	}
	public Integer getTopicType() {
		return topicType;
	}
	public void setTopicType(Integer topicType) {
		this.topicType = topicType;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Long getCreateTimeLong() {
		return createTimeLong;
	}
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong = createTimeLong;
	}
	public Long getReleaseTimeLong() {
		return releaseTimeLong;
	}
	public void setReleaseTimeLong(Long releaseTimeLong) {
		this.releaseTimeLong = releaseTimeLong;
	}
	


}
