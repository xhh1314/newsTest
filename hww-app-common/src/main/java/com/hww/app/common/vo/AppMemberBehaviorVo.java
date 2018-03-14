package com.hww.app.common.vo;

import com.hww.base.common.vo.AbsBaseVo;

import java.sql.Timestamp;

public class AppMemberBehaviorVo extends AbsBaseVo {

	private Long behaviorId;
	private Long memberId;//会员id
	private Long contentId;//cmsConent/topic/post  id
	private String uuid;//设备imei号
	private Integer bevType;// 0 详情查看， 1点赞 ，2 收藏，3评论，4爆料，5 感兴趣，6内容太水，7看过了
	private String keywords;//不感兴趣新闻关键词
	private Integer bevValue;//1 点赞收藏等，0 取消点赞收藏等
	private Short plateType;// 0  新闻  1 帖子/新鲜事  2 评论
	
	
	
	public Integer getBevValue() {
		return bevValue;
	}
	public void setBevValue(Integer bevValue) {
		this.bevValue = bevValue;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getContentId() {
		return contentId;
	}
	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getBevType() {
		return bevType;
	}
	public void setBevType(Integer bevType) {
		this.bevType = bevType;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Short getPlateType() {
		return plateType;
	}
	public void setPlateType(Short plateType) {
		this.plateType = plateType;
	}
	public Long getBehaviorId() {
		return behaviorId;
	}
	public void setBehaviorId(Long behaviorId) {
		this.behaviorId = behaviorId;
	}
	
}
