package com.hww.sys.common.manager.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.sys.common.dao.SysConfigDao;
import com.hww.sys.common.entity.SysConfig;
import com.hww.sys.common.entity.SysConfigId;
import com.hww.sys.common.manager.SysConfigMng;

@Service("sysConfigMng")
public class SysConfigMngImpl 
	extends
	BaseEntityMngImpl<SysConfigId, SysConfig, SysConfigDao>
	implements
	SysConfigMng
{

}
