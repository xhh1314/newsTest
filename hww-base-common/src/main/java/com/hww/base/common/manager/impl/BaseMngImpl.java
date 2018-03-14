package com.hww.base.common.manager.impl;


import java.util.List;

import com.hww.base.common.dao.IBaseDao;
import com.hww.base.common.manager.IBaseMng;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;

public class BaseMngImpl implements IBaseMng{
	
	IBaseDao baseDao;

	@Override
	public void update(Finder finder) {
		// TODO Auto-generated method stub
		baseDao.update(finder);
	}

	@Override
	public List<?> find(Finder finder) {
		// TODO Auto-generated method stub
		return baseDao.find(finder);
	}

	@Override
	public Pagination find(Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		return baseDao.find(pageNo, pageSize);
	}

	@Override
	public Pagination find(Finder finder, Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		return baseDao.find(finder, pageNo, pageSize);
	}

}
