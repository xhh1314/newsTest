package com.hww.base.common.vo;

import java.io.Serializable;


public class BaseTreeVo implements Serializable
{
    private Long id;
    private Long pId;
    private String name;
    private Boolean open;
    private Boolean isParent;
    private String accessPath;
    private String target;
    private Boolean checked;
    private Boolean chkDisabled;

    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public Long getpId()
    {
        return pId;
    }
    public void setpId(Long pId)
    {
        this.pId = pId;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Boolean isOpen()
    {
        return open;
    }
    public void setOpen(Boolean open)
    {
        this.open = open;
    }

    public String getAccessPath()
    {
        return accessPath;
    }
    public void setAccessPath(String accessPath)
    {
        this.accessPath = accessPath;
    }
    public String getTarget()
    {
        return target;
    }
    public void setTarget(String target)
    {
        this.target = target;
    }
    public Boolean getIsParent()
    {
        return isParent;
    }
    public void setIsParent(Boolean isParent)
    {
        this.isParent = isParent;
    }
    public Boolean getChecked()
    {
        return checked;
    }
    public void setChecked(Boolean checked)
    {
        this.checked = checked;
    }
    public Boolean getChkDisabled()
    {
        return chkDisabled;
    }
    public void setChkDisabled(Boolean chkDisabled)
    {
        this.chkDisabled = chkDisabled;
    }

}
