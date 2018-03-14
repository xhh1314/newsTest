package com.hww.sns.common.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.hww.base.common.entity.AbsBaseEntity;
import com.hww.base.common.entity.IBaseEntity;
@Entity
@Table(name = "sns_topic_es_sync_fail")
public class SnsTopicEsSyncFail  implements

IBaseEntity<Long> {
	
	
	private Long id;
	private Long topicId;
	private Timestamp createTime;
	private Integer failWhat;
	private Integer isDealSuccess;
	 public SnsTopicEsSyncFail() {
			super();
	}
	 public SnsTopicEsSyncFail(Long topicId, Integer failWhat) {
			super();
			this.topicId = topicId;
			this.failWhat = failWhat;
	}
	
    public SnsTopicEsSyncFail(Long topicId, Integer failWhat, Integer isDealSuccess) {
		super();
		this.topicId = topicId;
		this.failWhat = failWhat;
		this.isDealSuccess = isDealSuccess;
	}
    @Id
    @GeneratedValue(generator = "snowFlake")
  //@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="snowFlake", strategy="com.hww.framework.common.idgen.SnowFlakeIdGenerator")
    @Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="topic_id")
	public Long getTopicId() {
		return topicId;
	}
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	@Column(name="create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	@Column(name="fail_what")
	public Integer getFailWhat() {
		return failWhat;
	}
	public void setFailWhat(Integer failWhat) {
		this.failWhat = failWhat;
	}
	@Column(name="is_deal_success")
	public Integer getIsDealSuccess() {
		return isDealSuccess;
	}
	public void setIsDealSuccess(Integer isDealSuccess) {
		this.isDealSuccess = isDealSuccess;
	}
	

}
