package com.hww.file.webservice.controller;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hww.base.util.DateUtils;
import com.hww.base.util.SnowFlake;
import com.hww.file.common.Constants;
import com.hww.file.common.utils.TransfMediaTool;
import com.hww.file.common.vo.FileInfoVo;
import com.hww.file.common.vo.FileSetVo;
import com.hww.file.common.vo.FileVideoVo;
import com.hww.file.webservice.handler.AsyncHandler;
import com.hww.file.webservice.handler.VideoMsg;
import com.hww.file.webservice.service.FileVideoService;

@RestController
@RequestMapping("/file/video")
public class FileVideoController {

	private static final Log log = LogFactory.getLog(FileImgController.class);

	@Value("${video.location}")
	private String videoLocation;
	@Value("${video.urlpath}")
	private String videoPath;
	@Value("${ffmpegPath}")
	private String ffmpegPath;
	@Value("${video.cover.location}")
	private String coverPath;

	@Autowired
	private FileVideoService fileVideoService;

	private static TransfMediaTool transfMediaTool = new TransfMediaTool();
	// 文件最大500M
	private static long upload_maxsize = 800 * 1024 * 1024;
	// 文件允许格式
	private static String[] allowFiles = { ".rar", ".doc", ".docx", ".zip", ".pdf", ".txt", ".swf", ".xlsx", ".gif",
			".png", ".jpg", ".jpeg", ".bmp", ".xls", ".mp4", ".flv", ".ppt", ".avi", ".mpg", ".wmv", ".3gp", ".mov",
			".asf", ".asx", ".vob", ".wmv9", ".rm", ".rmvb" };
	// 允许转码的视频格式（ffmpeg）
	private static String[] allowFLV = { ".avi", ".mpg", ".wmv", ".3gp", ".mov", ".asf", ".asx", ".vob" };
	// 允许的视频转码格式(mencoder)
	private static String[] allowAVI = { ".wmv9", ".rm", ".rmvb" };

