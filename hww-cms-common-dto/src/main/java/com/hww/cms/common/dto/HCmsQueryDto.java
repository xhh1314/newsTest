package com.hww.cms.common.dto;


import java.io.Serializable;


public class HCmsQueryDto  implements Serializable{
 
	private static final long serialVersionUID = 1L;
	
	protected Long memberId;// 当前登录用户id
	protected String imei;// 
	protected Double longitude;// 经度
	protected Double latitude;// 纬度
    protected Integer  pageNo;
    protected Integer pageSize;

	private Long contentId; // 新闻id
	private Long columnId; // 栏目id
	private Long specialId; // 专题id
	private Long categoryId; // 内容类型id
	private String noInters;
	
    private Long contentType;
	private Integer orderBy;//1 时间。2 距离
	
	private String indexIds;
	
	
	
    public HCmsQueryDto() {
		super();
	}

	public Double getLongitude() {
		return longitude;
	}
	public HCmsQueryDto setLongitude(Double longitude) {
		this.longitude = longitude;
		return this;
	}
	public Double getLatitude() {
		return latitude;
	}
	public HCmsQueryDto setLatitude(Double latitude) {
		this.latitude = latitude;
		return this;
	}
	public Integer getPageNo() {
//		if(pageNo==null) {
//			pageNo=1;
//		}
		return pageNo;
	}
	public HCmsQueryDto setPageNo(Integer pageNo) {
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
	public HCmsQueryDto setPageSize(Integer pageSize) {
//		if(pageSize==null||pageSize<0) {
//			pageSize=10;
//		}
		this.pageSize = pageSize;
		return this;
	}
	public String getImei() {
		return imei;
	}
	public HCmsQueryDto setImei(String imei) {
		this.imei = imei;
		return this;
	}



	public Long getMemberId() {
		return memberId;
	}

	public HCmsQueryDto setMemberId(Long memberId) {
		this.memberId = memberId;
		return this;
	}

	public Long getContentId() {
		return contentId;
	}

	public HCmsQueryDto setContentId(Long contentId) {
		this.contentId = contentId;
		return this;
	}

	public Long getColumnId() {
		return columnId;
	}

	public HCmsQueryDto setColumnId(Long columnId) {
		this.columnId = columnId;
		return this;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public HCmsQueryDto setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
		return this;
	}

	public String getNoInters() {
		return noInters;
	}

	public HCmsQueryDto setNoInters(String noInters) {
		this.noInters = noInters;
		return this;
	}

	public Integer getOrderBy() {
		if(orderBy==null) {
			orderBy=1;
		}
		return orderBy;
	}

	public HCmsQueryDto setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
		return this;
	}

	public Long getContentType() {
		return contentType;
	}

	public HCmsQueryDto setContentType(Long contentType) {
		this.contentType = contentType;
		return this;
	}

	public Long getSpecialId() {
		return specialId;
	}

	public HCmsQueryDto setSpecialId(Long specialId) {
		this.specialId = specialId;
		return this;
	}

	public String getIndexIds() {
		return indexIds;
	}

	public void setIndexIds(String indexIds) {
		this.indexIds = indexIds;
	}
	public String tocacheKey() {
		return "memberId=" + memberId + ", imei=" + imei + ", longitude=" + longitude + ", latitude="
				+ latitude + ", pageNo=" + pageNo + ", pageSize=" + pageSize + ", contentId=" + contentId
				+ ", columnId=" + columnId + ", specialId=" + specialId + ", categoryId=" + categoryId + ", noInters="
				+ noInters + ", contentType=" + contentType + ", orderBy=" + orderBy + ", indexIds=" + indexIds +"";
	}
	@Override
	public String toString() {
		return "HCmsQueryDto [memberId=" + memberId + ", imei=" + imei + ", longitude=" + longitude + ", latitude="
				+ latitude + ", pageNo=" + pageNo + ", pageSize=" + pageSize + ", contentId=" + contentId
				+ ", columnId=" + columnId + ", specialId=" + specialId + ", categoryId=" + categoryId + ", noInters="
				+ noInters + ", contentType=" + contentType + ", orderBy=" + orderBy + ", indexIds=" + indexIds + "]";
	}

}
