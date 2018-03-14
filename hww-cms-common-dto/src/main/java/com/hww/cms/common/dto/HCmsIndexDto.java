package com.hww.cms.common.dto;


import java.io.Serializable;


public class HCmsIndexDto  implements Serializable{
 
	private static final long serialVersionUID = 1L;
	
	protected Long memberId;// 当前登录用户id
	protected String imei;// 
	protected Double longitude;// 经度
	protected Double latitude;// 纬度
    protected Integer  pageNo;
    protected Integer pageSize;
	
	private String indexIds;
	
	//1 是上滑加载历史
	private Integer isPullUp;
	
	
	
    public HCmsIndexDto() {
		super();
	}

	public Double getLongitude() {
		return longitude;
	}
	public HCmsIndexDto setLongitude(Double longitude) {
		this.longitude = longitude;
		return this;
	}
	public Double getLatitude() {
		return latitude;
	}
	public HCmsIndexDto setLatitude(Double latitude) {
		this.latitude = latitude;
		return this;
	}
	public Integer getPageNo() {
//		if(pageNo==null) {
//			pageNo=1;
//		}
		return pageNo;
	}
	public HCmsIndexDto setPageNo(Integer pageNo) {
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
	public HCmsIndexDto setPageSize(Integer pageSize) {
//		if(pageSize==null||pageSize<0) {
//			pageSize=10;
//		}
		this.pageSize = pageSize;
		return this;
	}
	public String getImei() {
		return imei;
	}
	public HCmsIndexDto setImei(String imei) {
		this.imei = imei;
		return this;
	}



	public Long getMemberId() {
		return memberId;
	}

	public HCmsIndexDto setMemberId(Long memberId) {
		this.memberId = memberId;
		return this;
	}

	
	public String getIndexIds() {
		return indexIds;
	}

	public HCmsIndexDto setIndexIds(String indexIds) {
		this.indexIds = indexIds;
		return this;
	}

	public Integer getIsPullUp() {
		return isPullUp;
	}

	public HCmsIndexDto setIsPullUp(Integer isPullUp) {
		this.isPullUp = isPullUp;
		return this;
	}

}
