package com.hww.app.common.vo;

import java.util.HashMap;
import java.util.List;

/**
 * @author XiaoBG
 * 用于返回APP首页信息
 */
public class AppHomeCategoryVo {

    private List<HashMap<String, Object>> unSubscribeColumns;

    private List<AppCategoryVo> indexColumns;

    public List<HashMap<String, Object>> getUnSubscribeColumns() {
        return unSubscribeColumns;
    }

    public void setUnSubscribeColumns(List<HashMap<String, Object>> unSubscribeColumns) {
        this.unSubscribeColumns = unSubscribeColumns;
    }

    public List<AppCategoryVo> getIndexColumns() {
        return indexColumns;
    }

    public void setIndexColumns(List<AppCategoryVo> indexColumns) {
        this.indexColumns = indexColumns;
    }
}
