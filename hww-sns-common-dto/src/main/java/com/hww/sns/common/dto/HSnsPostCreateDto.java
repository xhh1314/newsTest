package com.hww.sns.common.dto;



import java.io.Serializable;

import com.hww.base.common.dto.AbsBaseDto;

public class HSnsPostCreateDto  implements Serializable{

	private Long memberId;//用户
    private Double longitude;//经度
    private Double latitude;//纬度
    private String address;//地址
    
//    private Long postId;
    private Long parentId; //判断跟帖或回复,如果parentid=postId，则为针对postId的回复
    
    private Long newsId;//如果是对新闻的评论
    
    private Long topicId;// 所属主题 topic
    
    private Integer floor;// 楼层，最大为总个数
    private Integer anonymous;// 是否匿名
    private String content;// 帖子内容
    
    private Integer type;// 类型
    private Integer topicType;//评论类型,0:新鲜事评论1:爆料评论2:新闻评论
    
//    private Integer auditFlow;
//    private Integer showStatus;
	
    
//	public Long getPostId() {
//        return postId;
//    }
//    public void setPostId(Long postId) {
//        this.postId = postId;
//    }
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
   
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
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
    public Long getMemberId() {
        return memberId;
    }
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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
	public Integer getTopicType() {
		return topicType;
	}
	public void setTopicType(Integer topicType) {
		this.topicType = topicType;
	}
	public Long getNewsId() {
		return newsId;
	}
	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}
	
}
