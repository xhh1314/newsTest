package com.hww.sys.common.dto;

import com.hww.base.common.dto.AbsBaseDto;


public class SysGroupDto extends AbsBaseDto<Long>
{

    // Fields

    private Long groupId;
    private String groupName;
    
    private String menuIds;

    // Property accessors

    public Long getGroupId()
    {
        return this.groupId;
    }

    public void setGroupId(Long groupId)
    {
        this.groupId = groupId;
    }

    public String getGroupName()
    {
        return this.groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
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
		return groupId;
	}

}