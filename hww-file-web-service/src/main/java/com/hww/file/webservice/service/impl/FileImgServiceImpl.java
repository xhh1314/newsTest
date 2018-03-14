package com.hww.file.webservice.service.impl;

import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.BeanUtils;
import com.hww.base.util.SnowFlake;
import com.hww.file.common.entity.FileImg;
import com.hww.file.common.entity.FileInfo;
import com.hww.file.common.entity.FileSet;
import com.hww.file.common.manager.FileImgMng;
import com.hww.file.common.manager.FileInfoMng;
import com.hww.file.common.manager.FileSetMng;
import com.hww.file.common.vo.FileImgVo;
import com.hww.file.common.vo.FileInfoVo;
import com.hww.file.common.vo.FileSetVo;
import com.hww.file.webservice.service.FileImgService;
import com.hww.file.webservice.service.FileSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 13705 on 2017/11/23.
 */
@Service("fileImgService")
public class FileImgServiceImpl implements FileImgService {

    @Autowired
    private FileImgMng fileImgMng;
    
    @Autowired
    private FileInfoMng fileInfoMng;
    
    @Autowired
    private FileSetService fileSetService;
    
    @Autowired
	FileSetMng fileSetMng;

	@Override
	@Transactional
	public Long saveImg(FileImgVo fileImgVo, FileInfoVo fileInfoVo) {
		
		FileImg fileImg = new FileImg();
		
		FileInfo fileInfo=new FileInfo();
		
		try {
			BeanUtils.copyProperties(fileImg, fileImgVo);
			BeanUtils.copyProperties(fileInfo, fileInfoVo);
		} catch (InvocationTargetException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fileImg.setCreateTime(new Timestamp(System.currentTimeMillis()));
		fileImgMng.save(fileImg);
		fileInfo.setFileId(fileImg.getFileId());
		fileInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		fileInfoMng.save(fileInfo);
		return fileImg.getFileId();
		
	}
	
	@Override
	@Transactional
	public String saveImgs(List<FileImgVo> fileImgVos, List<FileInfoVo> fileInfoVos) {
		List<Long> fileIds = new ArrayList<Long>();
		if(fileImgVos!=null&&fileImgVos.size()>0) {
			List<FileImg> fileImgs = BeanMapper.mapList(fileImgVos, FileImg.class);
			for(int i=0;i<fileImgs.size();i++) {
				FileImg img = fileImgs.get(i);
				img.setCreateTime(new Timestamp(System.currentTimeMillis()));
				fileImgMng.save(img);
				fileIds.add(img.getFileId());
			}
			//fileImgMng.save(fileImgs);
		}
		
		if(fileInfoVos!=null&&fileInfoVos.size()>0) {
			List<FileInfo> fileInfos = BeanMapper.mapList(fileInfoVos, FileInfo.class);
			for(int i=0;i<fileInfos.size();i++) {
				FileInfo fileInfo = fileInfos.get(i);
				fileInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
				fileInfo.setFileId(fileIds.get(i));
				fileInfoMng.save(fileInfo);
			}
			//fileInfoMng.save(fileInfos);
		}
		String idss ="";
		for(Long id:fileIds){
			if(idss.length()!=0){
        		idss+=","+id.toString();
        	}else{
        		idss+=id.toString();
        	}
		}
		return idss;
	}
	
	/**
	 * 保存图集
	 * @param fileImgVo
	 * @param fileInfoVo
	 * @param fileSetVo 
	 * @return 图集id
	 */
	@Override
	@Transactional
	public Long saveImgs(List<FileImgVo> fileImgVos, List<FileInfoVo> fileInfoVos,FileSetVo fileSetVo) {
		
		List<FileImg> fileImgs = null;
		
		List<FileInfo> fileInfos= null;
		
		fileImgs = BeanMapper.mapList(fileImgVos, FileImg.class);
		fileInfos = BeanMapper.mapList(fileInfoVos, FileInfo.class);
		Long setId = fileSetVo.getSetId();
		/*if(setId==null) {
			SnowFlake flake = new SnowFlake(1, 1);
			setId = flake.nextId();
			fileSetVo.setSetId(setId);
		}*/
		if(fileImgs!=null&&fileImgs.size()>0) {
			for(FileImg entity :fileImgs) {
				//entity.setSetId(setId); //图集id
				entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
			}
			fileImgMng.save(fileImgs);
		}
		
		if(fileInfos!=null&&fileInfos.size()>0) {
			for(FileInfo entity :fileInfos) {
				entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
			}
			fileInfoMng.save(fileInfos);
		}
		
		fileSetService.saveOrUpdateFileSet(fileSetVo);
		return setId;
	}


	@Override
	public String getUrlByFileIds(String contentPath,String fileIds) {
		
		StringBuffer result = new StringBuffer();
		
		String[] file_ids = null;
		if(fileIds.indexOf(",")>0) {
			file_ids = fileIds.split(",");
		}else {
			file_ids = new String[] {fileIds};
		}
		if(file_ids.length>0) {
			List<Long> fileids = new LinkedList<Long>();
			for(int i=0;i<file_ids.length;i++) {
				//Long fileid = Long.getLong(file_ids[i]);
			  String fileid = file_ids[i];
			  fileid=fileid.replaceAll("\"", "").trim();
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
	public List<FileImgVo> getPicCollectBySetId(String setId) {
		//图片标题，url，摘要
		Long setid = Long.valueOf(setId);
		Finder hql = Finder.create(Finder.FROM);
		hql.append(FileImg.class.getName());
		hql.append("where setId=:setId").setParam("setId", setid);
		List<FileImg> entitys = (List<FileImg>) fileImgMng.find(hql);
		List<FileImgVo> imgVos = new LinkedList<FileImgVo>();
		if(entitys!=null&&entitys.size()>0) {
			List<Long> fileIds = new LinkedList<Long>();
			for(FileImg img :entitys) {
				fileIds.add(img.getFileId());
			}
			Finder fileinfoHql = Finder.create(Finder.FROM);
			fileinfoHql.append(FileInfo.class.getName());
			fileinfoHql.append("where fileId in (:fileIds)").setParamList("fileIds", fileIds);
			List<FileInfo> fileInfos = (List<FileInfo>) fileInfoMng.find(fileinfoHql);
			if(fileInfos!=null&&fileInfos.size()>0) {
				for(FileImg img :entitys) {
					for(FileInfo fi:fileInfos) {
						if(fi.getFileId().equals(img.getFileId())) {
							img.setFileInfo(fi);
							break;
						}
					}
				}
			}
			imgVos = BeanMapper.mapList(entitys, FileImgVo.class);
		}
		return imgVos;
	}

	@Override
	public List<FileInfoVo> getUrlsByids(List<Long> ids) {
		List<FileInfoVo> fiVos = new LinkedList<FileInfoVo>();
		if(ids!=null&&ids.size()>0) {
			Finder hql = Finder.create(Finder.FROM);
			hql.append(FileInfo.class.getName());
			hql.append("where fileId in(:ids)").setParamList("ids", ids);
			List<FileInfo> fileInfos = (List<FileInfo>) fileInfoMng.find(hql);
			fiVos = BeanMapper.mapList(fileInfos, FileInfoVo.class);
		}
		
		return fiVos;
	}

	@Override
	@Transactional
	public void picSet(List<Long> ids) {
		if(ids!=null&&ids.size()>0) {
			//SnowFlake flake = new SnowFlake(1, 1);
			//Long setId = flake.nextId();
			//新建图集对象
			FileSet fs = new FileSet();
			//fs.setSetId(setId);
			fs.setTotal(ids.size());
			fs.setCreateTime(new Timestamp(System.currentTimeMillis()));
			fileSetMng.save(fs);
			//更新图集id
			Finder updateHql = Finder.create("update");
			updateHql.append(FileImg.class.getName());
			updateHql.append("set setId=:setId").setParam("setId", fs.getId());
			updateHql.append("where fileId in(:ids)").setParamList("ids", ids);
			fileImgMng.update(updateHql);
		}
	}

}
