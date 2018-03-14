package com.hww.sys.common.vo;

import com.hww.base.common.vo.AbsBaseVo;

public class SysRoleCheckBoxVo extends AbsBaseVo
{
    private Long roleId;
    private String roleName;
    private Boolean checked;

    public SysRoleCheckBoxVo(Long roleId, String roleName, Boolean checked)
    {
        this.roleId = roleId;
        this.roleName = roleName;
        this.checked = checked;
    }
    public Long getRoleId()
    {
        return roleId;
    }
    public String getRoleName()
    {
        return roleName;
    }
    public Boolean getChecked()
    {
        return checked;
    }
    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }
    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }
    public void setChecked(Boolean checked)
    {
        this.checked = checked;
    }

}
