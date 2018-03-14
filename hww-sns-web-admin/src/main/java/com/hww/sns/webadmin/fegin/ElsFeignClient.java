//package com.hww.sns.webadmin.fegin;
//
//import com.hww.base.util.R;
//import com.hww.els.common.HSearchDto;
//import com.hww.els.common.dto.ESContentDto;
//import com.hww.els.common.entity.ESContent;
//
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@FeignClient(name = "hww-els-web-service-consumer")
//public interface ElsFeignClient {
//
//
//    @RequestMapping(value = "/es/search/createContentFeginApi.do")
//    R createContentFeginApi(@RequestBody ESContent esContent);
//
//    @RequestMapping(value = "/es/search/updateContentFykFeginApi.do")
//    R updateContentFeginApi(@RequestBody ESContentDto esContentDto);
//
//    @RequestMapping(value = "/es/search/searchNewsFeginApi.do")
//    public List<Map<String, Object>> searchNewsFeginApi(@RequestBody HSearchDto searchDto);
//    
//    @RequestMapping(value = "/es/search/searchContentIdFeginApi.do")
//    public Map<String, Object> searchContentIdFeginApi(@RequestParam("contentId") Long contentId);
//
//}
