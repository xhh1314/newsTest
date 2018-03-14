package com.hww.file.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.hww.file.common.dao.FileImgDao;
import com.hww.file.common.entity.FileImg;
import com.hww.framework.common.dao.impl.JpaDaoImpl;

@Repository("fileImgDao")
public class FileImgDaoImpl extends LocalEntityDaoImpl<Long, FileImg> implements FileImgDao{

}
