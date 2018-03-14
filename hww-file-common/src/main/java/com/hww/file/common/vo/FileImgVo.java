package com.hww.file.common.vo;

import com.hww.base.common.entity.AbsBaseEntity;
/**
 * 图像文件
 *
 */
public class FileImgVo extends AbsBaseEntity<Long>{
	private Long fileId;//统一的fileid 主键和外键
	private Long memberId;//作者id 会员id
	private Long setId;//统一的集合id 与 fileset的主键一致
	
	private Integer imgFileWidth;//图片宽度
	private Integer imgFileHight;//图片高度
	
	private Short imgType;//与作者类型一样
	
	private Integer displayOrder;//顺序 在图集列表里
	
	private FileInfoVo fileInfo;


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

	public Integer getImgFileWidth() {
		return imgFileWidth;
	}

	public void setImgFileWidth(Integer imgFileWidth) {
		this.imgFileWidth = imgFileWidth;
	}

	public Integer getImgFileHight() {
		return imgFileHight;
	}

	public void setImgFileHight(Integer imgFileHight) {
		this.imgFileHight = imgFileHight;
	}

	public Short getImgType() {
		return imgType;
	}

	public void setImgType(Short imgType) {
		this.imgType = imgType;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public FileInfoVo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileInfoVo fileInfo) {
		this.fileInfo = fileInfo;
	}

	@Override
	public Long getId() {
		return this.fileId;
	}

	
}
