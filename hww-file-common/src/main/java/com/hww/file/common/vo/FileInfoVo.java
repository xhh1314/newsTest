package com.hww.file.common.vo;

import javax.persistence.*;

import com.hww.base.common.entity.AbsBaseEntity;

/**
 * 文件 统一管理整个系统中的附件 没有实际的业务意义
 *
 */
public class FileInfoVo extends AbsBaseEntity<Long>{
	
	private Long fileId;//key
	//private Integer userId;//后台编辑
    //private Integer memberId;//作者id 会员id
	
	private String fileName;//文件原始名字，不带后缀
    private String fileDesc;//文件说明
    private Double fileSize;//文件大小 单位B
    
    private String fileRelativePath;//文件相对路径 可以不要
    private String fileSaveName;//文件保存后名字 uuid,不带后缀
    private String fileExtensionName;//文件后缀名(统一小写)
    private Integer fileType;//为了大数据量检索使用，与fileExtensionName 同样作用

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getFileSaveName() {
		return fileSaveName;
	}

	public void setFileSaveName(String fileSaveName) {
		this.fileSaveName = fileSaveName;
	}

	public String getFileDesc() {
		return fileDesc;
	}

	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}

	public Double getFileSize() {
		return fileSize;
	}

	public void setFileSize(Double fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileRelativePath() {
		return fileRelativePath;
	}

	public void setFileRelativePath(String fileRelativePath) {
		this.fileRelativePath = fileRelativePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileExtensionName() {
		return fileExtensionName;
	}

	public void setFileExtensionName(String fileExtensionName) {
		this.fileExtensionName = fileExtensionName;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return fileId;
	}
	
}
