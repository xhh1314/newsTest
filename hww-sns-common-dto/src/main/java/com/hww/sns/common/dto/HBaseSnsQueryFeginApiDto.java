package com.hww.sns.common.dto;


import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


public class HBaseSnsQueryFeginApiDto  implements Serializable

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
   
    protected List<Long> topicIds;
    
    public HBaseSnsQueryFeginApiDto() {
		super();
	}
    public static HBaseSnsQueryFeginApiDto newForNewsQuery(Long newsId, Long memberId) {
//		this.newsId = newsId;
//		this.memberId = memberId;
//		this.longitude = longitude;
//		this.latitude = latitude;
		return new HBaseSnsQueryFeginApiDto().setNewsId(newsId).setMemberId(memberId);
	} 
    public static HBaseSnsQueryFeginApiDto newForNewsQuery(Long newsId, Long memberId, Integer  pageNo,Integer pageSize) {
//		this.newsId = newsId;
//		this.memberId = memberId;
//		this.longitude = longitude;
//		this.latitude = latitude;
		return new HBaseSnsQueryFeginApiDto().setNewsId(newsId).setMemberId(memberId).setPageNo(pageNo).setPageSize(pageSize);
	} 
    
    public HBaseSnsQueryFeginApiDto(Long topicId, Long memberId, Double longitude,Double latitude) {
		super();
		this.topicId = topicId;
		this.memberId = memberId;
		this.longitude = longitude;
		this.latitude = latitude;
	} 
    public HBaseSnsQueryFeginApiDto(Long topicId, Long memberId, Long authorMemberId, Double longitude,
			Double latitude) {
		super();
		this.topicId = topicId;
		this.memberId = memberId;
		this.authorMemberId = authorMemberId;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public HBaseSnsQueryFeginApiDto(Long topicId, Long memberId, Long authorMemberId, String imei, Double longitude,
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
	public HBaseSnsQueryFeginApiDto setTopicId(Long topicId) {
		this.topicId = topicId;
		return this;
	}
	public Long getMemberId() {
		return memberId;
	}
	public HBaseSnsQueryFeginApiDto setMemberId(Long memberId) {
		this.memberId = memberId;
		return this;
	}
	public Double getLongitude() {
		return longitude;
	}
	public HBaseSnsQueryFeginApiDto setLongitude(Double longitude) {
		this.longitude = longitude;
		return this;
	}
	public Double getLatitude() {
		return latitude;
	}
	public HBaseSnsQueryFeginApiDto setLatitude(Double latitude) {
		this.latitude = latitude;
		return this;
	}
	public Integer getPageNo() {
//		if(pageNo==null) {
//			pageNo=1;
//		}
		return pageNo;
	}
	public HBaseSnsQueryFeginApiDto setPageNo(Integer pageNo) {
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
	public HBaseSnsQueryFeginApiDto setPageSize(Integer pageSize) {
//		if(pageSize==null||pageSize<0) {
//			pageSize=10;
//		}
		this.pageSize = pageSize;
		return this;
	}
	public String getImei() {
		return imei;
	}
	public HBaseSnsQueryFeginApiDto setImei(String imei) {
		this.imei = imei;
		return this;
	}
	public Long getAuthorMemberId() {
		return authorMemberId;
	}
	public HBaseSnsQueryFeginApiDto setAuthorMemberId(Long authorMemberId) {
		this.authorMemberId = authorMemberId;
		return this;
	}


	public Long getPostId() {
		return postId;
	}

	public HBaseSnsQueryFeginApiDto setPostId(Long postId) {
		this.postId = postId;
		return this;
	}

	public Long getNewsId() {
		return newsId;
	}

	public HBaseSnsQueryFeginApiDto setNewsId(Long newsId) {
		this.newsId = newsId;
		return this;
	}

	public Long getParentPostId() {
		return parentPostId;
	}

	public HBaseSnsQueryFeginApiDto setParentPostId(Long parentPostId) {
		this.parentPostId = parentPostId;
		return this;
	}
	public List<Long> getTopicIds() {
		return topicIds;
	}
	public HBaseSnsQueryFeginApiDto setTopicIds(List<Long> topicIds) {
		this.topicIds = topicIds;
		return this;
	}
	@Override
	public String toString() {
		return "HBaseSnsQueryFeginApiDto [topicId=" + topicId + ", postId=" + postId + ", newsId=" + newsId
				+ ", parentPostId=" + parentPostId + ", memberId=" + memberId + ", authorMemberId=" + authorMemberId
				+ ", imei=" + imei + ", longitude=" + longitude + ", latitude=" + latitude + ", pageNo=" + pageNo
				+ ", pageSize=" + pageSize + ", topicIds=" + idToStr(topicIds) + "]";
	}
	
	public String toCachekey() {
		return "HBaseSnsQueryFeginApiDto [topicId=" + topicId + ", postId=" + postId + ", newsId=" + newsId
				+ ", parentPostId=" + parentPostId + ", memberId=" + memberId + ", authorMemberId=" + authorMemberId
				+ ", imei=" + imei + ", longitude=" + longitude + ", latitude=" + latitude + ", pageNo=" + pageNo
				+ ", pageSize=" + pageSize + ", topicIds=" + idToStr(topicIds) + "]";
	}
	private String idToStr(List<Long>  ids) {
		if(ids==null||ids.isEmpty()) {
			return "";
		}
		return String.join("_",ids.stream().map(val->String.valueOf(val)).collect(Collectors.toList()));
	}
   

}
