package com.hww.file.common.vo;

import com.hww.base.common.vo.AbsBaseVo;
import com.hww.file.common.entity.FileInfo;
/**
 * 视频文件
 *
 */
public class FileVideoVo extends AbsBaseVo{
	private Long fileId;//统一的fileid 
	private Long memberId;//作者id 会员id（与SysFile里重复）
	private Long setId;//图集id 与 mediaSet的主键一致
	
	private Integer dataRate;//码率Kbps
	private String resolution;//分辨率
	
	private FileImgVo cover;//封面,每一个视频均有自己的封面
	
	private Integer length;//音视频时长 单位秒
	
	private FileInfo fileInfo;

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getSetId() {
		return setId;
	}

	public void setSetId(Long setId) {
		this.setId = setId;
	}

	public Integer getDataRate() {
		return dataRate;
	}

	public void setDataRate(Integer dataRate) {
		this.dataRate = dataRate;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public FileImgVo getCover() {
		return cover;
	}

	public void setCover(FileImgVo cover) {
		this.cover = cover;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	
}
