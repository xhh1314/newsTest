package com.hww.sys.common.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.sys.common.dao.SysMenuDao;
import com.hww.sys.common.dto.SysModuleDto;
import com.hww.sys.common.entity.SysMenu;
import com.hww.sys.common.manager.SysMenuMng;
@Service("sysMenuMng")
@Transactional
public class SysMenuMngImpl
	extends
		BaseEntityMngImpl<Long, SysMenu, SysMenuDao> implements SysMenuMng
{

    SysMenuDao sysMenuDao;

    public SysMenuDao getSysMenuDao()
    {
        return sysMenuDao;
    }
    @Autowired
    public void setSysMenuDao(SysMenuDao sysMenuDao)
    {
        super.setEntityDao(sysMenuDao);
        this.sysMenuDao = sysMenuDao;
    }
	

}
