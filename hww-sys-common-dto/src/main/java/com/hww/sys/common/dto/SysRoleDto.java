package com.hww.sys.common.dto;

import com.hww.base.common.dto.AbsBaseDto;


public class SysRoleDto extends AbsBaseDto<Long>
{

    // Fields

    private Long roleId;
    private String roleName;
    
    private String menuIds;//权限


    public Long getRoleId()
    {
        return this.roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public String getRoleName()
    {
        return this.roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

	public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return roleId;
	}

}