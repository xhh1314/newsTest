package com.hww.sys.common.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.hww.base.common.listener.IModifyListener;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.sys.common.dao.SysSiteDao;
import com.hww.sys.common.entity.SysSite;
import com.hww.sys.common.manager.SysSiteMng;
@Service("sysSiteMng")
public class SysSiteMngImpl
	extends
		BaseEntityMngImpl<Integer, SysSite, SysSiteDao> implements SysSiteMng
{
    SysSiteDao sysSiteDao;

    public SysSiteDao getSysSiteDao()
    {
        return sysSiteDao;
    }
    @Autowired
    public void setSysSiteDao(SysSiteDao sysSiteDao)
    {
        super.setEntityDao(sysSiteDao);
        this.sysSiteDao = sysSiteDao;
    }

    private List<IModifyListener<SysSite>> listenerList;

    public void setListenerList(
            List<IModifyListener<SysSite>> listenerList)
    {
        this.listenerList = listenerList;
    }

    @Override
    public void saveSite(SysSite entity)
    {
        // TODO Auto-generated method stub
        // 执行监听器
        preSave(entity);
        save(entity);
        afterSave(entity);

    }

    private void preSave(SysSite entity)
    {
        if (listenerList != null)
        {
            for (IModifyListener<SysSite> listener : listenerList)
            {
                listener.preSave(entity);
            }
        }
    }

    private void afterSave(SysSite entity)
    {
        if (listenerList != null)
        {
            for (IModifyListener<SysSite> listener : listenerList)
            {
                listener.afterSave(entity);
            }
        }
    }

    @Override
    public void updateSite(SysSite entity)
    {
        // TODO Auto-generated method stub
        List<Map<String, Object>> mapList = preChange(entity);
        update(entity);
        afterChange(entity, mapList);
    }

    private List<Map<String, Object>> preChange(SysSite entity)
    {
        if (listenerList != null)
        {
            int len = listenerList.size();
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(
                    len);
            for (IModifyListener<SysSite> listener : listenerList)
            {
                list.add(listener.preChange(entity));
            }
            return list;
        } else
        {
            return null;
        }
    }

    private void afterChange(SysSite entity,
            List<Map<String, Object>> mapList)
    {
        if (listenerList != null)
        {
            Assert.notNull(mapList,"not null");
            Assert.isTrue(mapList.size() == listenerList.size(),"not equal");
            int len = listenerList.size();
            IModifyListener<SysSite> listener;
            for (int i = 0; i < len; i++)
            {
                listener = listenerList.get(i);
                listener.afterChange(entity, mapList.get(i));
            }
        }
    }
    
}
