package com.hww.sys.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.hww.sys.common.dao.SysSiteDao;
import com.hww.sys.common.entity.SysSite;
@Repository("sysSiteDao")
public class SysSiteDaoImpl extends LocalEntityDaoImpl<Integer, SysSite>
        implements
            SysSiteDao
{

}
