//package com.hww.cms.webadmin.fegin;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.hww.file.common.dto.FileInfoDto;
//import com.hww.file.common.vo.FileImgVo;
//
///**
// * cms调用file系统
// * 
// * @author hewei url = "http://localhost:7810"
// */
//@FeignClient(name = "hww-file-web-service-consumer")
//public interface FileFeignClient {
//
//
//
//	@RequestMapping("/file/img/getUrlByFileIds.do")
//	@ResponseBody
//	public String getUrlByFileIds(@RequestParam("fileIds") String fileIds);
//
//	@RequestMapping("/file/img/picSet.do")
//	@ResponseBody
//	public String picSet(@RequestParam("imgIds") String imgIds);
//
//	@RequestMapping(value = "/file/img/getUrlsByIds.do")
//	@ResponseBody
//	String getUrlsByIds(@RequestParam("imgIds") String imgIds);
//
//	@RequestMapping("/file/video/getUrlByVideoIds.do")
//	@ResponseBody
//	public String getUrlByVideoId(@RequestParam("videoIds") String videoIds);
//
//
//
//	@RequestMapping(value = "/file/img/getUrlByFileIdFeiApi.do")
//	@ResponseBody
//	public String getUrlByFileIdFeiApi(@RequestParam("fileIds") String fileIds);
//	
//	@RequestMapping(value = "/file/info/undateInfoFeginApi.do")
//	public FileInfoDto undateInfoFeginApi(@RequestBody FileInfoDto dto);
//	
//
////	@RequestMapping("/file/img/upload.do")
////	@ResponseBody
////	public String save(@RequestParam("myfile") MultipartFile[] myfile) throws IOException;
////	/**
////	 * 图集上传
////	 *
////	 * @param request
////	 * @param myfile
////	 * @return setId:图集id
////	 * @throws IOException
////	 */
////	@RequestMapping("/file/img/uploadPicCollect.do")
////	@ResponseBody
////	public Map<String, Object> savePicCollect(HttpServletRequest request,
////			@RequestParam("myfile") MultipartFile[] myfile);
//
////	@RequestMapping("/file/img/kindEditorUpload.do")
////	public String uploadKindEditor(@RequestParam("callBackPath") String callBackPath,
////			@RequestBody MultipartFile myfile);
//	
////	/**
////	 * 根据图集id获取所有图片
////	 * 
////	 * @param setId
////	 * @return
////	 */
////	@RequestMapping("/file/img/getPicCollect.do")
////	@ResponseBody
////	public String getPicCollectBySetId(@RequestParam("setId") String setId);
//}
