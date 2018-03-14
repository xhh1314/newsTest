package com.hww.ucenter.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "ucenter_message")
public class UCenterMessage extends AbsBaseEntity<Long> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long messageId;
    private Long sendMemberId;
    private Long memberId;
    private Long resourceId;
    private Long commonId;
    
    private Integer bevType;//操作类型 0 查看, 1点赞 ，2 收藏，3评论
    private Integer targetType;//针对目标类型 0新鲜事，1爆料 ，2评论，3新闻 
    
//    private Long newsId;
//    private Long topicId;
    
    private String content;
//    private Integer type;
    private String isRead;
    
    private Double longitude;
    private Double latitude;
    
    private String address;

    private Integer subjectType;//消息针对的主体： 0新鲜事，1爆料 ，2 新闻
    
    private Long subjectContentId;

    
    @Id
    @Column(name = "message_id")
    @GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
    public Long getMessageId() {
        return messageId;
    }


    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "send_member_id")
    public Long getSendMemberId() {
        return sendMemberId;
    }


    public void setSendMemberId(Long sendMemberId) {
        this.sendMemberId = sendMemberId;
    }

    @Column(name = "common_id")
    public Long getCommonId() {
        return commonId;
    }


    public void setCommonId(Long commonId) {
        this.commonId = commonId;
    }

//    @Column(name = "type")
//    public Integer getType() {
//        return type;
//    }
//
//
//    public void setType(Integer type) {
//        this.type = type;
//    }

    
    @Column(name = "is_read")
    public String getIsRead() {
      return isRead;
    }


    public void setIsRead(String isRead) {
      this.isRead = isRead;
    }


    @Column(name = "member_id")
    public Long getMemberId() {
        return memberId;
    }


    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Column(name = "resource_id")
    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    @Transient
    public Long getId() {
        return messageId;
    }

    
    @Column(name = "bev_type")
	public Integer getBevType() {
		return bevType;
	}


	public void setBevType(Integer bevType) {
		this.bevType = bevType;
	}

	@Column(name = "target_type")
	public Integer getTargetType() {
		return targetType;
	}


	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}

//	@Column(name = "news_id")
//	public Long getNewsId() {
//		return newsId;
//	}
//
//
//	public void setNewsId(Long newsId) {
//		this.newsId = newsId;
//	}
//
//	@Column(name = "topic_id")
//	public Long getTopicId() {
//		return topicId;
//	}
//
//
//	public void setTopicId(Long topicId) {
//		this.topicId = topicId;
//	}

	@Column(name = "latitude")
	public Double getLongitude() {
		return longitude;
	}


	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "longitude")
	public Double getLatitude() {
		return latitude;
	}


	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	
	@Column(name = "subject_type")
	public Integer getSubjectType() {
		return subjectType;
	}


	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}

	@Column(name = "subject_content_id")
	public Long getSubjectContentId() {
		return subjectContentId;
	}


	public void setSubjectContentId(Long subjectContentId) {
		this.subjectContentId = subjectContentId;
	}
    
    
}
