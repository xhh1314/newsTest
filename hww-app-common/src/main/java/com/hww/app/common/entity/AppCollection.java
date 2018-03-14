package com.hww.app.common.entity;

import com.hww.base.common.entity.IBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

//@Entity
//@Table(name = "app_collection")
public class AppCollection implements IBaseEntity<Long> {
	private Long collectionId;//收藏表id
	private Long contentId;//新闻id
	private Short collectionValue;//收藏状态 0 未收藏  1收藏
	private Short plateType;//0  新闻  1 帖子 2 回复
	private Timestamp createTime;//创建时间
	
	@Id
	@Column(name = "collection_id")
    @GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	public Long getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Long collectionId) {
		this.collectionId = collectionId;
	}

	@Column(name = "content_id")
	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}
	@Column(name = "collection_value")
	public Short getCollectionValue() {
		return collectionValue;
	}

	public void setCollectionValue(Short collectionValue) {
		this.collectionValue = collectionValue;
	}
	@Column(name = "plate_type")
	public Short getPlateType() {
		return plateType;
	}

	public void setPlateType(Short plateType) {
		this.plateType = plateType;
	}
	@Column(name = "create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public Long getId() {
        return collectionId;
    }

    public void setId(Long id) {
        this.collectionId = id;
    }

}