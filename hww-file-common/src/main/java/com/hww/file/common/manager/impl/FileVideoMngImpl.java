package com.hww.file.common.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.file.common.dao.FileVideoDao;
import com.hww.file.common.entity.FileVideo;
import com.hww.file.common.manager.FileVideoMng;

@Service("fileVideoMng")
@Transactional
public class FileVideoMngImpl
	extends
		BaseEntityMngImpl<Long, FileVideo, FileVideoDao>
	implements
		FileVideoMng
{

	FileVideoDao fileVideoDao;

	public FileVideoDao getFileVideoDao() {
		return fileVideoDao;
	}
	@Autowired
	public void setFileVideoDao(FileVideoDao fileVideoDao) {
		super.setEntityDao(fileVideoDao);
		this.fileVideoDao = fileVideoDao;
	}
	
}
