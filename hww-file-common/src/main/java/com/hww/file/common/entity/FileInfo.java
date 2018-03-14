package com.hww.file.common.entity;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hww.base.common.entity.AbsBaseEntity;

/**
 * 文件 统一管理整个系统中的附件 没有实际的业务意义
 *
 */
@Entity
@Table(name="file_info")
@JsonIgnoreProperties(value = "id")
public class FileInfo extends AbsBaseEntity<Long>{
	
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

	@Id
	@Column(name = "file_id")
	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	@Column(name="file_save_name")
	public String getFileSaveName() {
		return fileSaveName;
	}

	public void setFileSaveName(String fileSaveName) {
		this.fileSaveName = fileSaveName;
	}

	@Column(name="file_desc")
	public String getFileDesc() {
		return fileDesc;
	}

	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}

	@Column(name="file_size")
	public Double getFileSize() {
		return fileSize;
	}

	public void setFileSize(Double fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name="file_relative_path")
	public String getFileRelativePath() {
		return fileRelativePath;
	}

	public void setFileRelativePath(String fileRelativePath) {
		this.fileRelativePath = fileRelativePath;
	}

	@Column(name="file_name")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name="file_extension_name")
	public String getFileExtensionName() {
		return fileExtensionName;
	}

	public void setFileExtensionName(String fileExtensionName) {
		this.fileExtensionName = fileExtensionName;
	}

	@Column(name="file_type")
	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	@Override
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
