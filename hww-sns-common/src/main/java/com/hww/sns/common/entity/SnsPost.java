package com.hww.sns.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 帖子，跟帖
 *
 */
@Entity
@Table(name = "sns_post")
public class SnsPost
	extends
		AbsBaseEntity<Long> {
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

    

    @Column(name = "topic_type")
    public Integer getTopicType() {
		return topicType;
	}

	public void setTopicType(Integer topicType) {
		this.topicType = topicType;
	}

	@Id
    @GeneratedValue(generator = "snowFlake")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
		@GenericGenerator(name="snowFlake", strategy="com.hww.framework.common.idgen.SnowFlakeIdGenerator")
    @Column(name = "post_id")
    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Column(name = "topic_id")
    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

	@Column(name = "floor")
    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    @Column(name = "anonymous")
    public Integer getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Integer anonymous) {
        this.anonymous = anonymous;
    }


	@Column(name = "member_id")
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "auditstatus")
	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	@Override
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return postId;
	}

	@Column(name = "up_num")
	public Long getUpNum() {
		return upNum;
	}

	public void setUpNum(Long upNum) {
		this.upNum = upNum;
	}

	@Column(name = "longitude")
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude")
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "comment_num")
    public Long getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Long commentNum) {
        this.commentNum = commentNum;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "parent_id")
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Column(name = "type")
    public Integer getType() {
      return type;
    }

    public void setType(Integer type) {
      this.type = type;
    }

    @Column(name = "audit_flow")
	public Integer getAuditFlow() {
		return auditFlow;
	}


	public void setAuditFlow(Integer auditFlow) {
		this.auditFlow = auditFlow;
	}

	@Column(name = "show_status")
	public Integer getShowStatus() {
		return showStatus;
	}


	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}
	
	
}
