package com.hww.cms.webadmin.service;

import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.vo.CmsCategoryVo;

import java.util.List;

public interface CmsCategoryService {


    List<CmsCategory> querySpecialList();
    
    boolean deleteCategory(CmsCategoryVo vo);
}
