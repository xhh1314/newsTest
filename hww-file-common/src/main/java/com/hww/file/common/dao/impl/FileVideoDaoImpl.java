package com.hww.file.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.hww.file.common.dao.FileVideoDao;
import com.hww.file.common.entity.FileVideo;
import com.hww.framework.common.dao.impl.JpaDaoImpl;

@Repository("fileVideoDao")
public class FileVideoDaoImpl
	extends
		JpaDaoImpl<Long, FileVideo>
	implements
		FileVideoDao{

}
