package com.hww.sys.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.hww.sys.common.dao.SysGroupDao;
import com.hww.sys.common.entity.SysGroup;
@Repository("sysGroupDao")
public class SysGroupDaoImpl extends LocalEntityDaoImpl<Long, SysGroup>
        implements
            SysGroupDao
{

}
