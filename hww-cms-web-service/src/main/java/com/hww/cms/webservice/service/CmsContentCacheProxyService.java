package com.hww.cms.webservice.service;

import java.util.List;

import com.hww.app.common.dto.AppRecommConfigDto;
import com.hww.base.util.R;
import com.hww.cms.common.dto.HCmsInstrDto;
import com.hww.cms.common.entity.CmsContent;
import com.hww.cms.common.entity.CmsContentData;
import com.hww.sns.common.dto.HBaseSnsQueryFeginApiDto;
import com.hww.sns.common.vo.SnsTopicVo;

public interface CmsContentCacheProxyService {

	CmsContentData loadCmsContentDataById(Long newsId);
	
	CmsContent loadCmsContentById(Long newsId);
	
	List<Long> loadUninterestedContentIds(Long memberId);

	List<Long> loadCmsCateIdsByColumnId(Long columnId);

	R newsUninterest(HCmsInstrDto uninstrDto);

	List<AppRecommConfigDto> loadAllRecomm();

	List<SnsTopicVo> loadTopicByIds(HBaseSnsQueryFeginApiDto queryFeginApiDto);
	
	

}
