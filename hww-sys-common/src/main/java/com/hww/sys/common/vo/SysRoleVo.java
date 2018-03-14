package com.hww.sys.common.vo;

import com.hww.base.common.vo.AbsBaseVo;

public class SysRoleVo extends AbsBaseVo
{
    private Long roleId;
    private Long siteId;
    private String roleName;
    private Short status;
    private String allIDCheck;
    private Integer pageNo;
    public Long getRoleId()
    {
        return roleId;
    }
    public Long getSiteId()
    {
        return siteId;
    }
    public String getRoleName()
    {
        return roleName;
    }
    public Short getStatus()
    {
        return status;
    }
    public String getAllIDCheck()
    {
        return allIDCheck;
    }
    public Integer getPageNo()
    {
        return pageNo;
    }
    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }
    public void setSiteId(Long siteId)
    {
        this.siteId = siteId;
    }
    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }
    public void setStatus(Short status)
    {
        this.status = status;
    }
    public void setAllIDCheck(String allIDCheck)
    {
        this.allIDCheck = allIDCheck;
    }
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }

}
