package com.hww.sys.common.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.sys.common.dao.SysGroupDao;
import com.hww.sys.common.entity.SysGroup;
import com.hww.sys.common.manager.SysGroupMng;
@Service("sysGroupMng")
public class SysGroupMngImpl
	extends
        BaseEntityMngImpl<Long, SysGroup, SysGroupDao>
    implements
        SysGroupMng
{
    SysGroupDao sysGroupDao;

    public SysGroupDao getSysGroupDao()
    {
        return sysGroupDao;
    }
    @Autowired
    public void setSysGroupDao(SysGroupDao sysGroupDao)
    {
        super.setEntityDao(sysGroupDao);
        this.sysGroupDao = sysGroupDao;
    }

}
