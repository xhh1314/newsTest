package com.hww.cms.common.dto;

import com.hww.base.common.dto.AbsBaseDto;

public class CmsRecommConfigDto extends AbsBaseDto<Long> {

    private Long columnId; //栏目id
    private String columnName; //栏目名称
    private Integer recommNum; //推荐数量
    private Integer type; //1 栏目 2 专题 3 新鲜事

    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Integer getRecommNum() {
        return recommNum;
    }

    public void setRecommNum(Integer recommNum) {
        this.recommNum = recommNum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
