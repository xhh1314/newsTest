package com.hww.sys.common.vo;

import com.hww.base.common.vo.AbsBaseVo;

public class SysGroupVo extends AbsBaseVo
{
    private Long groupId;
    private Long siteId;
    private String groupName;
    private Short status;
    private Integer pageNo;
    private String allIDCheck;
    public Long getGroupId()
    {
        return groupId;
    }
    public Long getSiteId()
    {
        return siteId;
    }
    public String getGroupName()
    {
        return groupName;
    }
    public Short getStatus()
    {
        return status;
    }
    public Integer getPageNo()
    {
        return pageNo;
    }
    public String getAllIDCheck()
    {
        return allIDCheck;
    }
    public void setGroupId(Long groupId)
    {
        this.groupId = groupId;
    }
    public void setSiteId(Long siteId)
    {
        this.siteId = siteId;
    }
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }
    public void setStatus(Short status)
    {
        this.status = status;
    }
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    public void setAllIDCheck(String allIDCheck)
    {
        this.allIDCheck = allIDCheck;
    }

}
