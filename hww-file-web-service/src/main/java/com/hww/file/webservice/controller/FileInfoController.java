package com.hww.file.webservice.controller;

import com.hww.base.util.DateUtils;
import com.hww.base.util.R;
import com.hww.file.common.Constants;
import com.hww.file.common.dto.FileInfoDto;
import com.hww.file.common.entity.FileInfo;
import com.hww.file.common.vo.FileInfoVo;
import com.hww.file.common.vo.FileSetVo;
import com.hww.file.common.vo.FileVideoVo;
import com.hww.file.webservice.service.FileInfoService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

@RestController
@RequestMapping("/file/info")
public class FileInfoController {

	private static final Log log = LogFactory.getLog(FileInfoController.class);
	@Value("${info.location}")
	private String infoLocation;
	@Value("${info.urlpath}")
	private String urlPath;
	@Autowired
	private FileInfoService fileInfoService;
	
	private static String[] allowFiles = { ".apk" };
	private static long upload_maxsize = 800 * 1024 * 1024;
	
	
	
	
	@GetMapping("/{fileId}")
	public FileInfoDto findById(@PathVariable Long fileId) {
		return fileInfoService.findFileInfoById(fileId);
	}

	@RequestMapping(value = "queryFileInfoListByIds.do/{ids}", method = { RequestMethod.GET, RequestMethod.POST })
	public List<FileInfo> queryFileInfoListByIds(@PathVariable("ids") String ids) {
		return fileInfoService.queryFileInfoListByIds(ids);
	}

	@RequestMapping(value = "undateInfoFeginApi.do", method = { RequestMethod.GET, RequestMethod.POST })
	public FileInfoDto undateInfoFeginApi(@RequestBody FileInfoDto dto) {
		return fileInfoService.update(dto);
	}
	@RequestMapping(value = "saveInfoFeginApi.do", method = { RequestMethod.GET, RequestMethod.POST })
	@CrossOrigin(origins = "*", maxAge = 360000000)
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request, @RequestParam MultipartFile[] myfile)
			throws IOException {
		List<Long> ids = new ArrayList<>();
		List<String> urls = new ArrayList<>();
		if (myfile.length == 0) {
			return null;
		} else {
			
			for (MultipartFile multipartFile : myfile) {
				String contentType = multipartFile.getContentType();
				String originalFilename = multipartFile.getOriginalFilename();
				log.info("file name:" + originalFilename);
				log.info("file type:" + contentType);
				// 文件前缀
				String fileNamePrefix = originalFilename.substring(0, originalFilename.lastIndexOf("."));
				// 文件后缀|类型
				String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
				// 数据库最长60
				if (fileNamePrefix.length() > (60 - fileType.length())) {
					fileNamePrefix = fileNamePrefix.substring(0, 60 - fileType.length());
					originalFilename = fileNamePrefix + fileType;
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
					String fileRelativePath = infoLocation + dateStr;
					String fileSaveName = UUID.randomUUID().toString();

					// 先创建目录
					File file = new File(fileRelativePath);

					if (!file.exists()) {
						file.mkdirs();
					}
					log.debug(file.getAbsolutePath());
					if (!file.isAbsolute()) {
						file = new File(file.getAbsolutePath());
					}
					// 后创建文件
					File target = new File(file, fileSaveName + fileType);

					try {
						multipartFile.transferTo(target);
					} catch (Exception e) {
						e.printStackTrace();
					}

					String url = urlPath + dateStr + "/" + fileSaveName + fileType;
					urls.add(url);

				}
			}

		}

		String idjoin = StringUtils.join(ids, ",");
		String urljoin = StringUtils.join(urls, ",");

		Map<String, Object> map = new HashMap<>();

		map.put("ids", idjoin);

		map.put("urls", urljoin);

		return map;

	}

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

}
