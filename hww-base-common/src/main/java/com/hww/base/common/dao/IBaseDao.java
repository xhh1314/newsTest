package com.hww.base.common.dao;

import java.util.List;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;

public interface IBaseDao {
	/**
	 * 更新
	 * 
	 * @param entity
	 * @return
	 */
	public Boolean update(Finder finder);
	
	public List<?> find(Finder finder);
	
	public List<?> findJoin(Finder finder,Class<?> o);
	
	/**
	 * 分页查找
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination find(Integer pageNo, Integer pageSize);
	
	public Pagination find(Finder finder, Integer pageNo, Integer pageSize);

	public Long countQueryResult(Finder finder);
}
