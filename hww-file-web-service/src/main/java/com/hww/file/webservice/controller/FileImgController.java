package com.hww.file.webservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hww.base.util.DateUtils;
import com.hww.base.util.R;
import com.hww.base.util.SnowFlake;
import com.hww.file.common.vo.FileImgVo;
import com.hww.file.common.vo.FileInfoVo;
import com.hww.file.webservice.FileConstants;
import com.hww.file.webservice.service.FileImgService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/file/img")
public class FileImgController {

    private static final Log log = LogFactory.getLog(FileImgController.class);

    @Value("${img.location}")
    private String imgLocation;
    @Value("${server_ip}")
    private String serverIp;
    @Value("${img.urlpath}")
    private String imgPath;
    @Autowired
    private FileImgService fileImgService;

    @RequestMapping(value = "upload.do", method = {RequestMethod.GET, RequestMethod.POST})
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public Map<String, Object> save(@RequestParam MultipartFile[] myfile) throws IOException {

        List<Long> ids = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        if (myfile.length == 0) {
            log.debug("file is empty");
            return map;
        } else {
            //String imgSuffixPattern = "gif|jpg|jpeg|png|GIF|JPG|PNG|bmp";
            for (MultipartFile multipartFile : myfile) {
                String contentType = multipartFile.getContentType();
                log.debug(contentType);
                if (StringUtils.endsWithAny(contentType, "image/jpg", "image/gif", "image/jpeg", "image/png")) {

                    InputStream inputStream = multipartFile.getInputStream();

                    String originalFilename = multipartFile.getOriginalFilename();
                    Date date = new Date();

                    String dateStr = DateUtils.getDateStr(date);

                    FileImgVo fileImgVo = new FileImgVo();

                    FileInfoVo fileInfoVo = new FileInfoVo();

                    fileInfoVo.setFileDesc("");

                    String fileRelativePath = dateStr;

                    fileInfoVo.setFileRelativePath(imgPath+fileRelativePath);

                    fileInfoVo.setFileSaveName(UUID.randomUUID().toString());

                    fileInfoVo.setFileExtensionName(originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length()));

                    fileInfoVo.setFileName(originalFilename);

                    Long size = multipartFile.getSize();

                    fileInfoVo.setFileSize(new Double(size));

                    BufferedImage bufferedImage = ImageIO.read(inputStream);

                    if (bufferedImage != null) {
                        fileImgVo.setImgFileHight(bufferedImage.getHeight());
                        fileImgVo.setImgFileWidth(bufferedImage.getWidth());
                    }

                    File file = new File(imgLocation + fileRelativePath);

                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    File target = new File(file, fileInfoVo.getFileSaveName() + fileInfoVo.getFileExtensionName());

                    String format = contentType.substring(contentType.lastIndexOf("/") + 1, contentType.length());

                    try {
                        ImageIO.write(bufferedImage, format, target);

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    //SnowFlake flake = new SnowFlake(1, 1);
                    //Long id = flake.nextId();

                    //TODO 前台传过来
                    fileImgVo.setSiteId(1);

                   // fileImgVo.setFileId(id);

                   // fileInfoVo.setFileId(id);

                    Long id = fileImgService.saveImg(fileImgVo, fileInfoVo);

                    ids.add(id);

                    String url = imgPath+fileRelativePath + "/" + fileInfoVo.getFileSaveName() + fileInfoVo.getFileExtensionName();
                    log.debug(url);

                    urls.add(url);
                }

            }


        }
        String idss="";
        for(Long i:ids){
        	if(idss.length()!=0){
        		idss+=","+i.toString();
        	}else{
        		idss+=i.toString();
        	}
        }
        //String idjoin = StringUtils.join(ids, ",");
        String urljoin = StringUtils.join(urls, ",");

        map.put("ids",idss);
        map.put("urls", urljoin);

        return map;
    }

//    /**
//     * @param urlPath     下载路径
//     * @return 返回下载文件
//     */
//    @RequestMapping("download.do")
//    @CrossOrigin(origins = "*", maxAge = 3600)
//    @ResponseBody
//    public File downloadFile(String urlPath) {
//        List<Long> ids = new ArrayList<>();
//        List<String> urls = new ArrayList<>();
//        Map<String, Object> map = new HashMap<>();
//        try {
//            // 统一资源W
//            URL url = new URL(urlPath);
//            // 连接类的父类，抽象类
//            URLConnection urlConnection = url.openConnection();
//            // http的连接类
//            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
//            // 设定请求的方法，默认是GET
//            httpURLConnection.setRequestMethod("POST");
//            // 设置字符编码
//            httpURLConnection.setRequestProperty("Charset", "UTF-8");
//            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//
//            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
//            httpURLConnection.connect();
//
//            // 文件大小
//            int fileLength = httpURLConnection.getContentLength();
//
//            // 文件名
////            String filePathUrl = httpURLConnection.getURL().getFile();
////            String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);
//
//            URLConnection con = url.openConnection();
//
//            Date date = new Date();
//
//            String dateStr = DateUtils.getDateStr(date);
//
//            FileImgVo fileImgVo = new FileImgVo();
//
//            FileInfoVo fileInfoVo = new FileInfoVo();
//
//            fileInfoVo.setFileDesc("");
//
//            String fileRelativePath = "img/" + dateStr;
//
//            BufferedInputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
//
//            BufferedImage bufferedImage = ImageIO.read(inputStream);
//
//            fileImgVo.setImgFileHight(bufferedImage.getHeight());
//            fileImgVo.setImgFileWidth(bufferedImage.getWidth());
//
//            File file = new File(fileLocation + fileRelativePath);
//
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//
//            File target = new File(file, fileInfoVo.getFileSaveName() + fileInfoVo.getFileExtensionName());
//
//            try {
//                ImageIO.write(bufferedImage, format, target);
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        return map;
//    }

