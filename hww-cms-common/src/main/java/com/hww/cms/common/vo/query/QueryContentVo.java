package com.hww.cms.common.vo.query;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;


/**
 * 新闻查询VO类 Created by kongkenan on 2017/11/18.
 */
public class QueryContentVo implements Serializable {

	@NotBlank(message = "经度不能为空")
	private Double longitude; // 经度
	@NotBlank(message = "纬度不能为空")
	private Double latitude; // 纬度
	@NotBlank(message = "分页页码不能为空")
	private Integer pageNo; // 页码
	@NotBlank(message = "分页页量不能为空")
	private Integer pageSize; // 页量
	@NotBlank(message = "用户id不能为空")
	private Long userId; // 用户id
	@NotBlank(message = "设备imei号不能为空")
	private String imei; // imei号
	@NotBlank(message = "新闻id不能为空")
	private Long contentId; // 新闻id
	@NotBlank(message = "栏目id不能为空")
	private Integer columnId; // 栏目id
	
	private Integer categoryId; // 内容类型id
	private String noInters;
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

	public Integer getPageNo() {
		return (pageNo == null || pageNo < 1) ? 1 : pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return (pageSize == null || pageSize < 1) ? 10 : pageSize;
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

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public Integer getColumnId() {
		return columnId;
	}

	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}

  public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

public String getNoInters() {
    return noInters;
  }

  public void setNoInters(String noInters) {
    this.noInters = noInters;
  }
	
}
