package com.hww.app.admin.service;

import com.hww.app.common.entity.AppCategory;
import com.hww.app.common.vo.AppCategoryVo;
import com.hww.base.common.vo.BaseTreeVo;
import com.hww.base.util.R;

import java.util.List;

public interface AppCategoryService {

	/**
	 * @author XiaoBG
	 * 管理员：多条件查询频道接口
	 * @param AppCategory
	 * @return List<AppCategory>
	 */
	List<AppCategory> selectAppCategoryInfo(AppCategory appCategory);


	/**
	 * @author XiaoBG
	 * 管理员添加频道信息
	 * @param appCategoryVo
	 * @return Integer
	 */
	void saveCategory(AppCategoryVo appCategoryVo);
	
	/**
	 * @author XiaoBG
	 * 管理员删除频道信息
	 * @param columnId
	 * @return Integer
	 */
	void deleteCategory(Long columnId);
	
	/**
	 * @author XiaoBG
	 * 管理员修改频道信息
	 * @param appCategoryVo
	 * @return Integer
	 */
	R updateCategory(AppCategoryVo appCategoryVo);
	

	/**
	 * @author XiaoBG
	 * 管理员：加载频道列表树
	 * @param node 
	 * @return List<AppCategoryVo>
	 */
	List<BaseTreeVo> loadAppCategoryTree(Integer node);
	
	List<Integer> sort(Integer sort);
}
