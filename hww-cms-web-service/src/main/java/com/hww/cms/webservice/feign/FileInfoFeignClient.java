//package com.hww.cms.webservice.feign;
//
//import com.hww.file.common.entity.FileInfo;
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import java.util.List;
//
//@FeignClient(name = "hww-file-web-service-consumer")
//public interface FileInfoFeignClient {
//
//	
//	@RequestMapping(value = "/file/img/getUrlsByIds.do")
//    String getUrlAndDescJsonsByIds(@RequestParam("imgIds")String imgIds);
//	
//    @RequestMapping(value = "/file/img/getUrl.do/{fileIds}")
//     String getUrlByFileId(@PathVariable("fileIds") String fileIds);
//    
//	
//	@RequestMapping(value = "/file/video/getUrlByVideoIds.do")
//	@ResponseBody
//    String getVedioUrlsByids(@RequestParam("videoIds")String videoIds);
//	
//	
//}