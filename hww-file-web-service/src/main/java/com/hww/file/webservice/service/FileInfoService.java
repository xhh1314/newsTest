package com.hww.file.webservice.service;

import com.hww.file.common.dto.FileInfoDto;
import com.hww.file.common.entity.FileInfo;
import com.hww.file.common.vo.FileInfoVo;

import java.util.List;

/**
 * Created by 13705 on 2017/11/23.
 */
public interface FileInfoService {

    List<FileInfo> queryFileInfoListByIds(String ids);

    FileInfoDto findFileInfoById(Long fileId);
    
    FileInfoDto update(FileInfoDto dto);
    public Long saveInfo(FileInfoVo fileInfoVo) ;
}
