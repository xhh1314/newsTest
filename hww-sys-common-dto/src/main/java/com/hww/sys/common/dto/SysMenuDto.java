package com.hww.sys.common.dto;

import java.util.ArrayList;
import java.util.List;

import com.hww.base.common.dto.AbsBaseDto;
import com.hww.base.common.entity.IBaseTree;

public class SysMenuDto
	extends
		AbsBaseDto<Long>
	implements
		IBaseTree<Long>{

	// Fields

	private Long menuId;
	private Long parentId;
	private String parentMenuName;
	private String menuName;
	private Long lft;
	private Long rgt;
	private Integer priority;
	private Short display;
	private String xtype;
	private String url;
	private Integer depth;
	private String icon;
	
	private Long moduleId; //模块id
	private List<SysMenuDto> list=new ArrayList<>();
	
	// Property accessors

	public List<SysMenuDto> getList() {
		return list;
	}

	public void setList(List<SysMenuDto> list) {
		this.list = list;
	}

	public Long getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Long getLft() {
		return this.lft;
	}

	public void setLft(Long lft) {
		this.lft = lft;
	}

	public Long getRgt() {
		return this.rgt;
	}

	public void setRgt(Long rgt) {
		this.rgt = rgt;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Short getDisplay() {
		return this.display;
	}

	public void setDisplay(Short display) {
		this.display = display;
	}

	public String getXtype() {
		return this.xtype;
	}

	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getDepth() {
		return this.depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getParentMenuName() {
		return parentMenuName;
	}

	public void setParentMenuName(String parentMenuName) {
		this.parentMenuName = parentMenuName;
	}

	@Override
	public String getLftName() {
		// TODO Auto-generated method stub
		return DEF_LEFT_NAME;
	}

	@Override
	public String getRgtName() {
		// TODO Auto-generated method stub
		return DEF_RIGHT_NAME;
	}

	@Override
	public String getParentName() {
		// TODO Auto-generated method stub
		return DEF_PARENT_NAME;
	}

	@Override
	public Long getParentId() {
		// TODO Auto-generated method stub
		return parentId;
	}

	@Override
	public Long getTreeId() {
		// TODO Auto-generated method stub
		return menuId;
	}

	@Override
	public String getTreeCondition() {
		// TODO Auto-generated method stub
		return "bean.sysSite.siteId=" + getSiteId();
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return menuId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	
}