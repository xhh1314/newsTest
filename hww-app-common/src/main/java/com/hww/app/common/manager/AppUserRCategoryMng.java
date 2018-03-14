package com.hww.app.common.manager;

import java.util.HashMap;
import java.util.List;

import com.hww.app.common.dao.AppUserRCategoryDao;
import com.hww.app.common.entity.AppCategory;
import com.hww.app.common.entity.AppUserRCategory;
import com.hww.base.common.manager.IBaseEntityMng;


public interface AppUserRCategoryMng extends IBaseEntityMng<Long, AppUserRCategory, AppUserRCategoryDao> {
	
	List<AppUserRCategory> loadMemberRCategoryList(Long userId, String imei);
	
	boolean deletUserRCategory(Long userId, String imei);
	
	boolean deletUserRCategoryByColumnId(Long columnId);
	
	

	AppUserRCategory maxUserCategory(AppUserRCategory appUserRCategory);

	/**
	 * @author XiaoBG
	 * 用户删除自频道信息
	 */
	void deleteUserCategory(AppCategory appCategory, Long userId, String imei);
	
	/**
	 * @author XiaoBG
	 * 用户查询自频道信息
	 */
	List<HashMap<String, Object>> selectUserCategoryByColumnName(String columnName, Long userId, String imei);
 
	/**
	 * @author XiaoBG
	 * 判断用户是否订阅了频道
	 */
	AppUserRCategory confirmUserCategory(Long columnId, Long userId, String imei);
	
	/**
	 * @author XiaoBG
	 * 查询用户订阅的频道
	 */
	List<AppUserRCategory> selectUserSubCategory(Long userId, String imei);

	
	
}
