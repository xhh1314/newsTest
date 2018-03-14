package com.hww.sys.common.vo;

import javax.validation.constraints.NotNull;

import com.hww.base.common.vo.AbsBaseVo;

public class SysUserVo extends AbsBaseVo
{
    private Long userId;
    private Integer siteId;
    private String email;
    private Short isAdmin;
    private Short isSelfAdmin;
    private Short isViewonlyAdmin;
    private Short isAllCategory;
    private Integer rank;
    @NotNull
    private String username;
    private String realName;
    private String pseudonym;
    private Short status;
    private String allIDCheck;
    private Integer pageNo;
    private Long defaultRole;
    private String password;
    
    public Long getDefaultRole() {
		return defaultRole;
	}
	public void setDefaultRole(Long defaultRole) {
		this.defaultRole = defaultRole;
	}
	public Long getUserId()
    {
        return userId;
    }
    public Integer getSiteId()
    {
        return siteId;
    }
    public String getEmail()
    {
        return email;
    }
    public Short getIsAdmin()
    {
        return isAdmin;
    }
    public Short getIsSelfAdmin()
    {
        return isSelfAdmin;
    }
    public Short getIsViewonlyAdmin()
    {
        return isViewonlyAdmin;
    }
    public Short getIsAllCategory()
    {
        return isAllCategory;
    }
    public Integer getRank()
    {
        return rank;
    }
    public String getUsername()
    {
        return username;
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
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }
    public void setSiteId(Integer siteId)
    {
        this.siteId = siteId;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public void setIsAdmin(Short isAdmin)
    {
        this.isAdmin = isAdmin;
    }
    public void setIsSelfAdmin(Short isSelfAdmin)
    {
        this.isSelfAdmin = isSelfAdmin;
    }
    public void setIsViewonlyAdmin(Short isViewonlyAdmin)
    {
        this.isViewonlyAdmin = isViewonlyAdmin;
    }
    public void setIsAllCategory(Short isAllCategory)
    {
        this.isAllCategory = isAllCategory;
    }
    public void setRank(Integer rank)
    {
        this.rank = rank;
    }
    public void setUsername(String username)
    {
        this.username = username;
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
    public String getRealName()
    {
        return realName;
    }
    public String getPseudonym()
    {
        return pseudonym;
    }
    public void setRealName(String realName)
    {
        this.realName = realName;
    }
    public void setPseudonym(String pseudonym)
    {
        this.pseudonym = pseudonym;
    }
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
