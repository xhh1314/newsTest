package com.hww.cms.webadmin.service;

import java.util.List;

import com.hww.cms.common.vo.CmsContentTypeVo;

public interface CmsContentTypeService {
	
	List<CmsContentTypeVo> getAllContentType(CmsContentTypeVo searchVo);
	

}
