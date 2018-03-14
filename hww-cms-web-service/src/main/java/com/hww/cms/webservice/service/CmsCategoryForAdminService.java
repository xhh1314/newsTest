package com.hww.cms.webservice.service;

import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.vo.CmsCategoryVo;
import com.hww.cms.common.vo.query.QueryContentVo;

import java.util.List;

public interface CmsCategoryForAdminService {

	List<CmsCategory> getRetrievingFullTree();
}
