//package com.hww.cms.webservice.feign;
//
//import com.hww.base.util.R;
//import com.hww.els.common.HSearchDto;
//import com.hww.els.common.entity.ESContent;
//import com.hww.els.common.entity.ESRecommendHis;
//import java.util.List;
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@FeignClient(name = "hww-els-web-service-consumer")
//public interface ElsFeignClient {
//	
//	@RequestMapping(value = "/es/search/searchIdsIncontentFeginApi.do")
//	List<Long> searchIdsIncontentFeginApi(@RequestBody HSearchDto searchDto);
//	
//	@RequestMapping(value = "/es/search/recommhis/searchRecommHisFeginApi.do")
//	List<Long> searchRecommHis(@RequestBody HSearchDto searchDto);
//	
//	@RequestMapping(value = "/es/search/recommhis/createRecommHisFeginApi.do")
//	R createRecommHis(@RequestBody List<ESRecommendHis> recommendHisList);	
//	
//	@RequestMapping(value = "/es/search/recommhis/searchRecommHisByPageFeginApi.do")
//	List<ESRecommendHis> searchRecommHisByPage(@RequestBody HSearchDto searchDto);
//	
//	@RequestMapping(value = "/es/search/searchNearTopicCountFeginApi.do")
//	Long searchNearTopicCountFeginApi(@RequestBody HSearchDto searchDto);
//	
//	
//	@RequestMapping(value = "/es/search/createContentFeginApi.do")
//	R createContentFeginApi(@RequestBody ESContent esContent);	
//
//}
