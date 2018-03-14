package com.hww.sys.common.manager;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.sys.common.dao.SysUserDao;
import com.hww.sys.common.entity.SysUser;

public interface SysUserMng
	extends
		IBaseEntityMng<Long, SysUser, SysUserDao>
{
    public void saveUser(SysUser user);

    public void updateUser(SysUser user);
    
    public SysUser findUserByUserName(String username);
}
