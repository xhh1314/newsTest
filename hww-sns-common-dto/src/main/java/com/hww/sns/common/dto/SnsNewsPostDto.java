package com.hww.sns.common.dto;

import java.util.Date;


import com.hww.base.common.dto.AbsBaseDto;
import com.hww.base.common.vo.AbsBaseVo;

/**
 * 新闻评论dto
 * @author hewei
 *
 */
public class SnsNewsPostDto extends AbsBaseDto<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long postId;
	private Long parentId;	//判断跟帖或回复0:跟帖1:回复
	private Integer topicId;//新闻id
	private Integer topicType;//默认为空表示新闻评论
	private Integer floor;//楼层
	
	private Boolean anonymous;//是否匿名
	private Boolean upflag;//是否对主题点赞
	
	private Long memberId;//用户id
	//分表
	private String title;//帖子标题
	private String content;//帖子内容
	private Integer auditStatus;//0新提交未审核1审核通过
	
	private Date beginTime;//开始时间
    private Date endTime;//结束时间
	private SnsNewsFollowDto snsNewsFollowDto;
	
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
	public Integer getTopicId() {
		return topicId;
	}
	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}
	
	public Integer getTopicType() {
		return topicType;
	}
	public void setTopicType(Integer topicType) {
		this.topicType = topicType;
	}
	public Integer getFloor() {
		return floor;
	}
	public void setFloor(Integer floor) {
		this.floor = floor;
	}
	public Boolean getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(Boolean anonymous) {
		this.anonymous = anonymous;
	}
	
	public Boolean getUpflag() {
		return upflag;
	}
	public void setUpflag(Boolean upflag) {
		this.upflag = upflag;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Integer getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public SnsNewsFollowDto getSnsNewsFollowDto() {
		return snsNewsFollowDto;
	}
	public void setSnsNewsFollowDto(SnsNewsFollowDto snsNewsFollowDto) {
		this.snsNewsFollowDto = snsNewsFollowDto;
	}
	
	
}
