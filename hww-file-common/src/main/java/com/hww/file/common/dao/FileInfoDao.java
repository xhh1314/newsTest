package com.hww.file.common.dao;

import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.file.common.entity.FileInfo;

import java.util.List;

public interface FileInfoDao extends IBaseEntityDao<Long, FileInfo> {

    List<FileInfo> queryFileInfoListByIds(String ids);
}
