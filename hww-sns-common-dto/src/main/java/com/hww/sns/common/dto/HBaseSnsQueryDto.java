package com.hww.sns.common.dto;


import java.io.Serializable;

import com.hww.base.common.dto.AbsBaseDto;



public class HBaseSnsQueryDto  implements Serializable

{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Long topicId;
	protected Long postId;
	protected Long newsId;//newsId
	protected Long parentPostId;
	protected Long memberId;// 当前登录用户id
	protected Long authorMemberId;// 作者用户用户id
	protected String imei;// 
	protected Double longitude;// 经度
	protected Double latitude;// 纬度
    protected Integer  pageNo;
    protected Integer pageSize;
    //【0/null】新鲜事，1只是爆料
    protected Integer  topicType;
    
    public HBaseSnsQueryDto() {
		super();
	}
    public static HBaseSnsQueryDto newForNewsQuery(Long newsId, Long memberId) {
//		this.newsId = newsId;
//		this.memberId = memberId;
//		this.longitude = longitude;
//		this.latitude = latitude;
		return new HBaseSnsQueryDto().setNewsId(newsId).setMemberId(memberId);
	} 
    public static HBaseSnsQueryDto newForNewsQuery(Long newsId, Long memberId, Integer  pageNo,Integer pageSize) {
//		this.newsId = newsId;
//		this.memberId = memberId;
//		this.longitude = longitude;
//		this.latitude = latitude;
		return new HBaseSnsQueryDto().setNewsId(newsId).setMemberId(memberId).setPageNo(pageNo).setPageSize(pageSize);
	} 
    
    public HBaseSnsQueryDto(Long topicId, Long memberId, Double longitude,Double latitude) {
		super();
		this.topicId = topicId;
		this.memberId = memberId;
		this.longitude = longitude;
		this.latitude = latitude;
	} 
    public HBaseSnsQueryDto(Long topicId, Long memberId, Long authorMemberId, Double longitude,
			Double latitude) {
		super();
		this.topicId = topicId;
		this.memberId = memberId;
		this.authorMemberId = authorMemberId;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public HBaseSnsQueryDto(Long topicId, Long memberId, Long authorMemberId, String imei, Double longitude,
			Double latitude, Integer pageNo, Integer pageSize) {
		super();
		this.topicId = topicId;
		this.memberId = memberId;
		this.authorMemberId = authorMemberId;
		this.imei = imei;
		this.longitude = longitude;
		this.latitude = latitude;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	public Long getTopicId() {
		return topicId;
	}
	public HBaseSnsQueryDto setTopicId(Long topicId) {
		this.topicId = topicId;
		return this;
	}
	public Long getMemberId() {
		return memberId;
	}
	public HBaseSnsQueryDto setMemberId(Long memberId) {
		this.memberId = memberId;
		return this;
	}
	public Double getLongitude() {
		return longitude;
	}
	public HBaseSnsQueryDto setLongitude(Double longitude) {
		this.longitude = longitude;
		return this;
	}
	public Double getLatitude() {
		return latitude;
	}
	public HBaseSnsQueryDto setLatitude(Double latitude) {
		this.latitude = latitude;
		return this;
	}
	public Integer getPageNo() {
//		if(pageNo==null) {
//			pageNo=1;
//		}
		return pageNo;
	}
	public HBaseSnsQueryDto setPageNo(Integer pageNo) {
		if(pageNo!=null&&pageNo<0) {
			pageNo=1;
		}
		this.pageNo = pageNo;
		return this;
	}
	public Integer getPageSize() {
//		if(pageSize==null) {
//			pageSize=10;
//		}
		return pageSize;
	}
	public HBaseSnsQueryDto setPageSize(Integer pageSize) {
//		if(pageSize==null||pageSize<0) {
//			pageSize=10;
//		}
		this.pageSize = pageSize;
		return this;
	}
	public String getImei() {
		return imei;
	}
	public HBaseSnsQueryDto setImei(String imei) {
		this.imei = imei;
		return this;
	}
	public Long getAuthorMemberId() {
		return authorMemberId;
	}
	public HBaseSnsQueryDto setAuthorMemberId(Long authorMemberId) {
		this.authorMemberId = authorMemberId;
		return this;
	}


	public Long getPostId() {
		return postId;
	}

	public HBaseSnsQueryDto setPostId(Long postId) {
		this.postId = postId;
		return this;
	}

	public Long getNewsId() {
		return newsId;
	}

	public HBaseSnsQueryDto setNewsId(Long newsId) {
		this.newsId = newsId;
		return this;
	}

	public Long getParentPostId() {
		return parentPostId;
	}

	public HBaseSnsQueryDto setParentPostId(Long parentPostId) {
		this.parentPostId = parentPostId;
		return this;
	}
	public Integer getTopicType() {
		return topicType;
	}
	public HBaseSnsQueryDto setTopicType(Integer topicType) {
		this.topicType = topicType;
		return this;
	}
   

}
