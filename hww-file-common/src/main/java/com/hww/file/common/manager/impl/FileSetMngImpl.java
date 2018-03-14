package com.hww.file.common.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.file.common.dao.FileSetDao;
import com.hww.file.common.entity.FileSet;
import com.hww.file.common.manager.FileSetMng;

@Service("fileSetMng")
@Transactional
public class FileSetMngImpl extends BaseEntityMngImpl<Long, FileSet, FileSetDao> implements FileSetMng{
	
	FileSetDao fileSetDao;

	public FileSetDao getFileSetDao() {
		return fileSetDao;
	}

	@Autowired
	public void setFileSetDao(FileSetDao fileSetDao) {
		super.setEntityDao(fileSetDao);
		this.fileSetDao = fileSetDao;
	}
	
	
	
}
