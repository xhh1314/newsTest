package com.hww.file.webservice.service;

import com.hww.file.common.vo.FileImgVo;
import com.hww.file.common.vo.FileInfoVo;
import com.hww.file.common.vo.FileSetVo;

import java.util.List;

/**
 * Created by 13705 on 2017/11/23.
 */
public interface FileImgService {

	Long saveImg(FileImgVo fileImgVo, FileInfoVo fileInfoVo);
	
	String saveImgs(List<FileImgVo> fileImgVos, List<FileInfoVo> fileInfoVos);
	
	Long saveImgs(List<FileImgVo> fileImgVos, List<FileInfoVo> fileInfoVos, FileSetVo fileSetVo);
	
	String getUrlByFileIds(String contentPath,String fileIds);
	
	List<FileImgVo> getPicCollectBySetId(String setId);

	List<FileInfoVo> getUrlsByids(List<Long> ids);

	/**
	 * 将一堆图片归到图集
	 * @param ids
	 */
	void picSet(List<Long> ids);

	
}
