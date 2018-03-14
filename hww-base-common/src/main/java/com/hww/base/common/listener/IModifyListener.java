package com.hww.base.common.listener;

import java.util.Map;

import com.hww.base.common.entity.IBaseEntity;

// 修改监听器
public interface IModifyListener<ENTITY extends IBaseEntity<?>>
{
    /**
     * 保存之前执行
     * 
     * @param entity
     * @return 获取一些需要在afterSave中使用的值。
     */
    public void preSave(ENTITY entity);

    /**
     * 保存之后执行
     * 
     * @param entity
     */
    public void afterSave(ENTITY entity);

    /**
     * 修改之前执行
     * 
     * @param entity
     *            修改前的
     * @return 获取一些需要在afterChange中使用的值。
     */
    public Map<String, Object> preChange(ENTITY entity);

    /**
     * 修改之后执行
     * 
     * @param entity
     *            修改后的
     * @param map
     *            从{@link #preChange(ENTITY)}方法返回的值。
     */
    public void afterChange(ENTITY entity, Map<String, Object> map);

    /**
     * 删除之前执行
     * 
     * @param entity
     */
    public void preDelete(ENTITY entity);

    /**
     * 删除之后执行
     * 
     * @param entity
     */
    public void afterDelete(ENTITY entity);
}
