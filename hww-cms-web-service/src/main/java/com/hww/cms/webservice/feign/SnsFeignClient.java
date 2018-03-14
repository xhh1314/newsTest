//package com.hww.cms.webservice.feign;
//
//import com.hww.sns.common.dto.HBaseSnsQueryDto;
//import com.hww.sns.common.dto.HBaseSnsQueryFeginApiDto;
//import com.hww.sns.common.vo.SnsPostVo;
//import com.hww.sns.common.vo.SnsTopicVo;
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//
//@FeignClient(name = "hww-sns-web-service-consumer")
//public interface SnsFeignClient{
//
//	@RequestMapping(value = "/sns/post/newsPostListFeginApi.do")
//	List<SnsPostVo> newsPostList(@RequestBody HBaseSnsQueryDto snsqueryDto);
//	
//	@RequestMapping(value = "/sns/topic/newTopicsFeginApi.do")
//	List<SnsTopicVo> newsTpoicList(@RequestBody HBaseSnsQueryDto snsqueryDto);
//
//	
//	@RequestMapping(value = "/sns/topic/loadTopicByIdsFeginApi.do")
//	List<SnsTopicVo> loadTopicByIdsFeginApi(@RequestBody HBaseSnsQueryFeginApiDto queryFeginApiDto);
//	
//	
//	@RequestMapping(value = "/sns/post/loadCountForNewsFeginApi.do",method=RequestMethod.POST)
//	 public Integer loadCountForNewsFeginApi(@RequestParam("newsId")Long newsId);
//	
//    @RequestMapping(value = "/sns/topic/topicCountsByNewIdFeginApi.do/{newId}",method=RequestMethod.POST)
//    public Integer topicCountsByNewIdFeginApi(@PathVariable("newId") Long newId) ;
//	
//	
//    
//}