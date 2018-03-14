package com.hww.sys.common.dto;

import java.sql.Timestamp;

import com.hww.base.common.dto.AbsBaseDto;

public class SysSiteDto extends AbsBaseDto<Integer>
{
    private String siteName;
    private String shortName;
    private String domain;
    private String sitePath;
    private String staticDir;
    private Integer siteId;
	
	private Short status;// 状态
	private Timestamp createTime;// 创建时间
	private Timestamp lastModifyTime;// 最后修改时间

	
    public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public String getSiteName()
    {
        return siteName;
    }
    public void setSiteName(String siteName)
    {
        this.siteName = siteName;
    }
    public String getShortName()
    {
        return shortName;
    }
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }
    public String getDomain()
    {
        return domain;
    }
    public void setDomain(String domain)
    {
        this.domain = domain;
    }
    public String getSitePath()
    {
        return sitePath;
    }
    public void setSitePath(String sitePath)
    {
        this.sitePath = sitePath;
    }
    public String getStaticDir()
    {
        return staticDir;
    }
    public void setStaticDir(String staticDir)
    {
        this.staticDir = staticDir;
    }

    
    public Short getStatus()
    {
        return status;
    }
    public void setStatus(Short status)
    {
        this.status = status;
    }
    
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return siteId;
	}

}
