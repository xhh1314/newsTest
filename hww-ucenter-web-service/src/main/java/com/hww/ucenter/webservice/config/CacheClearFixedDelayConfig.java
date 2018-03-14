package com.hww.ucenter.webservice.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CacheClearFixedDelayConfig {
	
	 private static final Log log = LogFactory.getLog(CacheClearFixedDelayConfig.class);
	 
//	 @Scheduled(fixedDelay = 1000*60*30)//
//	 public void clearIn30M() {
//		 getUCenterMemberById_delete_from_cache();
//	 }
//	 @Scheduled(fixedDelay = 1000*60*20)//
//	 public void clearIn20M() {
//		 loadUCenterMemberCacheFirst_delete_from_cache();
//	 }
//	 @Scheduled(fixedDelay = 1000*60*10)//
//	 public void clearIn10M() {
//		 loadSnsPostVoCacheFirst_delete_from_cache();
//		 loadSnsTopicVoCacheFirst_delete_from_cache();
//		 loadCmsContentDataVoCacheFirst_delete_from_cache();
//	 }
//	 @Scheduled(fixedDelay = 1000*60*5)//
//	 public void clearIn5M() {
//	
//	 }
//	 
//	@Scheduled(fixedDelay = 1000*60*3)//
//	 public void clearIn3M() {
//		
//	 }
//	 
//	@Scheduled(fixedDelay = 1000*60*2)//
//	 public void clearIn2M() {
//	 }
//	 @Scheduled(fixedDelay = 1000*60*1)//
//	 public void clearIn1M() {
//		isConcern_delete_from_cache();
//	 }
//	 @Scheduled(fixedDelay = 1000*60*1)
//	@CacheEvict(value = "ucenter_isConcern",allEntries=true)
//	   public void isConcern_delete_from_cache() {
//		   log.debug("----isConcern_delete_from_cache------------");
//	   }
//	 @Scheduled(fixedDelay = 1000*60*10)
//	  @CacheEvict(value = "ucenter_loadUCenterMemberCacheFirst",allEntries=true)
//	   public void getUCenterMemberById_delete_from_cache() {
//		   log.debug("----getUCenterMemberById_delete_from_cache------------");
//	   }
//	 @Scheduled(fixedDelay = 1000*60*20)
//	   @CacheEvict(value = "ucenter_loadUCenterMemberCacheFirst",allEntries=true)
//	   public void loadUCenterMemberCacheFirst_delete_from_cache() {
//		   log.debug("----loadUCenterMemberCacheFirst_delete_from_cache------------");
//	   }
//	 @Scheduled(fixedDelay = 1000*60*10)
//	   @CacheEvict(value = "ucenter_loadSnsPostVoCacheFirst",allEntries=true)
//	   public void loadSnsPostVoCacheFirst_delete_from_cache() {
//		   log.debug("----loadSnsPostVoCacheFirst_delete_from_cache------------");
//	   }
//	 @Scheduled(fixedDelay = 1000*60*10)
//	   @CacheEvict(value = "ucenter_loadSnsTopicVoCacheFirst",allEntries=true)
//	   public void loadSnsTopicVoCacheFirst_delete_from_cache() {
//		   log.debug("----loadSnsTopicVoCacheFirst_delete_from_cache------------");
//	   }
//	 @Scheduled(fixedDelay = 1000*60*10)
//	   @CacheEvict(value = "ucenter_loadCmsContentDataVoCacheFirst",allEntries=true)
//	   public void loadCmsContentDataVoCacheFirst_delete_from_cache() {
//		   log.debug("----loadCmsContentDataVoCacheFirst_delete_from_cache------------");
//	   }
}
