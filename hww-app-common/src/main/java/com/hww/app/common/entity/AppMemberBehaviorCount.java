package com.hww.app.common.entity;

import com.hww.base.common.entity.IBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "app_member_behavior_count")
public class AppMemberBehaviorCount implements IBaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private Long id;
	private Long contentId;
    private Integer bevCount;
	private Integer bevType;//用户行为类别1 点赞 2收藏   3评论  4分享  5不感兴趣(查看   6不感兴趣  7内容太水  8看过了)
	private Integer plateType;
	
	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "snowFlake")
	@GenericGenerator(name="snowFlake", strategy="com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "bev_count")
    public Integer getBevCount() {
		return bevCount;
	}

    public void setBevCount(Integer bevCount) {
		this.bevCount = bevCount;
	}


	@Column(name = "content_id", nullable = false)
	public Long getContentId() {
		return this.contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}


	@Column(name = "bev_type")
	public Integer getBevType() {
		return this.bevType;
	}

	public void setBevType(Integer bevType) {
		this.bevType = bevType;
	}
	
	@Column(name = "plate_type")
	public Integer getPlateType() {
		return plateType;
	}
	public void setPlateType(Integer plateType) {
		this.plateType = plateType;
	}

	
	

}