package com.hww.sys.common.manager;

import javax.servlet.http.HttpServletRequest;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.sys.common.dao.SysLogDao;
import com.hww.sys.common.entity.SysLog;
import com.hww.sys.common.entity.SysUser;

public interface SysLogMng
	extends IBaseEntityMng<Long, SysLog, SysLogDao>
{
    public SysLog loginFailure(HttpServletRequest request, String title,
            String content);

    public SysLog loginSuccess(HttpServletRequest request, SysUser user,
            String title);

    public SysLog operating(HttpServletRequest request, String title,
            String content);
}
