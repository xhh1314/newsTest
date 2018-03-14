package com.hww.file.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.hww.file.common.dao.FileAudioDao;
import com.hww.file.common.entity.FileAudio;
import com.hww.framework.common.dao.impl.JpaDaoImpl;

@Repository("fileAudioDao")
public class FileAudioDaoImpl
	extends
		JpaDaoImpl<Long, FileAudio>
	implements
		FileAudioDao{

}
