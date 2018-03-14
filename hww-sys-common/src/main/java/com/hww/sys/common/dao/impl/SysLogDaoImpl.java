package com.hww.sys.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.hww.sys.common.dao.SysLogDao;
import com.hww.sys.common.entity.SysLog;
@Repository("sysLogDao")
public class SysLogDaoImpl extends LocalEntityDaoImpl<Long, SysLog>
        implements
            SysLogDao
{

}
