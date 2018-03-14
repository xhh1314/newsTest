package com.hww.app.webservice.service;

import com.hww.app.common.entity.AppCategory;
import com.hww.app.common.vo.AppCategoryVo;
import com.hww.app.common.vo.AppHomeCategoryVo;
import com.hww.app.common.vo.ZAppHomeCategoryVo;
import com.hww.base.util.R;

import java.util.List;

public interface AppCategoryService {

	public  List<AppCategory> loadAllEnableColumns();

	ZAppHomeCategoryVo loadUserAppCate(Long userId, String imei);
	
	void userCategorySorting2(String columnSorting, Long userId, String imei);
	
	/**
	 * @author XiaoBG
	 * 全局：APP频道首页信息展示
	 * @param userId,imei
	 * @return hashMap<String, Object> 
	 */
	AppHomeCategoryVo queryCategoryList(Long userId, String imei);
    

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
	 * @param appCategory
	 * @return Integer
	 */
	void deleteCategory(AppCategory appCategory);
	
	/**
	 * @author XiaoBG
	 * 管理员修改频道信息
	 * @param appCategoryVo
	 * @return Integer
	 */
	R updateCategory(AppCategoryVo appCategoryVo);
	
	/**
	 * @author XiaoBG
	 * 用户：订阅频道接口
	 * @param columnId, userId, imei
	 * @return String
	 */
	R saveUserCategory(Long columnId, Long userId, String imei);
    
	/**
	 * @author XiaoBG
	 * 用户删除自频道信息
	 * @param columnId, userId, imei
	 * @return String
	 */
	void deleteUserCategory(Long columnId, Long userId, String imei);
	
	/**
	 * @author XiaoBG
	 * 用户查询自频道信息
	 * @param columnName, userId, imei
	 * @return String
	 */
	AppCategoryVo selectUserCategory(String columnName, Long userId, String imei);

	/**
	 * @author XiaoBG
	 * 用户自频道排序
	 * 排序的同时进行业务逻辑判断，添加或取消订阅接口
	 * @param imei 
	 * @param userId 
	 * @param String,排序后的频道字符串
	 * @return List<HashMap<String, Object>>
	 */
	void userCategorySorting(String columnSorting, Long userId, String imei);

	/**
	 * 用户：根据频道接口查询新闻Id列表
	 *
	 * @author XiaoBG
	 * @date 2018年1月11日 下午9:45:14
	 * param Long
	 * @return List<Long>
	 * @version v0.1
	 */
	List<Long> loadCateIdsByColumnId(Long columnId);
	
	  List<AppCategory> loadCustomCategoryList();

	



}
