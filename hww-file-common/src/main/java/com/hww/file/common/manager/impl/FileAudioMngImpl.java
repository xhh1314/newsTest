package com.hww.file.common.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.file.common.dao.FileAudioDao;
import com.hww.file.common.entity.FileAudio;
import com.hww.file.common.manager.FileAudioMng;
@Service("fileAudioMng")
@Transactional
public class FileAudioMngImpl
	extends
		BaseEntityMngImpl<Long, FileAudio, FileAudioDao>
	implements
		FileAudioMng
{
	FileAudioDao fileAudioDao;

	public FileAudioDao getFileAudioDao() {
		return fileAudioDao;
	}
	
	public void setFileAudioDao(FileAudioDao fileAudioDao) {
		super.setEntityDao(fileAudioDao);
		this.fileAudioDao = fileAudioDao;
	}
	
	
}
