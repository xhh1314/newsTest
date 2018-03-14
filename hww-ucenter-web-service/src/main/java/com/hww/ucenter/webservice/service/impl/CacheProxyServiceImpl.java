package com.hww.ucenter.webservice.service.impl;

import com.hww.cms.api.CmsFeignClient;
import com.hww.cms.common.dto.HCmsQueryDto;
import com.hww.cms.common.vo.CmsContentDataVo;
import com.hww.sns.api.SnsFeignClient;
import com.hww.sns.common.vo.SnsPostVo;
import com.hww.sns.common.vo.SnsTopicVo;
import com.hww.ucenter.webservice.service.CacheProxyService;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
@Service("ucenter_cacheProxyService")
@Transactional
public class CacheProxyServiceImpl implements CacheProxyService  {
	  private static final Logger log = LoggerFactory.getLogger(CacheProxyServiceImpl.class);
	@Autowired SnsFeignClient snsFeignClient;
    @Autowired CmsFeignClient cmsFeignClient;


    	//@Scheduled(fixedDelay = 1000*20)
	  // @CacheEvict(value = "ucenter_loadSnsPostVoCacheFirst",allEntries=true)
	   public void loadSnsPostVoCacheFirst_delete_from_cache() {
		   log.debug("----loadSnsPostVoCacheFirst_delete_from_cache------------");
	   }
    //@Cacheable(value = "ucenter_loadSnsPostVoCacheFirst",key="'loadSnsPostVoCacheFirst_'+#postId")
    @Override
    public SnsPostVo loadSnsPostVoCacheFirst(Long postId) {
    	log.debug("------------loadSnsPostVoCacheFirst-----");
    	SnsPostVo snsPost=snsFeignClient.postDetail(postId);//
    	return snsPost;
    }
    
    //@Scheduled(fixedDelay = 1000*20)
	 //  @CacheEvict(value = "ucenter_loadSnsTopicVoCacheFirst",allEntries=true)
	   public void loadSnsTopicVoCacheFirst_delete_from_cache() {
		   log.debug("----loadSnsTopicVoCacheFirst_delete_from_cache------------");
	   }
    
    //@Override
    //@Cacheable(value = "ucenter_loadSnsTopicVoCacheFirst",key="'loadSnsTopicVoCacheFirst_'+#topicId")
    public SnsTopicVo loadSnsTopicVoCacheFirst(Long topicId) {
    	log.debug("------------loadSnsTopicVoCacheFirst-----");
    	SnsTopicVo subjectSnsTopic=snsFeignClient.topicDetail(topicId);
    	return subjectSnsTopic;
    }
    
    @Scheduled(fixedDelay = 1000*60*10)
	   @CacheEvict(value = "ucenter_loadCmsContentDataVoCacheFirst",allEntries=true)
	   public void loadCmsContentDataVoCacheFirst_delete_from_cache() {
		   log.debug("----loadCmsContentDataVoCacheFirst_delete_from_cache------------");
	   }
    @Override
    @Cacheable(value = "ucenter_loadCmsContentDataVoCacheFirst",key="'loadCmsContentDataVoCacheFirst_'+#subjectContentId+'_'+#currentUserId")
    public CmsContentDataVo loadCmsContentDataVoCacheFirst(Long subjectContentId,Long currentUserId) {
    	log.debug("------------loadCmsContentDataVoCacheFirst-----");
    	CmsContentDataVo cmsContentDataVo=cmsFeignClient.loadNewsNoPostAndTopicFeginApi(new HCmsQueryDto().setMemberId(currentUserId).setContentId(subjectContentId).setPageNo(null).setPageSize(null));
    	return cmsContentDataVo;
    }
}
