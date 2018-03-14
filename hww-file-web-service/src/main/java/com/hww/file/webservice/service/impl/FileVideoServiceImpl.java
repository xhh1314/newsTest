package com.hww.file.webservice.service.impl;

import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.BeanUtils;
import com.hww.file.common.entity.FileImg;
import com.hww.file.common.entity.FileInfo;
import com.hww.file.common.entity.FileVideo;
import com.hww.file.common.manager.FileImgMng;
import com.hww.file.common.manager.FileInfoMng;
import com.hww.file.common.manager.FileVideoMng;
import com.hww.file.common.vo.FileImgVo;
import com.hww.file.common.vo.FileInfoVo;
import com.hww.file.common.vo.FileSetVo;
import com.hww.file.common.vo.FileVideoVo;
import com.hww.file.webservice.service.FileSetService;
import com.hww.file.webservice.service.FileVideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;


@Service("fileVideoService")
public class FileVideoServiceImpl implements FileVideoService {

    @Autowired
    private FileVideoMng fileVideoMng;
    
    @Autowired
    private FileInfoMng fileInfoMng;
    
    @Autowired
    private FileImgMng fileImgMng;
    
    @Autowired
    private FileSetService fileSetService;

	@Override
	@Transactional
	public Long saveVideo(FileVideoVo fileVideoVo, FileInfoVo fileInfoVo) {
		
		FileVideo fileVideo = new FileVideo();
		
		FileInfo fileInfo=new FileInfo();
		
		try {
			BeanUtils.copyProperties(fileVideo, fileVideoVo);
			BeanUtils.copyProperties(fileInfo, fileInfoVo);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileVideo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		fileVideoMng.save(fileVideo);
		if(fileVideo.getFileId()!=null){
			fileInfo.setFileId(fileVideo.getFileId());
		}
		fileInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		fileInfoMng.save(fileInfo);
		if(fileInfo.getFileId()!=null){
			return fileInfo.getFileId();
		}else{
			return null;
		}
	}
	
	@Override
	@Transactional
	public void saveVideos(List<FileVideoVo> fileVideoVos, List<FileInfoVo> fileInfoVos, FileSetVo fileSetVo) {
		
		List<FileVideo> fileVideos = new LinkedList<FileVideo>();
		
		List<FileInfo> fileInfos= new LinkedList<FileInfo>();
		
		try {
			BeanUtils.copyProperties(fileVideos, fileVideoVos);
			BeanUtils.copyProperties(fileInfos, fileInfoVos);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(fileVideos.size()>0) {
			for(FileVideo fileVideo:fileVideos) {
				fileVideo.setCreateTime(new Timestamp(System.currentTimeMillis()));
			}
			fileVideoMng.save(fileVideos);
		}
		
		if(fileInfos.size()>0) {
			for(FileInfo fileInfo:fileInfos) {
				fileInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
			}
			fileInfoMng.save(fileInfos);
		}
		
		fileSetVo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		fileSetService.saveOrUpdateFileSet(fileSetVo);
	}

	@Override
	public String getUrlByVideoId(String contentPath,String videoIds) {
		
		StringBuffer result = new StringBuffer();
		
		String[] file_ids = null;
		if(videoIds.indexOf(",")>0) {
			file_ids = videoIds.split(",");
		}else {
			file_ids = new String[] {videoIds};
		}
		if(file_ids.length>0) {
			List<Long> fileids = new LinkedList<Long>();
			for(int i=0;i<file_ids.length;i++) {
				//Long fileid = Long.getLong(file_ids[i]);
			  String fileid = file_ids[i];
			  fileids.add(Long.valueOf(fileid));
			}
			Finder hql = Finder.create(Finder.FROM);
			hql.append(FileInfo.class.getName());
			hql.append("where fileId in (:fileIds)").setParamList("fileIds", fileids);
			List<FileInfo> fileInfos = (List<FileInfo>) fileInfoMng.find(hql);
			if(fileInfos!=null&&fileInfos.size()>0) {
				for(FileInfo fileInfo :fileInfos) {
					result.append(contentPath);
					result.append(fileInfo.getFileRelativePath()+"/");
					result.append(fileInfo.getFileSaveName());
					result.append(fileInfo.getFileExtensionName());
					result.append(",");
				}
			}
		}
		if(result.length()>0) {
			//去掉最后一个逗号
			//result.deleteCharAt(result.toString().length());
		  String sub = result.substring(0, result.length()-1);
		  
		  return sub;
		}
		return result.toString();
	}

	@Override
	public List<FileVideoVo> getVideoCollectBySetId(String setId) {
		//图片标题，url，摘要
		Long setid = Long.valueOf(setId);
		Finder hql = Finder.create(Finder.FROM);
		hql.append(FileVideo.class.getName());
		hql.append("where setId=:setId").setParam("setId", setid);
		List<FileVideo> entitys = (List<FileVideo>) fileVideoMng.find(hql);
		List<FileVideoVo> imgVos = new LinkedList<FileVideoVo>();
		if(entitys!=null&&entitys.size()>0) {
			List<Long> fileIds = new LinkedList<Long>();
			for(FileVideo video :entitys) {
				fileIds.add(video.getFileId());
			}
			Finder fileinfoHql = Finder.create(Finder.FROM);
			fileinfoHql.append(FileInfo.class.getName());
			fileinfoHql.append("where fileId in (:fileIds)").setParamList("fileIds", fileIds);
			List<FileInfo> fileInfos = (List<FileInfo>) fileInfoMng.find(fileinfoHql);
			if(fileInfos!=null&&fileInfos.size()>0) {
				for(FileVideo video :entitys) {
					for(FileInfo fi:fileInfos) {
						if(fi.getFileId().equals(video.getFileId())) {
							video.setFileInfo(fi);
							break;
						}
					}
				}
			}
			imgVos = BeanMapper.mapList(entitys, FileVideoVo.class);
		}
		return imgVos;
	}

	/**
	 * 更新视频信息
	 */
	@Override
	@Transactional
	public void updateVideo(FileVideoVo fileVideoVo) {
		// TODO Auto-generated method stub
		if(fileVideoVo.getFileId()!=null) {
			Finder updateHql = Finder.create("update");
			updateHql.append(FileVideo.class.getName());
			updateHql.append("set");
			//码率Kbps
			if(fileVideoVo.getDataRate()!=null) {
				updateHql.append("dataRate=:dataRate,").setParam("dataRate", fileVideoVo.getDataRate());
			}
			if(fileVideoVo.getLength()!=null) {
				updateHql.append("length=:length").setParam("length", fileVideoVo.getLength());
			}
			if(updateHql.getParams().size()>0) {
				updateHql.append("where fileId=:fileId").setParam("fileId", fileVideoVo.getFileId());
				fileVideoMng.update(updateHql);
			}
			//视频封面
			FileImgVo imgVo = fileVideoVo.getCover();
			FileInfoVo imgInfoVo = imgVo.getFileInfo();
			if(imgVo!=null) {
				if(imgVo.getFileId()==null) {
					//文件id
					imgVo.setFileId(fileVideoVo.getFileId());
				}
				Long imgFileId = imgVo.getFileId();
				FileImg fileImg = fileImgMng.find(imgFileId);
				if(fileImg==null) {
					//插入封面
					fileImg = BeanMapper.map(imgVo, FileImg.class);
					fileImgMng.save(fileImg);
					if(imgInfoVo!=null) {
						FileInfo imgFileInfo = BeanMapper.map(imgInfoVo, FileInfo.class);
						if(imgFileInfo.getFileId()==null) {
							imgFileInfo.setFileId(fileVideoVo.getFileId());
						}
						fileInfoMng.save(imgFileInfo);
					}
					
				}
			}
		}
	}

	

}
