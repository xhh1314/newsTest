package com.hww.app.common.dto;

import com.hww.base.common.dto.AbsBaseDto;


/**
 * @author XiaoBG
 * @查询用户订阅的频道实体类
 * @param userId,imei,categoryId
 */
public class AppQueryCategoryDto extends AbsBaseDto<Long> {

    /*private static final Log log = LogFactory.getLog(AppQueryCategoryDto.class);*/
    
    private Long userColumnId;
    private Long categoryId; //栏目id
    private Long userId; //用户id
    private String imei; //设备imei号
    private Integer sort; //分类编号
    private String cloumIds;

    
    
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

    public Long getColumnId() {
        return categoryId;
    }

    public void setColumnId(Long categoryId) {
        this.categoryId = categoryId;
    }

	public Long getUserColumnId() {
		return userColumnId;
	}

	public void setUserColumnId(Long userColumnId) {
		this.userColumnId = userColumnId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getCloumIds() {
		return cloumIds;
	}

	public void setCloumIds(String cloumIds) {
		this.cloumIds = cloumIds;
	}
}
