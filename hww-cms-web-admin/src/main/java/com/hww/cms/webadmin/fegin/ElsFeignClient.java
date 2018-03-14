//package com.hww.cms.webadmin.fegin;
//
//import com.hww.base.util.R;
//import com.hww.els.common.dto.ESContentDto;
//import com.hww.els.common.entity.ESContent;
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@FeignClient(name = "hww-els-web-service-consumer")
//public interface ElsFeignClient {
//
////	@RequestMapping(value = "/es/search/news.do")
////	ElasticSearchPage<New> news(@RequestBody SearchNewsVo vo);
////
////	@RequestMapping(value = "/es/search/videos.do")
////	ElasticSearchPage<New> searchVideos(@RequestBody SearchNewsVo vo);
////
////	@RequestMapping(value = "/es/search/topics.do")
////	ElasticSearchPage<New> searchTopics(@RequestBody SearchNewsVo vo);
////
////	@RequestMapping(value = "/es/search/members.do")
////	ElasticSearchPage<Member> members(SearchNewsVo vo);
//
//    @RequestMapping(value = "/es/search/createContentFeginApi.do")
//    R createContentFeginApi(@RequestBody ESContent esContent);
//
//    @RequestMapping(value = "/es/search/updateContentFykFeginApi.do")
//    R updateContentFeginApi(@RequestBody ESContentDto esContentDto);
//
//    @RequestMapping(value = "/es/search/deleteContentFeginApi.do/{id}")
//    R deleteContentFeginApi(@PathVariable(value = "id") Long id);
//
//
//}
