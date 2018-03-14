package com.hww.ucenter.common.vo;

import com.hww.base.common.vo.AbsBaseVo;

import java.sql.Timestamp;

public class MessageVo extends AbsBaseVo {

    private Long messageId;
    private Long sendMemberId;
    private String content;
    private String address;
    private Long commonId;
    private String isRead;
    private Long memberId;
    private Integer siteId;// 站点id
    private Short status;// 状态
    private Timestamp createTime;// 创建时间
    private Timestamp lastModifyTime;// 最后修改时间
    private Long resourceId;
    
    private String nickName;
    private String sex;
    private String avatar;
    private Integer upNum;//点赞数
    private Integer commentNum;//评论数
    
    private Integer type;
    private MessageVo original;
    //    private SnsPostVo post; //我发的帖
//    private SnsTopicVo topic;//我发的新鲜事 爆料
    private String up;
    private String url;

    
    private Long newsId;
    private Long topicId;
    private Integer bevType;
    private Integer targetType;
    
    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getSendMemberId() {
        return sendMemberId;
    }

    public void setSendMemberId(Long sendMemberId) {
        this.sendMemberId = sendMemberId;
    }

    public Long getCommonId() {
        return commonId;
    }

    public void setCommonId(Long commonId) {
        this.commonId = commonId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getUp() {
        return up;
    }

    public void setUp(String up) {
        this.up = up;
    }


    public MessageVo getOriginal() {
        return original;
    }

    public void setOriginal(MessageVo original) {
        this.original = original;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

	public Long getNewsId() {
		return newsId;
	}

	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public Integer getBevType() {
		return bevType;
	}

	public void setBevType(Integer bevType) {
		this.bevType = bevType;
	}

	public Integer getTargetType() {
		return targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}
}
