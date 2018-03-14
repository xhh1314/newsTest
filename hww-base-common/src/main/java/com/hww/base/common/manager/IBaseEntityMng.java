package com.hww.base.common.manager;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.LockModeType;

import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.base.common.entity.IBaseEntity;
import com.hww.base.common.util.Updater;
public interface IBaseEntityMng<ID extends Serializable, ENTITY extends IBaseEntity<ID>,Dao extends IBaseEntityDao<ID, ENTITY>>
	extends IBaseMng
{
	/**
	 * 保存
	 * 
	 * @param entity
	 * @return
	 */
	public ENTITY save(ENTITY entity);

	public Collection<ENTITY> save(Collection<ENTITY> entities);

	/**
	 * 更新
	 * 
	 * @param entity
	 * @return
	 */
	public ENTITY update(ENTITY entity);
	
	public ENTITY update(Updater<ENTITY> updater);

	public Collection<ENTITY> update(Collection<ENTITY> entities);
	
	void updateStatusByProperty(Short status, String proname, Collection<ID> ids);
	
	/**
	 * 删除
	 * 
	 * @param entity
	 * @return
	 */
	public Boolean delete(ID id);
	
	public Boolean delete(ENTITY entity);
	
	public Boolean delete(Collection<ENTITY> entities);

	public Boolean delete(String property, Object... values);

	/**
	 * 查找单个实体
	 * 
	 * @param id
	 * @return
	 */
	public ENTITY find(ID id);

	public ENTITY find(ID id, LockModeType lockModeType);

	/**
	 * 查找多个实体
	 * 
	 * @param values
	 * @return
	 */
	public List<ENTITY> find(String property, Object... values);
	
}
