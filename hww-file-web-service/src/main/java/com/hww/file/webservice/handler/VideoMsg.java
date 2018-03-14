package com.hww.file.webservice.handler;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public class VideoMsg{

	private MultipartFile multipartFile;
	private File target;
	private Long fileId;
	private String ffmpegPath;
	private String coverPath;
	
	public VideoMsg(MultipartFile multipartFile, File target, Long fileId) {
		super();
		this.multipartFile = multipartFile;
		this.target = target;
		this.fileId = fileId;
	}
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}
	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}
	public File getTarget() {
		return target;
	}
	public void setTarget(File target) {
		this.target = target;
	}
	public String getFfmpegPath() {
		return ffmpegPath;
	}
	public void setFfmpegPath(String ffmpegPath) {
		this.ffmpegPath = ffmpegPath;
	}
	public String getCoverPath() {
		return coverPath;
	}
	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}
	
	
}
