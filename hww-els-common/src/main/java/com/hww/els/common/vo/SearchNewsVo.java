package com.hww.els.common.vo;

import java.util.List;

/**
 * 搜索VO类
 * 
 * @author 13705
 *
 */
public class SearchNewsVo {

	private String str; // 搜索文本
	private Integer pageNo; // 页码
	private Integer pageSize; // 页量
	private Long userId; //用户id
	private String imei; //设备imei号
	private Double longitude;// 经度
	private Double latitude;// 纬度
	private Integer orderBy; //过滤条件，1为按时间排序，2为按位置排序
	private List<Long> ids; //id集合
	private Integer topicType; //主题类型 0新鲜事 1爆料

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public Integer getPageNo() {
		if (pageNo == null || pageNo == 0) {
			return 1;
		}
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		if (pageSize == null || pageSize == 0) {
			return 10;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Integer getTopicType() {
		return topicType;
	}

	public void setTopicType(Integer topicType) {
		this.topicType = topicType;
	}
}
