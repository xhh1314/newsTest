package com.hww.base.common.dao;

import java.util.List;

import com.hww.base.common.entity.IBaseTree;

public interface IBaseTreeDao<T extends Number, ENTITY extends IBaseTree<T>>
	extends
		IBaseEntityDao<T, ENTITY> {
	public List<ENTITY> getChildList(T parentId, Short display);

	// 返回完整的树
	public List<ENTITY> getRetrievingFullTree(T userId, Integer siteId, Short status);

	public List<ENTITY> getRetrievingPaths(T categoryId, Integer siteId);
}
