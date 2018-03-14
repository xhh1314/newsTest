//package com.hww.ucenter.webservice.feign;
//
//import com.hww.sns.common.vo.SnsPostVo;
//import com.hww.sns.common.vo.SnsTopicVo;
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//
//@FeignClient(name = "hww-sns-web-service-consumer")
//public interface SnsFeignClient {
//
//    @RequestMapping(value = "/sns/topic/loadUserTopicCountFeginApi.do/{memberId}/{topicType}", method = RequestMethod.POST)
//    Integer findCountById(@PathVariable("memberId") Long memberId, @PathVariable("topicType") Integer topicType);
//
//    @RequestMapping(value = "/sns/post/loadPostByIdFeginApi.do", method = RequestMethod.POST)
//    SnsPostVo postDetail(@RequestParam("postId") Long postId);
//
//    @RequestMapping(value = "/sns/topic/loadTopicByIdFeginApi.do", method = RequestMethod.POST)
//    SnsTopicVo topicDetail(@RequestParam("topicId") Long topicId);
//
//    @RequestMapping(value = "/sns/topic/myTopicsFeginApi.do/{memberId}", method = RequestMethod.POST)
//    List<SnsTopicVo> myTopics(@PathVariable("memberId") Long memberId);
//}
