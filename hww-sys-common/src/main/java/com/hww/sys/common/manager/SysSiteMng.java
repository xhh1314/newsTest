package com.hww.sys.common.manager;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.sys.common.dao.SysSiteDao;
import com.hww.sys.common.entity.SysSite;

public interface SysSiteMng
	extends
		IBaseEntityMng<Integer, SysSite, SysSiteDao>
{
    public void saveSite(SysSite entity);
    public void updateSite(SysSite entity);
}
