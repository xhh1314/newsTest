package com.hww.sys.common.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.hww.base.common.entity.AbsBaseEntity;
import com.hww.base.common.entity.IBaseTree;
import com.hww.framework.common.PriorityInterface;

/**
 * 菜单
 *
 */
@Entity
@Table(name = "sys_menu")
public class SysMenu
	extends
		AbsBaseEntity<Long>
	implements
		IBaseTree<Long>,
		PriorityInterface {

	static final String MENU_ID = "menuId";
	static final String PARENT = "parent";
	static final String MENU_NAME = "menuName";
	static final String LFT = "lft";
	static final String RGT = "rgt";
	static final String PRIORITY = "priority";
	static final String DISPLAY = "display";
	static final String XTYPE = "xtype";
	static final String URL = "url";
	static final String DEPTH = "depth";
	static final String ICON = "icon";

	// Fields

	private Long menuId;
	private SysMenu parent;
	private String menuName;
	private Long lft;
	private Long rgt;
	private Integer priority;// 顺序
	private Short display;// 是否显示
	private String xtype;
	private Long moduleId;
	private String url;
	private Integer depth;
	private String icon;
	private List<SysGroup> sysGroups = new ArrayList<SysGroup>(0);
	private List<SysMenu> childs = new ArrayList<SysMenu>(0);
	private List<SysRole> sysRoles = new ArrayList<SysRole>(0);

	// Property accessors
	@Id
	@GeneratedValue(generator = "snowFlake")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="snowFlake", strategy="com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name = "menu_id")
	public Long getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	@ManyToOne
	@JoinColumn(name = "parent_id")
	public SysMenu getParent() {
		return this.parent;
	}

	public void setParent(SysMenu parent) {
		this.parent = parent;
	}

	@Column(name = "menu_name")
	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "lft")
	public Long getLft() {
		return this.lft;
	}

	public void setLft(Long lft) {
		this.lft = lft;
	}

	@Column(name = "rgt")
	public Long getRgt() {
		return this.rgt;
	}

	public void setRgt(Long rgt) {
		this.rgt = rgt;
	}

	@Column(name = "priority")
	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(name = "display")
	public Short getDisplay() {
		return this.display;
	}

	public void setDisplay(Short display) {
		this.display = display;
	}

	@Column(name = "module_id")
	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	@Column(name = "xtype")
	public String getXtype() {
		return this.xtype;
	}

	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

	@Column(name = "url")
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "depth")
	public Integer getDepth() {
		return this.depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	@Column(name = "icon")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@ManyToMany(mappedBy = "sysMenus")
	public List<SysGroup> getSysGroups() {
		return this.sysGroups;
	}

	public void setSysGroups(List<SysGroup> sysGroups) {
		this.sysGroups = sysGroups;
	}

	@OneToMany(mappedBy = "parent")
	public List<SysMenu> getChilds() {
		return childs;
	}

	public void setChilds(List<SysMenu> childs) {
		this.childs = childs;
	}

	@ManyToMany(mappedBy = "sysMenus")
	public List<SysRole> getSysRoles() {
		return this.sysRoles;
	}

	public void setSysRoles(List<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}

	@Override
	@Transient
	public String getLftName() {
		// TODO Auto-generated method stub
		return DEF_LEFT_NAME;
	}

	@Override
	@Transient
	public String getRgtName() {
		// TODO Auto-generated method stub
		return DEF_RIGHT_NAME;
	}

	@Override
	@Transient
	public String getParentName() {
		// TODO Auto-generated method stub
		return DEF_PARENT_NAME;
	}

	@Override
	@Transient
	public Long getParentId() {
		// TODO Auto-generated method stub
		SysMenu parent = getParent();
		if (parent != null) {
			return parent.getId();
		} else {
			return null;
		}
	}

	@Override
	@Transient
	public Long getTreeId() {
		// TODO Auto-generated method stub
		return menuId;
	}

	@Override
	@Transient
	public String getTreeCondition() {
		// TODO Auto-generated method stub
		return "bean.siteId=" + getSiteId();
	}

	@Override
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return menuId;
	}

}