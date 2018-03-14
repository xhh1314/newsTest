//package com.hww.app.common.dto;
//
//
//import java.io.Serializable;
//
//
//public class HSearchDto implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    private String keywords;
//    private Long memberId;//会员id
//    private String imei;//设备imei号
//    private Integer searchType;// 综合 爆料 视频 新鲜事 用户 地点
//    private Integer pageNo; // 页码
//    private Integer pageSize; // 页量
//    private Double longitude;// 经度
//    private Double latitude;// 纬度
//    private Integer orderBy; //过滤条件，1为按时间排序，2为按位置排序
//
//    public Long getMemberId() {
//        return memberId;
//    }
//
//    public void setMemberId(Long memberId) {
//        this.memberId = memberId;
//    }
//
//   
//    public Integer getSearchType() {
//        return searchType;
//    }
//
//    public void setSearchType(Integer searchType) {
//        this.searchType = searchType;
//    }
//
//    public String getKeywords() {
//        return keywords;
//    }
//
//    public void setKeywords(String keywords) {
//        this.keywords = keywords;
//    }
//
//
//    public Integer getPageNo() {
//        return pageNo;
//    }
//
//    public void setPageNo(Integer pageNo) {
//        this.pageNo = pageNo;
//    }
//
//    public Integer getPageSize() {
//        return pageSize;
//    }
//
//    public void setPageSize(Integer pageSize) {
//        this.pageSize = pageSize;
//    }
//
//    public String getImei() {
//        return imei;
//    }
//
//    public void setImei(String imei) {
//        this.imei = imei;
//    }
//
//    public Double getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(Double longitude) {
//        this.longitude = longitude;
//    }
//
//    public Double getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(Double latitude) {
//        this.latitude = latitude;
//    }
//
//    public Integer getOrderBy() {
//        return orderBy;
//    }
//
//    public void setOrderBy(Integer orderBy) {
//        this.orderBy = orderBy;
//    }
//}