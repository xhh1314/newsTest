package com.hww.file.api;

//import com.hww.file.common.entity.FileInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
//import java.util.List;

import com.hww.file.common.dto.FileInfoDto;

@FeignClient(name = "hww-file-web-service-consumer")
public interface FileFeignClient {

	
	@RequestMapping(value = "/file/img/getUrlsByIds.do")
    String getUrlAndDescJsonsByIds(@RequestParam("imgIds")String imgIds);
	
    @RequestMapping(value = "/file/img/getUrl.do/{fileIds}")
     String getUrlByFileId(@PathVariable("fileIds") String fileIds);
	
	
	@RequestMapping(value = "/file/video/getUrlByVideoIds.do")
	@ResponseBody
    String getVedioUrlsByids(@RequestParam("videoIds")String videoIds);
	
	
	//================================
	
	@RequestMapping("/file/img/getUrlByFileIds.do")
	@ResponseBody
	public String getUrlByFileIds(@RequestParam("fileIds") String fileIds);

	@RequestMapping("/file/img/picSet.do")
	@ResponseBody
	public String picSet(@RequestParam("imgIds") String imgIds);

	@RequestMapping(value = "/file/img/getUrlsByIds.do")
	@ResponseBody
	String getUrlsByIds(@RequestParam("imgIds") String imgIds);

	@RequestMapping("/file/video/getUrlByVideoIds.do")
	@ResponseBody
	public String getUrlByVideoId(@RequestParam("videoIds") String videoIds);



	@RequestMapping(value = "/file/img/getUrlByFileIdFeiApi.do")
	@ResponseBody
	public String getUrlByFileIdFeiApi(@RequestParam("fileIds") String fileIds);
	
	@RequestMapping(value = "/file/info/undateInfoFeginApi.do")
	public FileInfoDto undateInfoFeginApi(@RequestBody FileInfoDto dto);
	
}