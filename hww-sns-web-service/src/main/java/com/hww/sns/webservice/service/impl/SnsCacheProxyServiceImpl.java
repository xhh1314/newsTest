package com.hww.sns.webservice.service.impl;

import com.hww.cms.api.CmsFeignClient;
import com.hww.cms.common.dto.HCmsQueryDto;
import com.hww.cms.common.vo.CmsContentDataVo;
import com.hww.sns.webservice.service.SnsCacheProxyService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;


@Service("snsCacheProxyService")
@Transactional
public class SnsCacheProxyServiceImpl implements SnsCacheProxyService {
	private static final Log log = LogFactory.getLog(SnsCacheProxyServiceImpl.class);

  @Autowired
  CmsFeignClient cmsFeignClient;


  
  	   @Scheduled(fixedDelay = 1000*60*3)
	   @CacheEvict(value = "sns_loadNewsNoPostAndTopicFeginApi",allEntries=true)
	   public void loadNewsNoPostAndTopicFeginApi_delete_from_cache() {
	   }
  	@Cacheable(value = "sns_loadNewsNoPostAndTopicFeginApi",key="'loadNewsNoPostAndTopicFeginApi_'+#cmsQueryDto.tocacheKey()")
    @Override
    public CmsContentDataVo loadNewsNoPostAndTopicFeginApi(HCmsQueryDto cmsQueryDto) {
	  CmsContentDataVo cmsContentDataVo=cmsFeignClient.loadNewsNoPostAndTopicFeginApi(cmsQueryDto);
    	return cmsContentDataVo;
    }
}
