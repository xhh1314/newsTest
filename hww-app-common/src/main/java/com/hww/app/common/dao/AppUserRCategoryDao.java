package com.hww.app.common.dao;


import java.util.HashMap;
import java.util.List;

import com.hww.app.common.entity.AppCategory;
import com.hww.app.common.entity.AppUserRCategory;
import com.hww.base.common.dao.IBaseEntityDao;


public interface AppUserRCategoryDao extends IBaseEntityDao<Long, AppUserRCategory> {

	/**
	 * @author XiaoBG
	 * 用于自频道排序
	 */
	AppUserRCategory maxUserColumn(AppUserRCategory appUserRCategory);

	/**
	 * @author XiaoBG
	 * 删除用户字频道信息
	 */
	void deleteUserCategory(AppCategory appCategory, Long userId, String imei);

	/**
	 * @author XiaoBG
	 * 根据频道名称查询用户自频道信息
	 * @return 
	 */
	List<HashMap<String, Object>> selectUserCategoryByColumnName(String columnName, Long userId, String imei);

	/**
	 * @author XiaoBG
	 * 判读用户是否订阅了某频道
	 * @param: columnId, userId, imei
	 * @return AppUserRCategory
	 */
	AppUserRCategory confirmUserCategory(Long columnId, Long userId, String imei);
	/**
	 * @author XiaoBG
	 * 查询用户订阅的频道
	 * @param: userId, imei
	 * @return AppUserRCategory
	 */
	List<AppUserRCategory> selectUserSubCategory(Long userId, String imei);
	
}
