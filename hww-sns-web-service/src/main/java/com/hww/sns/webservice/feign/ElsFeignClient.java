//package com.hww.sns.webservice.feign;
//
//import com.hww.base.util.R;
//import com.hww.els.common.HSearchDto;
//import com.hww.els.common.entity.ESContent;
//import java.util.List;
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//@FeignClient(name = "hww-els-web-service-consumer")
//public interface ElsFeignClient {
//
//	
//	@RequestMapping(value = "/es/search/searchIdsIncontentFeginApi.do")
//	List<Long> searchIdsIncontentFeginApi(@RequestBody HSearchDto searchDto);
//	@RequestMapping(value = "/es/search/searchNearTopicIdsFeginApi.do")
//	List<Long> searchNearTopicIdsFeginApi(@RequestBody HSearchDto searchDto);
//	
//	
//	
//	@RequestMapping(value = "/es/search/createContentFeginApi.do")
//	R createContentFeginApi(@RequestBody ESContent esContent);	
//	
//	@RequestMapping(value = "/es/search/updateContentFeginApi.do")
//	R updateContentFeginApi(@RequestBody  ESContent esContent);
//}
