package com.hww.sys.common.dto;

import com.hww.base.common.dto.IBaseDto;

/**
 * 模块的概念用于content,就是 几种系统默认的分类 cms,img,media,product,live等等
 * 
 */
public class SysModuleDto
	implements
		IBaseDto<Long> {

	// Fields
	private Long moduleId;
	private String moduleName;
	private String moduleTab;// 标记
	private String moduleUrl;

	// Property accessors
	public Long getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleTab() {
		return moduleTab;
	}

	public void setModuleTab(String moduleTab) {
		this.moduleTab = moduleTab;
	}

	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}
	
	
	

}