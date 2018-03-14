package com.hww.cms.common.vo.query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 查询新闻栏目VO类
 */
public class QueryColumnVo {

    private static final Log log = LogFactory.getLog(QueryColumnVo.class);

    private Long userId; //用户id
    private String imei; //设备imei号
    private Long columnId; //栏目id
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
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }
}
