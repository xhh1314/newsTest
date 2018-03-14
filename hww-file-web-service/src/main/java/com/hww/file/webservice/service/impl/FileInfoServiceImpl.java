package com.hww.file.webservice.service.impl;

import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.BeanUtils;
import com.hww.file.common.dao.FileInfoDao;
import com.hww.file.common.dto.FileInfoDto;
import com.hww.file.common.entity.FileInfo;
import com.hww.file.common.manager.FileInfoMng;
import com.hww.file.common.vo.FileInfoVo;
import com.hww.file.webservice.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by 13705 on 2017/11/23.
 */
@Service("fileInfoService")
public class FileInfoServiceImpl implements FileInfoService {

    @Autowired
    private FileInfoMng fileInfoMng;

    @Override
    public FileInfoDto findFileInfoById(Long fileId) {
        FileInfo entity = fileInfoMng.find(fileId);
        if (entity == null) {
            return null;
        }
        FileInfoDto dto = BeanMapper.map(entity, FileInfoDto.class);
        //文件访问路径
        String fileVisitUrl = entity.getFileRelativePath()+"/"+entity.getFileSaveName()+entity.getFileExtensionName();
        dto.setFileVisitUrl(fileVisitUrl);
        return dto;
    }

    @Override
    public List<FileInfo> queryFileInfoListByIds(String ids) {
        return fileInfoMng.queryFileInfoListByIds(ids);
    }

	@Override
	@Transactional
	public FileInfoDto update(FileInfoDto dto) {
		if(dto.getFileId()!=null) {
			FileInfo entity=fileInfoMng.find(dto.getFileId());
			if(dto.getFileName()!=null) {
				entity.setFileName(dto.getFileName());
			}
			if(dto.getFileDesc()!=null) {
				entity.setFileDesc(dto.getFileDesc());
			}
			if(dto.getFileSize()!=null) {
				entity.setFileSize(dto.getFileSize());
			}
			entity.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
			
			return BeanMapper.map(fileInfoMng.update(entity), FileInfoDto.class);
		}
		return null;
	}
	
	
	@Override
	@Transactional
	public Long saveInfo(FileInfoVo fileInfoVo) {
		FileInfo fileInfo=new FileInfo();
		
		try {
			BeanUtils.copyProperties(fileInfo, fileInfoVo);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		fileInfoMng.save(fileInfo);
		if(fileInfo.getFileId()!=null){
			return fileInfo.getFileId();
		}else{
			return null;
		}
	}
}
