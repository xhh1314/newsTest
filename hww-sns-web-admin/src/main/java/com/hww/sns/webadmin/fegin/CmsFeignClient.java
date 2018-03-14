//package com.hww.sns.webadmin.fegin;
//
//import com.hww.base.common.page.Pagination;
//import com.hww.base.util.R;
//import com.hww.cms.common.dto.CmsContentDto;
//import com.hww.sns.common.dto.SnsTopicToCmsDto;
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//@FeignClient(name = "hww-cms-web-service-consumer")
//public interface CmsFeignClient {
//
//    @RequestMapping(value = "/cms/tocontent/snsToCmsContentFeginApi.do", method = RequestMethod.POST)
//    R toCmsContent(@RequestBody SnsTopicToCmsDto snsTopicToCmsDto);
//
//    
//    @RequestMapping("/cms/content/update_auditstatu.do")
//	public String updateAuditstatus(@RequestBody CmsContentDto cmsContentDto);
//	
//	@RequestMapping("/cms/content/list.do")
//	public Pagination list(@RequestBody(required=false) CmsContentDto cmsContentDto);
//
//}
