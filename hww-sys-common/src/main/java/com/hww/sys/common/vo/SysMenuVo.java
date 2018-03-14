package com.hww.sys.common.vo;

import java.util.ArrayList;
import java.util.List;

import com.hww.base.common.vo.AbsBaseVo;

public class SysMenuVo
	extends
		AbsBaseVo {
	private Long menuId;
	private Long parentId;
	private String parentMenuName;
	private String menuName;
	private Long siteId;
	private Integer priority = 0;
	private Short display;
	private String xtype;
	private String domain;
	private String url;
	private Integer depth;
	private Long lft;
	private Long rgt;
	private Short status;
	private Boolean inOrOut = false;
	private String allIDCheck;
	private List<SysMenuVo> list=new ArrayList<>();

	public Long getLft() {
		return lft;
	}

	public void setLft(Long lft) {
		this.lft = lft;
	}

	public Long getRgt() {
		return rgt;
	}

	public void setRgt(Long rgt) {
		this.rgt = rgt;
	}

	public List<SysMenuVo> getList() {
		return list;
	}

	public void setList(List<SysMenuVo> list) {
		this.list = list;
	}

	public Long getMenuId() {
		return menuId;
	}

	public Long getParentId() {
		return parentId;
	}

	public String getMenuName() {
		return menuName;
	}

	public Integer getPriority() {
		return priority;
	}

	public Short getDisplay() {
		return display;
	}

	public String getXtype() {
		return xtype;
	}

	public String getUrl() {
		return url;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public void setDisplay(Short display) {
		this.display = display;
	}

	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Boolean getInOrOut() {
		return inOrOut;
	}

	public void setInOrOut(Boolean inOrOut) {
		this.inOrOut = inOrOut;
	}

	public String getAllIDCheck() {
		return allIDCheck;
	}

	public void setAllIDCheck(String allIDCheck) {
		this.allIDCheck = allIDCheck;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getParentMenuName() {
		return parentMenuName;
	}

	public void setParentMenuName(String parentMenuName) {
		this.parentMenuName = parentMenuName;
	}

}
