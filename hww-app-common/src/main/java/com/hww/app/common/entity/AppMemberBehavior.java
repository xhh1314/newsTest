package com.hww.app.common.entity;

import com.hww.base.common.entity.IBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * AbstractAppMemberBehavior entity provides the base persistence definition of
 * the AppMemberBehavior entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "app_member_behavior")
public class AppMemberBehavior implements IBaseEntity<Long> {
	private static final long serialVersionUID = -425890651835632580L;

	// Fields

	private Long behaviorId;
	private Long memberId;
	private Long contentId;
	private Long categoryId;
	private String uuid;
	private Timestamp createTime;
	private Timestamp readedTime;
//	private Short praised;
//	private Integer commented;
//	private Timestamp favoritedTime;
	private Integer bevType;//用户行为类别1 点赞 2收藏   3评论  4分享  5查看   6不感兴趣  7内容太水  8看过了
	private String keywords;
	private Integer bevValue;
	private Integer plateType;
	// Constructors

	/** default constructor */
	public AppMemberBehavior() {
	}

	/** minimal constructor */
	public AppMemberBehavior(Long behaviorId, Long contentId) {
		this.behaviorId = behaviorId;
		this.contentId = contentId;
	}

	/** full constructor */
	public AppMemberBehavior(Long behaviorId, Long memberId,
							 Long contentId, Long categoryId, String uuid,
							 Timestamp createTime, Timestamp readedTime, Integer type,Integer bevType, 
							 String keywords,Integer bevValue) {
		this.behaviorId = behaviorId;
		this.memberId = memberId;
		this.contentId = contentId;
		this.categoryId = categoryId;
		this.uuid = uuid;
		this.createTime = createTime;
		this.readedTime = readedTime;
		this.bevType = bevType;
		this.keywords = keywords;
		this.bevValue=bevValue;
	}

	// Property accessors
	@Id
	@Column(name = "behavior_id", unique = true, nullable = false)
    @GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	public Long getBehaviorId() {
		return this.behaviorId;
	}

	public void setBehaviorId(Long behaviorId) {
		this.behaviorId = behaviorId;
	}

	@Column(name = "member_id")
	public Long getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@Column(name = "content_id", nullable = false)
	public Long getContentId() {
		return this.contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	@Column(name = "category_id")
	public Long getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "uuid", length = 64)
	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "readed_time", length = 19)
	public Timestamp getReadedTime() {
		return this.readedTime;
	}

	public void setReadedTime(Timestamp readedTime) {
		this.readedTime = readedTime;
	}

	

	@Column(name = "bev_type")
	public Integer getBevType() {
		return this.bevType;
	}

	public void setBevType(Integer bevType) {
		this.bevType = bevType;
	}

	@Column(name = "keywords")
	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	@Column(name = "bev_value")
	public Integer getBevValue() {
		return bevValue;
	}

	public void setBevValue(Integer bevValue) {
		this.bevValue = bevValue;
	}
	@Column(name = "plate_type")
	public Integer getPlateType() {
		return plateType;
	}

	public void setPlateType(Integer plateType) {
		this.plateType = plateType;
	}

	@Override
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return behaviorId;
	}

}