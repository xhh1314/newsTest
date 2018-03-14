package com.hww.file.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.hww.file.common.dao.FileSetDao;
import com.hww.file.common.entity.FileSet;

@Repository("fileSetDao")
public class FileSetDaoImpl extends LocalEntityDaoImpl<Long, FileSet> implements FileSetDao{

}
