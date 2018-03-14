package com.hww.file.common.manager.impl;

import com.hww.base.common.util.Finder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.file.common.dao.FileInfoDao;
import com.hww.file.common.entity.FileInfo;
import com.hww.file.common.manager.FileInfoMng;

import java.util.List;

@Service("fileInfoMng")
@Transactional
public class FileInfoMngImpl extends BaseEntityMngImpl<Long, FileInfo, FileInfoDao> implements FileInfoMng {

    @Autowired
    FileInfoDao fileInfoDao;
    
    public FileInfoDao getFileInfoDao() {
		return fileInfoDao;
	}

    @Autowired
	public void setFileInfoDao(FileInfoDao fileInfoDao) {
		super.setEntityDao(fileInfoDao);
		this.fileInfoDao = fileInfoDao;
	}


	@Override
    public List<FileInfo> queryFileInfoListByIds(String ids) {
        return fileInfoDao.queryFileInfoListByIds(ids);
    }
	
	@Override
	public FileInfo save(FileInfo entity) {
		return super.save(entity);
	}
}
