package com.hww.sys.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.hww.sys.common.dao.SysConfigDao;
import com.hww.sys.common.entity.SysConfig;
import com.hww.sys.common.entity.SysConfigId;
@Repository("sysConfigDao")
public class SysConfigDaoImpl extends LocalEntityDaoImpl<SysConfigId, SysConfig>
				implements SysConfigDao{

}
