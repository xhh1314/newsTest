package com.hww.cms.webservice.service;

import com.hww.base.util.R;
import com.hww.sns.common.dto.SnsTopicToCmsDto;

public interface CmsToContentService {

	R snsToCmsContent(SnsTopicToCmsDto tocmsdto) ;
	
	
}
