package com.hww.cms.webadmin.service.impl;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hww.cms.common.vo.CmsContentTypeVo;
import com.hww.cms.webadmin.service.CmsContentTypeService;
@Service("cmsContentTypeService")
@Transactional
public class CmsContentTypeServiceImpl implements CmsContentTypeService {

	 
	 
	public List<CmsContentTypeVo> getAllContentType(CmsContentTypeVo searchVo) {
		
		return null;
	}

	

}