	@RequestMapping(value="upload.do",method = {RequestMethod.GET, RequestMethod.POST})
	@CrossOrigin(origins = "*", maxAge = 360000000)
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request, @RequestParam("videofiles") MultipartFile[] myfile)
			throws IOException {

		List<Long> ids = new ArrayList<>();
		List<String> urls = new ArrayList<>();
		if (myfile.length == 0) {
			return null;
		} else {
			//是否是视频集
			boolean isVideoSet = false;
			List<FileVideoVo> fileVideoVos = new LinkedList<FileVideoVo>(); 
			List<FileInfoVo> fileInfoVos = new LinkedList<FileInfoVo>();
			if(myfile.length>1) {
				isVideoSet = true;
			}
			for (MultipartFile multipartFile : myfile) {
				String contentType = multipartFile.getContentType();
				String originalFilename = multipartFile.getOriginalFilename();
				log.info("file name:" + originalFilename);
				log.info("file type:" + contentType);
				// 文件前缀
				String fileNamePrefix = originalFilename.substring(0, originalFilename.lastIndexOf("."));
				// 文件后缀|类型
				String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
				//数据库最长60
				if(fileNamePrefix.length()>(60-fileType.length())) {
					fileNamePrefix = fileNamePrefix.substring(0, 60-fileType.length());
					originalFilename = fileNamePrefix+fileType;
				}
				boolean bflag = false;
				// 判断文件大小
				if (multipartFile.getSize() <= upload_maxsize) {
					bflag = true;
					// 文件类型判断
					if (this.checkFileType(originalFilename)) {
						bflag = true;
					} else {
						bflag = false;
						log.error("文件类型不允许");
					}
				} else {
					bflag = false;
					log.error("文件大小超范围");
				}
				
				if (bflag) {

					Date date = new Date();

					String dateStr = DateUtils.getDateStr(date);
					String fileRelativePath = videoLocation + dateStr;
					String fileSaveName = UUID.randomUUID().toString();

					// 先创建目录
					File file = new File(fileRelativePath);

					if (!file.exists()) {
						file.mkdirs();
					}
					log.debug(file.getAbsolutePath());
					if(!file.isAbsolute()) {
						file = new File(file.getAbsolutePath());
					}
					// 后创建文件
					File target = new File(file, fileSaveName + fileType);
					
					//SnowFlake flake = new SnowFlake(1, 1);
					//Long id = flake.nextId();
					
					try {
						/*VideoMsg videoMsg = new VideoMsg(multipartFile,target,id);
						videoMsg.setFfmpegPath(ffmpegPath);
						videoMsg.setCoverPath(coverPath);
						AsyncHandler handler = AsyncHandler.getInstance();
						handler.sendMsg(videoMsg);*/
						
						// 转码Avi
						// 源文件保存路径
						String aviSourcePath = target.getAbsolutePath();
						// 设置转换为AVI格式后文件的保存路径
						String aviPath = fileRelativePath + fileSaveName + ".avi";
						if (this.checkAVIType(fileType)) {
							// 获取配置的转换工具（mencoder.exe）的存放路径
							String mencoderPath = request.getServletContext().getRealPath("/tools/mencoder.exe");
							log.debug(mencoderPath);
							aviPath = transfMediaTool.processAVI(mencoderPath, aviSourcePath, aviPath);
						}
						if (aviPath != null) {
							// 转码Flv
							if (this.checkMediaType(fileType)) {
								// 设置转换为flv格式后文件的保存路径
								String codcFilePath = fileRelativePath + fileSaveName +".flv";
								try {
									// 获取配置的转换工具（ffmpeg.exe）的存放路径
									String ffmpegPath = request.getServletContext().getRealPath("/tools/ffmpeg.exe");
									log.debug(ffmpegPath);
									transfMediaTool.processFLV(ffmpegPath, aviPath, codcFilePath);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					} catch (IllegalStateException e) {
						e.printStackTrace();
					}

					
					
					// 1保存fileinfo
					FileInfoVo fileInfoVo = new FileInfoVo();
					//fileInfoVo.setFileId(id);
					fileInfoVo.setFileDesc("");

					
					
					fileInfoVo.setFileRelativePath(videoPath+dateStr);

					fileInfoVo.setFileSaveName(fileSaveName);

					fileInfoVo.setFileExtensionName(fileType);

					fileInfoVo.setFileName(originalFilename);

					Long size = multipartFile.getSize();

					fileInfoVo.setFileSize(new Double(size));

					// 2保存filevideo
					FileVideoVo fileVideoVo = new FileVideoVo();

					//fileVideoVo.setFileId(id);
					//如果是视频集,批量添加
					Long id = null;
					if(isVideoSet) {
						//Long setId = flake.nextId();
						//fileVideoVo.setSetId(setId);
						fileVideoVos.add(fileVideoVo);
						fileInfoVos.add(fileInfoVo);
					}else {
						// 3保存
						id = fileVideoService.saveVideo(fileVideoVo, fileInfoVo);
					}
					try {
						multipartFile.transferTo(target);
						
//						VideoMsg videoMsg = new VideoMsg(multipartFile,target,id);
//						videoMsg.setFfmpegPath(ffmpegPath);
//						videoMsg.setCoverPath(coverPath);
//						AsyncHandler handler = AsyncHandler.getInstance();
//						handler.sendMsg(videoMsg);
					} catch (Exception e) {
						e.printStackTrace();
					}
					// 4返回路径
					if(id!=null){
						ids.add(id);
					}
					String url = videoPath + dateStr + "/" + fileInfoVo.getFileSaveName()
							+ fileInfoVo.getFileExtensionName();
					urls.add(url);
				}
			}
			if(isVideoSet) {
				//SnowFlake flake = new SnowFlake(1, 1);
				//Long setId = flake.nextId();
				FileSetVo fileSetVo = new FileSetVo();
				//fileSetVo.setSetId(setId);
				fileSetVo.setModelId(Constants.MODEL_VIDEO);
				fileSetVo.setTotal(fileVideoVos.size());
				
				fileVideoService.saveVideos(fileVideoVos, fileInfoVos, fileSetVo);
			}
		}
		String idjoin = StringUtils.join(ids, ",");
		String urljoin = StringUtils.join(urls, ",");

		Map<String, Object> map = new HashMap<>();

		map.put("ids", idjoin);

		map.put("urls", urljoin);

		return map;
	}

	/**
	 * 根据文件id返回可访问的url
	 * 
	 * @param fileIds
	 * @return url1,url2,url3
	 */
	@RequestMapping(value="getUrlByVideoIds.do",method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String getUrlByVideoId(HttpServletRequest request, String videoIds) {
		String urls = null;
		if (StringUtils.isNotEmpty(videoIds)) {
			try {
				urls = fileVideoService.getUrlByVideoId(request.getContextPath(), videoIds);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return urls;

	}

	/**
	 * 文件类型判断
	 *
	 * @param fileName
	 * @return
	 */
	private boolean checkFileType(String fileName) {
		Iterator<String> type = Arrays.asList(allowFiles).iterator();
		while (type.hasNext()) {
			String ext = type.next();
			if (fileName.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 视频类型判断(AVI)
	 *
	 * @param fileName
	 * @return
	 */
	private boolean checkAVIType(String fileEnd) {
		Iterator<String> type = Arrays.asList(allowAVI).iterator();
		while (type.hasNext()) {
			String ext = type.next();
			if (fileEnd.equals(ext)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 文件大小，返回kb.mb
	 * 
	 * @return
	 */
	private String getSize(long fileLength) {
		String size = "";
		DecimalFormat df = new DecimalFormat("#.00");
		if (fileLength < 1024) {
			size = df.format((double) fileLength) + "BT";
		} else if (fileLength < 1048576) {
			size = df.format((double) fileLength / 1024) + "KB";
		} else if (fileLength < 1073741824) {
			size = df.format((double) fileLength / 1048576) + "MB";
		} else {
			size = df.format((double) fileLength / 1073741824) + "GB";
		}

		return size;

	}

	/**
	 * 视频类型判断(flv)
	 *
	 * @param fileName
	 * @return
	 */
	private boolean checkMediaType(String fileEnd) {
		Iterator<String> type = Arrays.asList(allowFLV).iterator();
		while (type.hasNext()) {
			String ext = type.next();
			if (fileEnd.equals(ext)) {
				return true;
			}
		}
		return false;
	}

}
