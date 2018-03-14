package com.hww.file.common.dao.impl;

import com.hww.base.common.util.Finder;
import com.hww.file.common.dao.FileInfoDao;
import com.hww.file.common.entity.FileInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("fileInfoDao")
public class FileInfoDaoImpl extends LocalEntityDaoImpl<Long, FileInfo> implements FileInfoDao {

    @Override
    public List<FileInfo> queryFileInfoListByIds(String ids) {
        Finder hql = Finder.create("from FileInfo");
        hql.append(" where 1=1");
        hql.append(" and status=1");
        hql.append(" and FIND_IN_SET(fileId,:fileIds)>0");
        hql.setParam("fileIds", ids);
        List<FileInfo> fileInfoList = (List<FileInfo>) find(hql);
        return fileInfoList;
    }
}
