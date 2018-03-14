//package com.hww.cms.webservice.config;
//
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//import com.google.common.cache.Cache;
//import com.google.common.cache.CacheBuilder;
//
//public class GuavaCache {
//
//	  final static Cache<String, List<Map<String, Object>>> homeCache = CacheBuilder.newBuilder()  
//		        .initialCapacity(20)  //设置cache的初始大小
//		        .maximumSize(2*1000)
//		        .concurrencyLevel(200)  //设置并发数为200
//		       // .expireAfterAccess(60*10, TimeUnit.SECONDS)
//		        .expireAfterWrite(60*3, TimeUnit.SECONDS)  //设置cache中的数据在写入之后的存活时间
//		        .build();
//	  
//	public static List<Map<String, Object>> getHomeData(String key) {
//		return homeCache.getIfPresent(key);
//	}
//	public static void putHomeData(String key,List<Map<String, Object>> homeData) {
//		 homeCache.put(key, homeData);
//	}
//
//}
