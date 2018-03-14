package com.hww.cms.webservice.config;

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
//		
//	 }
//	 
//	 @Scheduled(fixedDelay = 1000*60*10)//
//	 public void clearIn10M() {
//		
//		 
//		 loadCmsContentDataById_delete_from_cache();
//		 loadCmsContentById_delete_from_cache();
//		 
//		 loadNewsDetailForShare_delete_from_cache();
//	 }
//	 @Scheduled(fixedDelay = 1000*60*5)//缓存5分钟，执行清理操作
//	 public void clearIn5M() {
//		 
//		 loadTopNews_delete_from_cache();
//		 
//		 loadCmsContentBySpecilId_delete_from_cache();
//		 loadFirstLeveSpecialList_delete_from_cache();
//		 loadNotTopLeveSpecialList_delete_from_cache();
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
//	@Scheduled(fixedDelay = 1000*60*1)//
//	 public void clearIn1M() {
//		// loadCmsContentByColumnId_delete_from_cache();
//	 }
	
//		@Scheduled(fixedDelay = 1000*60*10)
//	   @CacheEvict(value = "cms_loadCmsContentDataById",allEntries=true)
//	   public void loadCmsContentDataById_delete_from_cache() {
//		   log.debug("----cms_loadCmsContentDataById------------");
//	   }
//		@Scheduled(fixedDelay = 1000*60*10)
//	   @CacheEvict(value = "cms_loadCmsContentById",allEntries=true)
//	   public void loadCmsContentById_delete_from_cache() {
//		   log.debug("----loadCmsContentById_delete_from_cache------------");
//	   }
//		@Scheduled(fixedDelay = 1000*60*10)
//	   @CacheEvict(value = "cms_loadNewsDetailForShare",allEntries=true)
//	   public void loadNewsDetailForShare_delete_from_cache() {
//		   log.debug("----loadNewsDetailForShare_delete_from_cache------------");
//	   }
	   
//	   @Scheduled(fixedDelay = 1000*60*5)
//	   @CacheEvict(value = "cms_topNews",allEntries=true)
//	   public void loadTopNews_delete_from_cache() {
//		   log.debug("----loadTopNews_delete_from_cache------------");
//	   }
//	   @Scheduled(fixedDelay = 1000*60*5)
//	   @CacheEvict(value = "cms_cmsContentBySpecilId",allEntries=true)
//	   public void loadCmsContentBySpecilId_delete_from_cache() {
//		   log.debug("----loadCmsContentBySpecilId_delete_from_cache------------");
//	   }
//	   @Scheduled(fixedDelay = 1000*60*1)
//	   @CacheEvict(value = "cms_loadCmsContentByColumnId",allEntries=true)
//	   public void loadCmsContentByColumnId_delete_from_cache() {
//		   log.debug("----oadCmsContentByColumnId_delete_from_cache------------");
//	   }
//	   @Scheduled(fixedDelay = 1000*60*5)
//	   @CacheEvict(value = "cms_loadFirstLeveSpecialList",allEntries=true)
//	   public void loadFirstLeveSpecialList_delete_from_cache() {
//		   log.debug("----loadFirstLeveSpecialList_delete_from_cache------------");
//	   }
//	   @CacheEvict(value = "cms_loadNotTopLeveSpecialList")
//	   @Scheduled(fixedDelay = 1000*60*5)//缓存5分钟，执行清理操作
//	   public void loadNotTopLeveSpecialList_delete_from_cache() {
//		   log.debug("----loadNotTopLeveSpecialList_delete_from_cache------------");
//	   }
	   
}
