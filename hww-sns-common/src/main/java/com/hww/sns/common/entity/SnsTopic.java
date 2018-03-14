package com.hww.sns.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 主题 在板块列表中可见的，具体帖子是post
 *
 */
@Entity
@Table(name = "sns_topic")
public class SnsTopic    extends AbsBaseEntity<Long> 
// implements
// ISocializable,IContentable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long topicId;
    private Long memberId;//用户id
    private Long forumId;// 所属板块id 0新鲜事 1 爆料 固定板块不允许修改

    private Long relatednewsId; //关联新闻id
//    private Long contentId;

    private String title;// 主题的标题


    private Long firstPostId;// 第一个帖子id
    private Long lastPostId;// 最后一个帖子id

    private Integer upNum;//点赞数
    private Integer commentNum;//评论数
    private Integer auditStatus;//审核状态
    private Integer topicType;  //类型 主题类型0:新鲜事1:爆料
    private Integer anonymous;//是否匿名
    private Integer publi;//是否公开
    private String fileId;//图片
    private String address;// 主题的标题
    private Double longitude;//经度
    private Double latitude;//纬度
    private String content;// 主题内容（可空）
    private String outLink;// 来源链接

    private Long parentId; //父爆料id
    private String imei;
    private Integer auditFlow;
    private Integer showStatus;
    private String url;
    
    private Integer isAdminSetBl;//是否是管理员设为爆料：0 否 1 是


    
    @Id
    @GeneratedValue(generator = "snowFlake")
  //@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="snowFlake", strategy="com.hww.framework.common.idgen.SnowFlakeIdGenerator")
    @Column(name = "topic_id")
    public Long getTopicId() {
        return topicId;
    }


    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "out_link")
    public String getOutLink() {
        return outLink;
    }

    public void setOutLink(String outLink) {
        this.outLink = outLink;
    }

    @Column(name = "member_id")
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Column(name = "forum_id")
    public Long getForumId() {
        return forumId;
    }

    public void setForumId(Long forumId) {
        this.forumId = forumId;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "first_post_id")
    public Long getFirstPostId() {
        return firstPostId;
    }

    public void setFirstPostId(Long firstPostId) {
        this.firstPostId = firstPostId;
    }

    @Column(name = "last_post_id")
    public Long getLastPostId() {
        return lastPostId;
    }

    public void setLastPostId(Long lastPostId) {
        this.lastPostId = lastPostId;
    }

    @Override
    @Transient
    public Long getId() {
        // TODO Auto-generated method stub
        return topicId;
    }

    @Column(name = "up_num")
    public Integer getUpNum() {
        return upNum;
    }

    public void setUpNum(Integer upNum) {
        this.upNum = upNum;
    }

    @Column(name = "comment_num")
    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    @Column(name = "auditstatus")
    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    @Column(name = "topictype")
    public Integer getTopicType() {
        return topicType;
    }

    public void setTopicType(Integer topicType) {
        this.topicType = topicType;
    }


    @Column(name = "anonymous")
    public Integer getAnonymous() {
        return anonymous;
    }


    public void setAnonymous(Integer anonymous) {
        this.anonymous = anonymous;
    }


    @Column(name = "publi")
    public Integer getPubli() {
        return publi;
    }


    public void setPubli(Integer publi) {
        this.publi = publi;
    }


    @Column(name = "file_id")
    public String getFileId() {
        return fileId;
    }


    public void setFileId(String fileId) {
        this.fileId = fileId;
    }


    @Column(name = "address")
    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
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

    @Column(name = "relatednews_id")
    public Long getRelatednewsId() {
        return relatednewsId;
    }

    public void setRelatednewsId(Long relatednewsId) {
        this.relatednewsId = relatednewsId;
    }

    @Column(name = "parent_id")
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Column(name = "imei")
    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
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

	@Transient
	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}

    @Column(name = "is_admin_set_bl")
	public Integer getIsAdminSetBl() {
		return isAdminSetBl;
	}


	public void setIsAdminSetBl(Integer isAdminSetBl) {
		this.isAdminSetBl = isAdminSetBl;
	}


//	@Column(name = "content_id")
//	public Long getContentId() {
//		return contentId;
//	}
//
//
//	public void setContentId(Long contentId) {
//		this.contentId = contentId;
//	}
	
	
}
