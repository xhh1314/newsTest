//package com.hww.cms.webadmin.webservice.feign;
//
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.hww.cms.webadmin.webservice.feign.fallback.ELSFeignClientFallback;
//
///**
// * cms调用ELS系统
// * @author hewei
// *
// */
//@FeignClient(name = "hww-els-web-service-consumer",fallback=ELSFeignClientFallback.class)
//public interface ELSFeignClient {
//
//	/**
//	 * 新闻审核通过后放入
//	 * @param news
//	 * @return
//	 */
//	@RequestMapping(value = "/els/save/news.do")
//	public int news(@RequestBody String news);
//}
