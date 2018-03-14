package com.hww.framework.common.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import com.alibaba.fastjson.JSONArray;
import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.base.common.entity.IBaseEntity;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.common.util.Updater;

//http://blog.csdn.net/yingxiake/article/details/51019985
public class JpaDaoImpl<ID extends Serializable, ENTITY extends IBaseEntity<ID>>
	implements
		IBaseEntityDao<ID, ENTITY>

{
	private Class<ENTITY> entityClass;
	private EntityManager entityManager;
	private SimpleJpaRepository<ENTITY, ID> simpleJpaRepository;

	/**
	 * 注入EntityManager，同时实例化SimpleJpaRepository
	 * 
	 * @param em
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class) params[1];

		this.simpleJpaRepository = new SimpleJpaRepository<ENTITY, ID>(entityClass, entityManager);

		this.entityManager = entityManager;
	}

	/**
	 * 获取EntityManager，操作jpa api的入口
	 * 
	 * @return
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * 给子类提供simpleJpaRepository实例，用来操作spring data jpa常用的接口
	 * 
	 * @return
	 */
	public SimpleJpaRepository<ENTITY, ID> getSimpleJpaRepository() {
		return simpleJpaRepository;
	}

	/****************************************************************/

	@Override
	public ENTITY save(ENTITY entity) {
		// TODO Auto-generated method stub
		return simpleJpaRepository.save(entity);
	}

	@Override
	public Collection<ENTITY> save(Collection<ENTITY> entities) {
		// TODO Auto-generated method stub
		return simpleJpaRepository.save(entities);
	}

	@Override
	public ENTITY update(ENTITY entity) {
		// TODO Auto-generated method stub
		return simpleJpaRepository.saveAndFlush(entity);
	}

	@Override
	public ENTITY update(Updater<ENTITY> updater) {
		return null;
	}

	@Override
	public Collection<ENTITY> update(Collection<ENTITY> entities) {
		// TODO Auto-generated method stub
		return simpleJpaRepository.save(entities);
	}

	@Override
	public Boolean delete(ID id) {
		// TODO Auto-generated method stub
		simpleJpaRepository.delete(id);
		return true;
	}

	@Override
	public Boolean delete(ENTITY entity) {
		// TODO Auto-generated method stub
		simpleJpaRepository.delete(entity);
		return true;
	}

	@Override
	public Boolean delete(Collection<ENTITY> entities) {
		// TODO Auto-generated method stub
		simpleJpaRepository.delete(entities);
		return true;
	}

	@Override
	public Boolean delete(String property, Object... values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ENTITY find(ID id) {
		// TODO Auto-generated method stub
		return find(id, null);
	}

	@Override
	public ENTITY find(ID id, LockModeType lockModeType) {
		// TODO Auto-generated method stub
		ENTITY ret;
		if (lockModeType != null) {
			ret = entityManager.find(entityClass, id, lockModeType);
		} else {
			ret = entityManager.find(entityClass, id);
		}
		return ret;
	}

	@Override
	public List<ENTITY> find(final String property, final Object... values) {
		// TODO Auto-generated method stub
		return simpleJpaRepository.findAll(new Specification<ENTITY>() {
			@Override
			public Predicate toPredicate(Root<ENTITY> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Path<String> expression = root.get(property);// 获取属性路径
				Predicate condition = expression.in(values);
				return condition;
			}
		});
	}

	@Override
	public Pagination find(Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		Page<ENTITY> p = simpleJpaRepository.findAll(new PageRequest(pageNo, pageSize));
		Pagination ret = new Pagination(p.getNumber(), p.getSize(), p.getTotalElements(), p.getContent());
		return ret;
	}

	@Override
	public Boolean update(Finder finder) {
		// TODO Auto-generated method stub
		finder.createQuery(entityManager).executeUpdate();
		return true;
	}

	@Override
	public List<?> find(Finder finder) {
		// TODO Auto-generated method stub
		return finder.createQuery(entityManager).getResultList();
	}

	/**
	 * 根据sql返回一个list集合，使用泛型
	 * @param finder
	 * @return
	 */
	public List<ENTITY> findWithGenericity(Finder finder){
		return finder.createQuery(entityManager).getResultList();
	}

	/**
	 * 使用原生sql查询
	 * @param finder
	 * @param o
	 * @return
	 */
	@Override
    public List<?> findJoin(Finder finder,Class<?> o) {
        Query query = finder.createNativeQuery(entityManager);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return JSONArray.parseArray(JSONArray.toJSONString(query.getResultList()), o);
    }

	/**
	 * 使用HQL分页查询
	 * @param finder
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Override
	public Pagination find(Finder finder, Integer pageNo, Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 20;
		}
		Long totalCount = countQueryResult(finder);
		Pagination p = new Pagination(pageNo, pageSize, totalCount);
		if (totalCount < 1) {
			p.setList(new ArrayList());
			return p;
		}
		Query query = entityManager.createQuery(finder.getOrigHql());
		finder.setParamsToQuery(query);
		query.setFirstResult(p.getFirstResult());
		query.setMaxResults(p.getPageSize());
		List<?> list = query.getResultList();
		p.setList(list);
		return p;
	}

	/**
	 * 使用原生sql分页查询
	 * @param finder
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */

	@Deprecated
	public Pagination findByPageWithNative(Finder finder, Integer pageNo, Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 20;
		}
		Long totalCount = countQueryResultWithNative(finder);
		Pagination p = new Pagination(pageNo, pageSize, totalCount);
		if (totalCount < 1) {
			p.setList(new ArrayList());
			return p;
		}
		Query query =entityManager.createNativeQuery(finder.getOrigHql(),entityClass);
		finder.setParamsToQuery(query);
		query.setFirstResult(p.getFirstResult());
		query.setMaxResults(p.getPageSize());
		List<ENTITY> list = query.getResultList();
		p.setList(list);
		return p;
	}

	@Override
	public Long countQueryResult(Finder finder) {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(finder.getRowCountHql());
		finder.setParamsToQuery(query);
		return ((Number) query.getSingleResult()).longValue();
	}

   //测试冲突
	//
	//
	private Long countQueryResultWithNative(Finder finder) {
		// TODO Auto-generated method stub
		Finder newfinder=Finder.create(finder.getRowCountHql());
		newfinder.setParams(finder.getParams(),finder.getValues());
		Query query = newfinder.createNativeQuery(entityManager);
		newfinder.setParamsToQuery(query);
		return ((Number) query.getSingleResult()).longValue();
	}
	
	public Integer countQueryResultWithNativeInteger(Finder finder) {
		// TODO Auto-generated method stub
		Finder newfinder=Finder.create(finder.getRowCountHql());
		newfinder.setParams(finder.getParams(),finder.getValues());
		Query query = newfinder.createNativeQuery(entityManager);
		newfinder.setParamsToQuery(query);
		return ((Number) query.getSingleResult()).intValue();
	}

}
