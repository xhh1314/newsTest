package com.hww.sys.common.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.listener.IModifyListener;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.sys.common.dao.SysUserDao;
import com.hww.sys.common.entity.SysUser;
import com.hww.sys.common.manager.SysUserMng;
@Service("sysUserMng")
public class SysUserMngImpl extends BaseEntityMngImpl<Long, SysUser, SysUserDao> implements SysUserMng{
    SysUserDao sysUserDao;

    @Autowired
    public void setSysUserDao(SysUserDao sysUserDao)
    {
        super.setEntityDao(sysUserDao);
        this.sysUserDao = sysUserDao;
    }

    private List<IModifyListener<SysUser>> listenerList;

    public void setListenerList(
            List<IModifyListener<SysUser>> listenerList)
    {
        this.listenerList = listenerList;
    }

    @Override
    public void saveUser(SysUser user)
    {
        // TODO Auto-generated method stub
        save(user);

    }
    @Override
    public void updateUser(SysUser user)
    {
        // TODO Auto-generated method stub
    	save(user);
        afterSave(user);
    }

    private void afterSave(SysUser user)
    {
        if (listenerList != null)
        {
            for (IModifyListener<SysUser> listener : listenerList)
            {
                listener.afterSave(user);
            }
        }
    }
    
	@Override
	public SysUser findUserByUserName(String username) {
		// TODO Auto-generated method stub
		List<SysUser> users = sysUserDao.find("username", username);
		if(users!=null && users.size()>0){
			return users.get(0);
		}
		return null;
	}

}
