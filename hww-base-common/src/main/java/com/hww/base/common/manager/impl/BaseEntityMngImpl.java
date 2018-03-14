package com.hww.base.common.manager.impl;

import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.base.common.entity.IBaseEntity;
import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Updater;

import javax.persistence.LockModeType;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class BaseEntityMngImpl<ID extends Serializable, ENTITY extends IBaseEntity<ID>, DAO extends IBaseEntityDao<ID, ENTITY>>
	extends
		BaseMngImpl
	implements
		IBaseEntityMng<ID, ENTITY, DAO>{
	
	private Class<ENTITY> entityClass;
	
	private IBaseEntityDao<ID, ENTITY> entityDao;

	/**
	 * 注入BaseEntityDao，同时实例化
	 * 
	 * @param dao
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void setEntityDao(IBaseEntityDao<ID, ENTITY> entityDao) {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class) params[1];
		super.baseDao = entityDao;
		this.entityDao = entityDao;

	}
	
	public IBaseEntityDao<ID, ENTITY> getEntityDao() {
		return entityDao;
	}

	@Override
	public ENTITY save(ENTITY entity) {
		// TODO Auto-generated method stub
		return entityDao.save(entity);
	}

	@Override
	public Collection<ENTITY> save(Collection<ENTITY> entities) {
		// TODO Auto-generated method stub
		return entityDao.save(entities);
	}

	@Override
	public ENTITY update(ENTITY entity) {
		// TODO Auto-generated method stub
		return entityDao.update(entity);
	}

	@Override
	public Collection<ENTITY> update(Collection<ENTITY> entities) {
		// TODO Auto-generated method stub
		return entityDao.update(entities);
	}

	@Override
	public Boolean delete(ID id) {
		// TODO Auto-generated method stub
		return entityDao.delete(id);
	}

	@Override
	public Boolean delete(ENTITY entity) {
		// TODO Auto-generated method stub
		return entityDao.delete(entity);
	}

	@Override
	public Boolean delete(Collection<ENTITY> entities) {
		// TODO Auto-generated method stub
		return entityDao.delete(entities);
	}

	@Override
	public Boolean delete(String property, Object... values) {
		// TODO Auto-generated method stub
		return entityDao.delete(property, values);
	}

	@Override
	public ENTITY find(ID id) {
		// TODO Auto-generated method stub
		return entityDao.find(id);
	}

	@Override
	public ENTITY find(ID id, LockModeType lockModeType) {
		// TODO Auto-generated method stub
		return entityDao.find(id, lockModeType);
	}

	@Override
	public List<ENTITY> find(String property, Object... values) {
		// TODO Auto-generated method stub
		return entityDao.find(property, values);
	}

	@Override
	public Pagination find(Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		return entityDao.find(pageNo, pageSize);
	}

	@Override
	public ENTITY update(Updater<ENTITY> updater) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStatusByProperty(Short status, String proname, Collection<ID> ids) {
	}
	

}
