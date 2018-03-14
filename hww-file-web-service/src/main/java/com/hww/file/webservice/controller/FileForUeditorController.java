package com.hww.file.webservice.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hww.base.util.DateUtils;
import com.hww.file.common.dto.UeditorState;
import com.hww.file.common.vo.FileImgVo;
import com.hww.file.common.vo.FileInfoVo;
import com.hww.file.webservice.service.FileImgService;
@Controller
@RequestMapping("file/ueditor")
public class FileForUeditorController {

	@Value("${img.location}")
	private String imgLocation;
	@Value("${server_ip}")
	private String serverIp;
	@Value("${img.urlpath}")
	private String imgPath;
	@Autowired
	private FileImgService fileImgService;

	@RequestMapping(value = { "/upload",
			"/upload/" }, method = {RequestMethod.POST,RequestMethod.OPTIONS}, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "*", maxAge = 360000000)
	public @ResponseBody Object uploadAttachment(HttpServletRequest request,
			@RequestParam(name = "upfile", required = false) MultipartFile myfile, String module) {
		String filePath = "";
		String fileName = "";
		List<FileImgVo> fileImgVos = new LinkedList<FileImgVo>();
        List<FileInfoVo> fileInfoVos = new LinkedList<FileInfoVo>();
        List<Long> ids = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        String url = "";
        try {
            String contentType = myfile.getContentType();
            if (StringUtils.endsWithAny(contentType, "image/jpg", "image/gif", "image/jpeg", "image/png")) {

                String originalFilename = myfile.getOriginalFilename();
                Date date = new Date();

                String dateStr = DateUtils.getDateStr(date);
                FileInfoVo fileInfoVo = new FileInfoVo();

                fileInfoVo.setFileDesc("");

                String fileRelativePath = dateStr;

                fileInfoVo.setFileRelativePath(imgPath+fileRelativePath);

                fileInfoVo.setFileSaveName(UUID.randomUUID().toString());

                fileInfoVo.setFileExtensionName(originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length()));

                fileInfoVo.setFileName(originalFilename);

                Long size = myfile.getSize();

                fileInfoVo.setFileSize(new Double(size));
                File file = new File(imgLocation + fileRelativePath);

                if (!file.exists()) {
                    file.mkdirs();
                }

                File target = new File(file, fileInfoVo.getFileSaveName() + fileInfoVo.getFileExtensionName());

                String format = contentType.substring(contentType.lastIndexOf("/") + 1, contentType.length());

                InputStream inputStream = myfile.getInputStream();
                BufferedImage bufferedImage = ImageIO.read(inputStream);
                try {
                    ImageIO.write(bufferedImage, format, target);

                } catch (IOException e) {
                    e.printStackTrace();
                }



                FileImgVo fileImgVo = new FileImgVo();
                if (bufferedImage != null) {
                    fileImgVo.setImgFileHight(bufferedImage.getHeight());
                    fileImgVo.setImgFileWidth(bufferedImage.getWidth());
                }
                //TODO 前台传过来
                fileImgVo.setSiteId(1);

                //放入集合
                fileImgVos.add(fileImgVo);
                fileInfoVos.add(fileInfoVo);

                //ids.add(id);
                url =imgPath+ fileRelativePath + "/" + fileInfoVo.getFileSaveName() + fileInfoVo.getFileExtensionName();
                filePath=url;
                urls.add(url);
            }
            fileImgService.saveImgs(fileImgVos, fileInfoVos);
        } catch (Exception e) {
            e.printStackTrace();
            return new UeditorState("上传失败").setTitle("上传失败：【"+e.getMessage()+"】");
        }
    
        return new UeditorState( "SUCCESS", filePath ).setOriginal(fileName).setTitle("");
    }


}
