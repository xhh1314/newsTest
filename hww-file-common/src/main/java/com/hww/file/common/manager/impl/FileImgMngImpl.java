package com.hww.file.common.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.file.common.dao.FileImgDao;
import com.hww.file.common.entity.FileImg;
import com.hww.file.common.manager.FileImgMng;
@Service("fileImgMng")
@Transactional
public class FileImgMngImpl extends BaseEntityMngImpl<Long, FileImg, FileImgDao> implements FileImgMng{
	
	FileImgDao fileImgDao;

	public FileImgDao getFileImgDao() {
		return fileImgDao;
	}
	
	 @Autowired
	public void setFileImgDao(FileImgDao fileImgDao) {
		super.setEntityDao(fileImgDao);
		this.fileImgDao = fileImgDao;
	}
	 
	 @Override
	public FileImg save(FileImg entity) {
		return super.save(entity);
	}
	
}
