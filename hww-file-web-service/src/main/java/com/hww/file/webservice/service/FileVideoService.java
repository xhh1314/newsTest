package com.hww.file.webservice.service;

import com.hww.file.common.vo.FileInfoVo;
import com.hww.file.common.vo.FileSetVo;
import com.hww.file.common.vo.FileVideoVo;

import java.util.List;

public interface FileVideoService {

	Long saveVideo(FileVideoVo fileVideoVo, FileInfoVo fileInfoVo);
	
	/**
	 * 批量保存
	 * @param fileVideoVos
	 * @param fileInfoVos
	 * @param fileSetVo 视频集vo
	 */
	void saveVideos(List<FileVideoVo> fileVideoVos, List<FileInfoVo> fileInfoVos,FileSetVo fileSetVo);
	
	String getUrlByVideoId(String contentPath,String videoIds);
	
	List<FileVideoVo> getVideoCollectBySetId(String setId);
	
	void updateVideo(FileVideoVo fileVideoVo);
}
