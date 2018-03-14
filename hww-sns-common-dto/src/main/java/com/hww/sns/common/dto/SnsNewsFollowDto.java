package com.hww.sns.common.dto;

import java.sql.Timestamp;
import java.util.Date;

import com.hww.base.common.dto.AbsBaseDto;

/**
 * 新闻统计dto
 * @author hewei
 *
 */
public class SnsNewsFollowDto
	extends
		AbsBaseDto<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long followId;
	private Long memberId;//用户id
	private Long newsId;//新闻id
	private Integer newsType; //新闻类型见Constants.newsType
	private Integer followType;//'0查看1点赞2评论3收藏4分享5不感兴趣'
	
	private Integer followNum; //统计数字
	private Integer notinterestedNum;//不感兴趣数
	private Integer upNum; //点赞数
    private Integer commentNum;//评论数
    private Integer collectNum;//收藏数
    private Integer shareNum;//分享数
    private Integer viewNum;;//查看数
    
	private Date beginTime;//开始时间
    private Date endTime;//结束时间
	private String imei;
	private Timestamp lastModifyTime;
	private Timestamp createTime;
	private Long newsPostId; //评论id
	private Long newsTopicId; //爆料id
	
	public Long getFollowId() {
		return followId;
	}
	public void setFollowId(Long followId) {
		this.followId = followId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getNewsId() {
		return newsId;
	}
	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}
	public Integer getNewsType() {
		return newsType;
	}
	public void setNewsType(Integer newsType) {
		this.newsType = newsType;
	}
	public Integer getFollowType() {
		return followType;
	}
	public void setFollowType(Integer followType) {
		this.followType = followType;
	}
	
	public Integer getFollowNum() {
		return followNum;
	}
	public void setFollowNum(Integer followNum) {
		this.followNum = followNum;
	}
	public Integer getNotinterestedNum() {
		return notinterestedNum;
	}
	public void setNotinterestedNum(Integer notinterestedNum) {
		this.notinterestedNum = notinterestedNum;
	}
	public Integer getUpNum() {
		return upNum;
	}
	public void setUpNum(Integer upNum) {
		this.upNum = upNum;
	}
	public Integer getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}
	public Integer getCollectNum() {
		return collectNum;
	}
	public void setCollectNum(Integer collectNum) {
		this.collectNum = collectNum;
	}
	public Integer getShareNum() {
		return shareNum;
	}
	public void setShareNum(Integer shareNum) {
		this.shareNum = shareNum;
	}
	public Integer getViewNum() {
		return viewNum;
	}
	public void setViewNum(Integer viewNum) {
		this.viewNum = viewNum;
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

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Timestamp getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Long getNewsPostId() {
		return newsPostId;
	}

	public void setNewsPostId(Long newsPostId) {
		this.newsPostId = newsPostId;
	}

	public Long getNewsTopicId() {
		return newsTopicId;
	}

	public void setNewsTopicId(Long newsTopicId) {
		this.newsTopicId = newsTopicId;
	}
}
