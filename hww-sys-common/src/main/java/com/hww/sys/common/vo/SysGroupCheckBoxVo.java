package com.hww.sys.common.vo;

import com.hww.base.common.vo.IBaseVo;

public class SysGroupCheckBoxVo implements IBaseVo
{
    private Long groupId;
    private String groupName;
    private Boolean checked;

    public SysGroupCheckBoxVo(Long groupId, String groupName, Boolean checked)
    {
        this.groupId = groupId;
        this.groupName = groupName;
        this.checked = checked;
    }
    public Long getGroupId()
    {
        return groupId;
    }
    public String getGroupName()
    {
        return groupName;
    }
    public Boolean getChecked()
    {
        return checked;
    }
    public void setGroupId(Long groupId)
    {
        this.groupId = groupId;
    }
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }
    public void setChecked(Boolean checked)
    {
        this.checked = checked;
    }

}
