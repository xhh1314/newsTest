package com.hww.sys.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.hww.base.common.entity.IBaseEntity;


/**
 * 全局性的 模块的概念用于content,就是 几种系统默认的分类 cms,img,media,product,live等等 区别于model模型
 */
@Entity
@Table(name = "sys_module")
public class SysModule
	implements
		IBaseEntity<Long> {

	// Fields
	private Long moduleId;
	private String moduleName;
	private String moduleSymbol;// 代号
	private String moduleUrl;

	// Property accessors
	@Id
	@Column(name = "module_id")
	@GeneratedValue(generator = "snowFlake")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="snowFlake", strategy="com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	public Long getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	@Column(name = "module_name")
	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Column(name = "module_symbol")
	public String getModuleSymbol() {
		return moduleSymbol;
	}

	public void setModuleSymbol(String moduleSymbol) {
		this.moduleSymbol = moduleSymbol;
	}
	
	@Column(name = "module_url")
	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	@Override
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return moduleId;
	}
}