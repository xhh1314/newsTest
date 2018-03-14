package com.hww.file.common.manager;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.file.common.dao.FileInfoDao;
import com.hww.file.common.entity.FileInfo;

import java.util.List;

public interface FileInfoMng extends IBaseEntityMng<Long, FileInfo, FileInfoDao> {

    List<FileInfo> queryFileInfoListByIds(String ids);
}
