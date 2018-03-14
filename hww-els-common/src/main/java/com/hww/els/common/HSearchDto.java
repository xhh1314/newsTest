package com.hww.els.common;


import java.io.Serializable;
import java.util.List;


public class HSearchDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String keywords;
    private Long memberId;//会员id
    private String imei;//设备imei号
    private Integer searchType;//1综合  2爆料 3视频 4 新鲜事 5 用户  6地点 # 附近的人 null 不限制   1 附近的人，2 签到 
    private Integer pageNo; // 页码
    private Integer pageSize; // 页量
    private Double longitude;// 经度
    private Double latitude;// 纬度
    private Integer orderBy; //过滤条件，1为按时间排序，2为按位置排序

    private Integer distance;
    
    private List<Long> hisids;
    
    private Integer contentType;// 2图文 5图集  6视频
    
    private Integer plateType;//0 news 1 topic 2 post
    
    public Long getMemberId() {
        return memberId;
    }

    public HSearchDto setMemberId(Long memberId) {
        this.memberId = memberId;
		return this;
    }

   
    public Integer getSearchType() {
        return searchType;
    }

    public HSearchDto setSearchType(Integer searchType) {
        this.searchType = searchType;
		return this;
    }

    public String getKeywords() {
        return keywords;
    }

    public HSearchDto setKeywords(String keywords) {
        this.keywords = keywords;
		return this;
    }


    public Integer getPageNo() {
        return pageNo;
    }

    public HSearchDto setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
		return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public HSearchDto setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
		return this;
    }

    public String getImei() {
        return imei;
    }

    public HSearchDto setImei(String imei) {
        this.imei = imei;
		return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public HSearchDto setLongitude(Double longitude) {
        this.longitude = longitude;
		return this;
    }

    public Double getLatitude() {
        return latitude;
    }

    public HSearchDto setLatitude(Double latitude) {
        this.latitude = latitude;
		return this;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public HSearchDto setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
		return this;
    }

	public Integer getDistance() {
		return distance;
	}

	public HSearchDto setDistance(Integer distance) {
		this.distance = distance;
		return this;
	}

	public List<Long> getHisids() {
		return hisids;
	}

	public HSearchDto setHisids(List<Long> hisids) {
		this.hisids = hisids;
		return this;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public Integer getPlateType() {
		return plateType;
	}

	public HSearchDto setPlateType(Integer plateType) {
		this.plateType = plateType;
		return this;
	}
    
    
}