    /**
     * 根据文件id返回可访问的url
     */
    @RequestMapping(value = "getUrlByFileIds.do", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R getUrlByFileId(HttpServletRequest request, String fileIds) {
        if (StringUtils.isNotEmpty(fileIds)) {
            try {
                String urls = fileImgService.getUrlByFileIds(request.getContextPath(), fileIds);
                return R.ok().put("urls", urls);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return R.ok().put("urls", "");
    }
    /**
     * id 返回 url
     * @param request
     * @param fileIds
     * @return
     */
    @RequestMapping(value = "getUrlByFileIdFeiApi.do", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String getUrlByFileIdFeiApi(HttpServletRequest request, String fileIds) {
        if (StringUtils.isNotEmpty(fileIds)) {
            try {
                String urls = fileImgService.getUrlByFileIds(request.getContextPath(), fileIds);
                return urls;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       return "";
    }

    /**
     * 服务调用
     *
     * @return
     * @author lyb
     * @email 674142624@qq.com
     * @date 2017年11月28日 下午2:54:20
     * @version v0.1
     */
    @RequestMapping(value = "getUrl.do/{fileIds}", method = {RequestMethod.GET, RequestMethod.POST})
    public String getUrl(HttpServletRequest request, @PathVariable String fileIds) {

        String urls = null;
        if (StringUtils.isNotEmpty(fileIds)) {
            try {
                urls = fileImgService.getUrlByFileIds(request.getContextPath(), fileIds);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    /**
     * 图集上传
     *
     * @param request
     * @param myfile
     * @return setId:图集id
     * @throws IOException
     */
    @RequestMapping(value = "uploadPicCollect.do", method = {RequestMethod.GET, RequestMethod.POST})
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public Map<String, Object> savePicCollect(HttpServletRequest request,
                                              @RequestParam MultipartFile[] myfile) {

        String desc = request.getParameter("desc");//图片描述
        log.info("desc:" + desc);
        List<FileImgVo> fileImgVos = new LinkedList<FileImgVo>();
        List<FileInfoVo> fileInfoVos = new LinkedList<FileInfoVo>();
        List<Long> idss = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        //返回结果
        Map<String, Object> map = new HashMap<>();
        try {
            if (myfile.length == 0) {
                log.debug("myfile is empty");
                return map;
            } else {
                //String imgSuffixPattern = "gif|jpg|jpeg|png|GIF|JPG|PNG|bmp";
                for (MultipartFile multipartFile : myfile) {
                    String contentType = multipartFile.getContentType();
                    log.debug(contentType);
                    if (StringUtils.endsWithAny(contentType, "image/jpg", "image/gif", "image/jpeg", "image/png")) {

                        String originalFilename = multipartFile.getOriginalFilename();
                        Date date = new Date();

                        String dateStr = DateUtils.getDateStr(date);
                        FileInfoVo fileInfoVo = new FileInfoVo();

                        fileInfoVo.setFileDesc("");

                        String fileRelativePath = dateStr;

                        fileInfoVo.setFileRelativePath(imgPath+fileRelativePath);

                        fileInfoVo.setFileSaveName(UUID.randomUUID().toString());

                        fileInfoVo.setFileExtensionName(originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length()));

                        fileInfoVo.setFileName(originalFilename);

                        Long size = multipartFile.getSize();

                        fileInfoVo.setFileSize(new Double(size));
                        File file = new File(imgLocation + fileRelativePath);

                        if (!file.exists()) {
                            file.mkdirs();
                        }

                        File target = new File(file, fileInfoVo.getFileSaveName() + fileInfoVo.getFileExtensionName());

                        String format = contentType.substring(contentType.lastIndexOf("/") + 1, contentType.length());

                        InputStream inputStream = multipartFile.getInputStream();
                        BufferedImage bufferedImage = ImageIO.read(inputStream);
                        try {
                            ImageIO.write(bufferedImage, format, target);

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        //SnowFlake flake = new SnowFlake(1, 1);
                        //Long id = flake.nextId();

                        FileImgVo fileImgVo = new FileImgVo();
                        if (bufferedImage != null) {
                            fileImgVo.setImgFileHight(bufferedImage.getHeight());
                            fileImgVo.setImgFileWidth(bufferedImage.getWidth());
                        }
                        //TODO 前台传过来
                        fileImgVo.setSiteId(1);


                        //fileImgVo.setFileId(id);

                        //fileInfoVo.setFileId(id);
                        fileInfoVo.setFileDesc(desc); //图片描述

                        //放入集合
                        fileImgVos.add(fileImgVo);
                        fileInfoVos.add(fileInfoVo);

                        //idss.add(id);
                        String url = imgPath+fileRelativePath + "/" + fileInfoVo.getFileSaveName() + fileInfoVo.getFileExtensionName();
                        log.debug(url);
                        urls.add(url);
                    }
                }
                String ids = fileImgService.saveImgs(fileImgVos, fileInfoVos);
                //String idjoin = StringUtils.join(ids, ",");
                String urljoin = StringUtils.join(urls, ",");
                log.info("ids:" + ids);
                log.info("urls:" + urljoin);
                map.put("ids", ids);
                map.put("urls", urljoin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 根据图集id获取所有图片
     *
     * @return 只返回必要信息
     */
    @RequestMapping(value="getPicCollect.do",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String getPicCollectBySetId(String setId) {
        log.info("query setId:" + setId);
        String result = "";
        if (setId != null) {
            try {
                List<FileImgVo> list = fileImgService.getPicCollectBySetId(setId);
                if (list != null && list.size() > 0) {
                    JSONArray imgs = new JSONArray();
                    for (FileImgVo imgVo : list) {
                        FileInfoVo fiVo = imgVo.getFileInfo();
                        if (fiVo != null) {
                            String imgUrl = fiVo.getFileRelativePath() + "/" + fiVo.getFileSaveName() + fiVo.getFileExtensionName();
                            JSONObject obj = new JSONObject();
                            obj.put("fileId", fiVo.getFileId());
                            obj.put("imgUrl", imgUrl);
                            obj.put("fileDesc", fiVo.getFileDesc());
                            imgs.add(obj);
                        }
                    }
                    if (imgs.size() > 0) {
                        result = imgs.toJSONString();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        log.info("query result:" + result);
        return result;
    }

    /**
     * 图片归集
     */
    @RequestMapping(value="picSet.do",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String picSet(String imgIds) {
        log.info("ids:" + imgIds);
        String result = "success";
        try {
            String[] idsStr = imgIds.split(",");
            if (idsStr.length > 0) {
                List<Long> ids = new LinkedList<Long>();
                for (String str : idsStr) {
                    if (StringUtils.isNotEmpty(str)) {
                        ids.add(Long.valueOf(str));
                    }
                }
                fileImgService.picSet(ids);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "error";
        }
        log.info("operate result:" + result);
        return result;
    }

    /**
     * 根据图片ids获取访问urls和desc
     */
    @RequestMapping(value = "getUrlsByIds.do", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String getUrlsByIds(String imgIds) {
        log.info("ids:" + imgIds);
        String result = "";
        if (StringUtils.isEmpty(imgIds)) {
            result = "param imgIds is empty";
            return result;
        }
        try {
            String[] idsStr = imgIds.split(",");
            if (idsStr.length > 0) {
                List<Long> ids = new LinkedList<Long>();
                for (String str : idsStr) {
                    if (StringUtils.isNotEmpty(str)) {
                        ids.add(Long.valueOf(str));
                    }
                }
                List<FileInfoVo> fileInfos = fileImgService.getUrlsByids(ids);
                if (fileInfos != null && fileInfos.size() > 0) {
                    JSONArray imgs = new JSONArray();
                    for (FileInfoVo fiVo : fileInfos) {
                        String imgUrl = fiVo.getFileRelativePath() + "/"+ fiVo.getFileSaveName() + fiVo.getFileExtensionName();
                        JSONObject obj = new JSONObject();
                        obj.put("fileId", fiVo.getFileId());
                        obj.put("imgUrl", imgUrl);
                        obj.put("fileDesc", fiVo.getFileDesc());
                        imgs.add(obj);
                    }
                    if (imgs.size() > 0) {
                        result = imgs.toJSONString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("operate result:" + result);
        return result;
    }

    @RequestMapping(value = "/kindEditorUpload.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String uploadKindEditor(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam("callBackPath") String callBackPath,
                                   @RequestBody MultipartFile myfile) {
        List<FileImgVo> fileImgVos = new LinkedList<FileImgVo>();
        List<FileInfoVo> fileInfoVos = new LinkedList<FileInfoVo>();
        List<Long> ids = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        //返回结果
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
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                //SnowFlake flake = new SnowFlake(1, 1);
                //Long id = flake.nextId();

                FileImgVo fileImgVo = new FileImgVo();
                if (bufferedImage != null) {
                    fileImgVo.setImgFileHight(bufferedImage.getHeight());
                    fileImgVo.setImgFileWidth(bufferedImage.getWidth());
                }
                //TODO 前台传过来
                fileImgVo.setSiteId(1);


                //fileImgVo.setFileId(id);

                //fileInfoVo.setFileId(id);

                //放入集合
                fileImgVos.add(fileImgVo);
                fileInfoVos.add(fileInfoVo);

                //ids.add(id);
                url =imgPath+ fileRelativePath + "/" + fileInfoVo.getFileSaveName() + fileInfoVo.getFileExtensionName();
                log.debug(url);
                urls.add(url);
            }
            fileImgService.saveImgs(fileImgVos, fileInfoVos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url_redirect = "";
        try {
            // 同域时直接返回json，跨域需redirect
            url_redirect = callBackPath + "?error=0&url=http://"+serverIp+"/" + url;
        } catch (Exception e) {
            e.printStackTrace();
            url_redirect = callBackPath + "?error=1&message=" + "错误信息";
        }
        try {
            response.sendRedirect(url_redirect);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


}
