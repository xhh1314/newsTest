package com.hww.sys.common.dto;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hww.base.common.dto.AbsBaseDto;


public class SysUserDto extends AbsBaseDto<Long>
{

    // Fields

    private Long userId;
    private String email;
    private Short isAdmin;
    private Short isSelfAdmin;
    private Short isViewonlyAdmin;
    private Short isAllCategory;
    private String lastLoginIp;
    private Timestamp lastLoginTime;
    private Integer loginCount;
    private Integer rank;
    private String registerIp;
    private String username;
    private String password;
    private String realName;
    private String pseudonym;
    
    
    private String groupIds;
    private String roleIds;
    private Long defaultRole;
    
    private List<Long> roleIdList;
    // Property accessors

    public Long getUserId()
    {
        return this.userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Short getIsAdmin()
    {
        return this.isAdmin;
    }

    public void setIsAdmin(Short isAdmin)
    {
        this.isAdmin = isAdmin;
    }

    public Short getIsSelfAdmin()
    {
        return this.isSelfAdmin;
    }

    public void setIsSelfAdmin(Short isSelfAdmin)
    {
        this.isSelfAdmin = isSelfAdmin;
    }

    public Short getIsViewonlyAdmin()
    {
        return this.isViewonlyAdmin;
    }

    public void setIsViewonlyAdmin(Short isViewonlyAdmin)
    {
        this.isViewonlyAdmin = isViewonlyAdmin;
    }

    public Short getIsAllCategory()
    {
        return this.isAllCategory;
    }

    public void setIsAllCategory(Short isAllCategory)
    {
        this.isAllCategory = isAllCategory;
    }

    public String getLastLoginIp()
    {
        return this.lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp)
    {
        this.lastLoginIp = lastLoginIp;
    }

    public Timestamp getLastLoginTime()
    {
        return this.lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime)
    {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getLoginCount()
    {
        return this.loginCount;
    }

    public void setLoginCount(Integer loginCount)
    {
        this.loginCount = loginCount;
    }

    public Integer getRank()
    {
        return this.rank;
    }

    public void setRank(Integer rank)
    {
        this.rank = rank;
    }

    public String getRegisterIp()
    {
        return this.registerIp;
    }

    public void setRegisterIp(String registerIp)
    {
        this.registerIp = registerIp;
    }

    public String getUsername()
    {
        return this.username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password;
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

    public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public Set<String> getPerms(Integer siteId, Set<String> perms)
    {

        Set<String> allPerms = new HashSet<String>();
        allPerms.add("*");

        return allPerms;
    }

	public Long getDefaultRole() {
		return defaultRole;
	}

	public void setDefaultRole(Long defaultRole) {
		this.defaultRole = defaultRole;
	}

	public List<Long> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<Long> roleIdList) {
		this.roleIdList = roleIdList;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return userId;
	}
    
}