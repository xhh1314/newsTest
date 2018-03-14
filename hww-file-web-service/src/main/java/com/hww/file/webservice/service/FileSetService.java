package com.hww.file.webservice.service;

import com.hww.file.common.vo.FileSetVo;

/**
 * 图集，音频集，视频集(电视剧)
 */
public interface FileSetService {

	void saveOrUpdateFileSet(FileSetVo fileSetVo);
	
}
