package com.hww.sys.common.manager;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.sys.common.dao.SysConfigDao;
import com.hww.sys.common.entity.SysConfig;
import com.hww.sys.common.entity.SysConfigId;

public interface SysConfigMng extends
	IBaseEntityMng<SysConfigId, SysConfig, SysConfigDao>{

}
