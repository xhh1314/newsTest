package com.hww.file.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.hww.base.common.entity.AbsBaseEntity;

/**
 * 附件集合的基础类，用来管理一类相关的file
 *
 */
@Entity
@Table(name="file_set")
public class FileSet extends AbsBaseEntity<Long>{

	private Long setId;// 集id key
	private Long memberId;//// 作者id 会员id
	private Short modelId;//来自constants里的三个值
	
	private Integer total;//包含的文件个数，包含的集数（电视剧）

	@Id
	@GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name="set_id")
	public Long getSetId() {
		return setId;
	}

	public void setSetId(Long setId) {
		this.setId = setId;
	}

	@Column(name="member_id")
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
	@Column(name="model_id")
	public Short getModelId() {
		return modelId;
	}

	public void setModelId(Short modelId) {
		this.modelId = modelId;
	}

	@Column(name="total")
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	@Override
	@GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return setId;
	}

	
}
