package com.hww.sys.common.manager.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.sys.common.dao.SysLogDao;
import com.hww.sys.common.entity.SysLog;
import com.hww.sys.common.entity.SysUser;
import com.hww.sys.common.manager.SysLogMng;
@Service("sysLogMng")
public class SysLogMngImpl 
	extends
		BaseEntityMngImpl<Long, SysLog, SysLogDao>
    implements
        SysLogMng
{

    SysLogDao sysLogDao;

    public SysLogDao getSysLogDao()
    {
        return sysLogDao;
    }
    @Autowired
    public void setSysLogDao(SysLogDao sysLogDao)
    {
        super.setEntityDao(sysLogDao);
        this.sysLogDao = sysLogDao;
    }

    @Override
    public SysLog loginFailure(HttpServletRequest request, String title,
            String content)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SysLog loginSuccess(HttpServletRequest request, SysUser user,
            String title)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SysLog operating(HttpServletRequest request, String title,
            String content)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
