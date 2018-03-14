package com.hww.sys.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.hww.sys.common.dao.SysUserDao;
import com.hww.sys.common.entity.SysUser;
@Repository("sysUserDao")
public class SysUserDaoImpl extends LocalEntityDaoImpl<Long, SysUser>
        implements
            SysUserDao
{

}
