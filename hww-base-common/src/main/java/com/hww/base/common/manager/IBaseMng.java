package com.hww.base.common.manager;

import java.util.List;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;

public interface IBaseMng
{
	/**
	 * 更新
	 * 
	 * @param entity
	 * @return
	 */
	public void update(Finder finder);
	
	public List<?> find(Finder finder);
	
	/**
	 * 分页查找
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination find(Integer pageNo, Integer pageSize);
	
	public Pagination find(Finder finder, Integer pageNo, Integer pageSize);
}
