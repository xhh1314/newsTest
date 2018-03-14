//package com.hww.sns.webadmin.fegin;
//
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@FeignClient(name = "hww-file-web-service-consumer")
//public interface FileInfoFeignClient {
//
//    @RequestMapping(value = "/file/img/getUrl.do/{fileIds}")
//     String getUrlByFileId(@PathVariable("fileIds") String fileIds);
//
//	
////	@RequestMapping("/file/img/getUrlByFileIds.do")
////	@ResponseBody
////	public String getUrlByFileId2(@RequestParam("fileIds")String fileIds);
//}