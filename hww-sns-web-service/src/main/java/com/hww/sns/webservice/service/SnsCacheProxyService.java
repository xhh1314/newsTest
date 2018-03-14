package com.hww.sns.webservice.service;

import org.springframework.cache.annotation.Cacheable;

import com.hww.cms.common.dto.HCmsQueryDto;
import com.hww.cms.common.vo.CmsContentDataVo;

public interface SnsCacheProxyService {

	 CmsContentDataVo loadNewsNoPostAndTopicFeginApi(HCmsQueryDto cmsQueryDto);
	 
}
