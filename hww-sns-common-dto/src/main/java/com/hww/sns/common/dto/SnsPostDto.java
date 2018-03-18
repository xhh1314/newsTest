package com.hww.sns.common.dto;

import com.hww.base.common.dto.AbsBaseDto;

//帖子
public class SnsPostDto extends AbsBaseDto<Long>{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long postId;
	private Long parentId; //如果有父表示回复,否则为评论
	private Long topicId;// 所属主题 topic
	private Integer floor;// 楼层，最大为总个数
	private Integer anonymous;// 是否匿名
	private Long memberId;//用户id
	private Long upNum;//点赞数
	private Long commentNum;//评论数
	// 分表
	// private String title;// 帖子标题
	private String content;// 帖子内容
	private Integer auditStatus;//审核状态0新提交未审核1审核通过

	private Double longitude;//经度
	private Double latitude;//纬度
	private String address;// 地址
	private Integer type;

	private Integer topicType;

	private Integer auditFlow;
	private Integer showStatus;

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public Integer getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(Integer anonymous) {
		this.anonymous = anonymous;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getUpNum() {
		return upNum;
	}

	public void setUpNum(Long upNum) {
		this.upNum = upNum;
	}

	public Long getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Long commentNum) {
		this.commentNum = commentNum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getTopicType() {
		return topicType;
	}

	public void setTopicType(Integer topicType) {
		this.topicType = topicType;
	}

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
}
