package com.hww.app.webservice.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CacheClearFixedDelayConfig {
	
	 private static final Log log = LogFactory.getLog(CacheClearFixedDelayConfig.class);
	 
//	 @Scheduled(fixedDelay = 1000*60*30)//缓存30分钟，执行清理操作
//	 public void clearIn30M() {
//		 listNationalTelephoneCode_delete_from_cache();
//	 }
//	 
//	 @Scheduled(fixedDelay = 1000*60*10)//缓存10分钟，执行清理操作
//	 public void clearIn10M() {
//		
//		 cityList_delete_from_cache(); //系统部署完成后，迁移到30M中去
//		 countryList_delete_from_cache(); //系统部署完成后，迁移到30M中去
//		 provinceList_delete_from_cache(); //系统部署完成后，迁移到30M中去
//		 
//		 
//	 }
//	 @Scheduled(fixedDelay = 1000*60*5)//缓存5分钟，执行清理操作
//	 public void clearIn5M() {
//		 searchHotWord_delete_from_cache();
//		 loadAllEnableColumns_delete_from_cache() ;
//	 }
//	 
//	@Scheduled(fixedDelay = 1000*60*3)//缓存3分钟，执行清理操作
//	 public void clearIn3M() {
//		loadCateIdsByColumnId_delete_from_cache();
//	 }
//	 
//	@Scheduled(fixedDelay = 1000*60*2)//缓存2分钟，执行清理操作
//	 public void clearIn2M() {
//		
//	 }
//	
//	@Scheduled(fixedDelay = 1000*60*1)//缓存1分钟，执行清理操作
//	 public void clearIn1M() {
//		searchHistory_delete_from_cache();
//	 }
//	
//	   @Scheduled(fixedDelay = 1000*60*5)
//	  @CacheEvict(value = "app_loadAllEnableColumns",allEntries=true)
//	   public void loadAllEnableColumns_delete_from_cache() {
//		   log.debug("----loadAllEnableColumns_delete_from_cache------------");
//	   }
//	   @Scheduled(fixedDelay = 1000*60*3)
//	   @CacheEvict(value = "app_loadCateIdsByColumnId",allEntries=true)
//	   public void loadCateIdsByColumnId_delete_from_cache() {
//		   log.debug("----loadCateIdsByColumnId_delete_from_cache------------");
//	   }
//	   @Scheduled(fixedDelay = 1000*60*1)
//	   @CacheEvict(value = "app_searchHistory",allEntries=true)
//	   public void searchHistory_delete_from_cache() {
//		   log.debug("----searchHistory_delete_from_cache------------");
//	   }
//	   @Scheduled(fixedDelay = 1000*60*5)
//	   @CacheEvict(value = "app_searchHotWord",allEntries=true)
//	   public void searchHotWord_delete_from_cache() {
//		   log.debug("----searchHotWord_delete_from_cache------------");
//	   }
	   
//	   @Scheduled(fixedDelay = 1000*60*10)
//	   @CacheEvict(value = "app_provinceList",allEntries=true)
//	   public void provinceList_delete_from_cache() {
//		   log.debug("----provinceList_delete_from_cache------------");
//	   }
//	   @Scheduled(fixedDelay = 1000*60*10)
//	   @CacheEvict(value = "app_countryList",allEntries=true)
//	   public void countryList_delete_from_cache() {
//		   log.debug("----countryList_delete_from_cache------------");
//	   }
//	   @Scheduled(fixedDelay = 1000*60*10)
//	   @CacheEvict(value = "app_cityList",allEntries=true)
//	   public void cityList_delete_from_cache() {
//		   log.debug("----cityList_delete_from_cache------------");
//	   }
//	   @Scheduled(fixedDelay = 1000*60*30)
//	   @CacheEvict(value = "app_listNationalTelephoneCode",allEntries=true)
//	   public void listNationalTelephoneCode_delete_from_cache() {
//		   log.debug("----listNationalTelephoneCode_delete_from_cache------------");
//	   }
	   
}
