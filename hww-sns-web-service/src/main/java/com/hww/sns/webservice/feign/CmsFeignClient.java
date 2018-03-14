//package com.hww.sns.webservice.feign;
//
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.hww.cms.common.dto.HCmsQueryDto;
//import com.hww.cms.common.vo.CmsContentDataVo;
//
//
//@FeignClient(name = "hww-cms-web-service-consumer")
//public interface CmsFeignClient {
//    
//    @RequestMapping(value = "/cms/content/loadNewsNoPostAndTopicFeginApi.do", method = RequestMethod.POST)
//    CmsContentDataVo loadNewsNoPostAndTopicFeginApi(@RequestBody HCmsQueryDto cmsQueryDto);
//}
