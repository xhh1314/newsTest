package com.hww.cms.common.vo.query;

import java.io.Serializable;

/**
 * Created by kongkenan on 2017/11/18.
 */
public class QueryOtherContentVo implements Serializable {

    private Integer columnId; //栏目id
    private Integer pageNo; //页码
    private Integer pageSize; //页量
    private Long userId; //用户id
    private String imei; //imei号

    public Integer getColumnId() {
        return columnId;
    }

    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
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
}
