package com.hww.file.common.vo;

import com.hww.base.common.entity.AbsBaseEntity;

/**
 * 附件集合的基础类，用来管理一类相关的file
 *
 */
public class FileSetVo extends AbsBaseEntity<Long>{

	private Long setId;// 集id key
	private Long memberId;//// 作者id 会员id
	private Short modelId;//来自constants里的三个值
	
	private Integer total;//包含的文件个数，包含的集数（电视剧）
	
	public Long getSetId() {
		return setId;
	}

	public void setSetId(Long setId) {
		this.setId = setId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Short getModelId() {
		return modelId;
	}

	public void setModelId(Short modelId) {
		this.modelId = modelId;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return setId;
	}
	

}
