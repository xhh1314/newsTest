package com.hww.file.common.dto;

import com.hww.base.common.dto.AbsBaseDto;

public class FileInfoDto extends AbsBaseDto<Long>{
	
	private Long fileId;
	
	private String fileTitle;//
    private String fileDesc;//文件说明
    private Double fileSize;//文件大小
    private String filePath;//文件相对路径
    
    private String fileName;//文件名
    private String fileExtensionName;//文件后缀
    
    private Integer userId;//后台编辑
    private Integer memberId;//作者id 会员id
    private String fileVisitUrl; //文件访问路径

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getFileTitle() {
		return fileTitle;
	}

	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getFileVisitUrl() {
		return fileVisitUrl;
	}

	public void setFileVisitUrl(String fileVisitUrl) {
		this.fileVisitUrl = fileVisitUrl;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return fileId;
	}

}
