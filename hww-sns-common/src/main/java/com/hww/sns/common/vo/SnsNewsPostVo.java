package com.hww.sns.common.vo;

import org.hibernate.validator.constraints.NotBlank;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by kongkenan on 2017/11/27.
 */
public class SnsNewsPostVo {

    @NotBlank(message = "评论id不能为空")
    private Long postId;
    private Long topicId;// 新闻id
    private Integer topicType;//所属主题topic类型0:新鲜事1:爆料
    private Integer floor;// 楼层，最大为总个数
    private Integer anonymous;// 是否匿名
    private Long memberId;//用户id
    private String avatar;//头像
    private String nickName;//昵称
    private String up;//跟帖
    private Integer concernType;// 社会化类型
    private Long upNum;//点赞数
    private Long commentNum;//评论数
    private String content;// 帖子内容
    private Integer auditStatus;//审核状态0新提交未审核1审核通过
    private Double longitude;//经度
    private Double latitude;//纬度
    private String address;// 地址
    private Integer type;// 地址
    private Long parentId;
    private String imei;
    private Long userId;
    private List<SnsNewsPostVo> snsNewsPostList; //评论列表
    private String createTimeStr; //创建时间字符串
    private boolean followFlag; //点赞标识 true为已点赞
    private Timestamp createTime;
    private Timestamp lastModifyTime;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUp() {
		return up;
	}

	public void setUp(String up) {
		this.up = up;
	}
	
	public Integer getConcernType() {
		return concernType;
	}

	public void setConcernType(Integer concernType) {
		this.concernType = concernType;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<SnsNewsPostVo> getSnsNewsPostList() {
        return snsNewsPostList;
    }

    public void setSnsNewsPostList(List<SnsNewsPostVo> snsNewsPostList) {
        this.snsNewsPostList = snsNewsPostList;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public boolean isFollowFlag() {
        return followFlag;
    }

    public void setFollowFlag(boolean followFlag) {
        this.followFlag = followFlag;
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
}
