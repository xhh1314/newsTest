package com.hww.els.common.entity;

import java.io.Serializable;
import java.util.Date;
public class ESRecommendHis implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String hisid;
	private Long memberId;
	private Integer type;//首页类型
	private String imei;
	private Long contentId;
	private Integer plateType;//0 news 1 
	private Date createTime;
	private Long recommTimeStamp;

	public ESRecommendHis() {
		super();
	}
	
	
	public ESRecommendHis(Long memberId, String imei, Integer type, Long contentId, Integer plateType) {
		super();
		this.memberId = memberId;
		this.imei = imei;
		this.type = type;
		this.contentId = contentId;
		this.plateType = plateType;
	}
	public String getHisid() {
		if(memberId==null) {
			return imei+"_"+contentId;
		}else {
			return memberId+"_"+contentId;
		}
	}
	public Long getMemberId() {
		return memberId;
	}
	public ESRecommendHis setMemberId(Long memberId) {
		this.memberId = memberId;
		return this;
	}
	public String getImei() {
		return imei;
	}
	public ESRecommendHis setImei(String imei) {
		this.imei = imei;
		return this;
	}
	public Long getContentId() {
		return contentId;
	}
	public ESRecommendHis setContentId(Long contentId) {
		this.contentId = contentId;
		return this;
	}
	public Integer getPlateType() {
		return plateType;
	}
	public ESRecommendHis setPlateType(Integer plateType) {
		this.plateType = plateType;
		return this;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public ESRecommendHis setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}
	public Long getRecommTimeStamp() {
		return recommTimeStamp;
	}
	public ESRecommendHis setRecommTimeStamp(Long recommTimeStamp) {
		this.recommTimeStamp = recommTimeStamp;
		return this;
	}
	public Integer getType() {
		return type;
	}
	public ESRecommendHis setType(Integer type) {
		this.type = type;
		return this;
	}
	public ESRecommendHis setHisid(String hisid) {
		this.hisid = hisid;
		return this;
	}
	
	
}
