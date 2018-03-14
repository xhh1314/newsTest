package com.hww.app.common.manager.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.hww.app.common.dao.AppUserRCategoryDao;
import com.hww.app.common.entity.AppCategory;
import com.hww.app.common.entity.AppHotWord;
import com.hww.app.common.entity.AppUserRCategory;
import com.hww.app.common.manager.AppUserRCategoryMng;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.util.Finder;


@Service("appUserRCategoryMng")
@Transactional
public class AppUserRCategoryMngImpl extends BaseEntityMngImpl<Long, AppUserRCategory, AppUserRCategoryDao>
		implements AppUserRCategoryMng {
	
	private AppUserRCategoryDao appUserRCategoryDao;

	public AppUserRCategoryDao getAppUserRCategoryDao() {
		return appUserRCategoryDao;
	}

	@Autowired
	public void setAppUserRCategoryDao(AppUserRCategoryDao appUserRCategoryDao) {
		super.setEntityDao(appUserRCategoryDao);
		this.appUserRCategoryDao = appUserRCategoryDao;
	}
 
	@Override
	public  List<AppUserRCategory> loadMemberRCategoryList(Long userId, String imei){
		 Finder finder = Finder.create("from AppUserRCategory where 1=1");
		 if(userId!=null) {
			 finder.append(" and userId=:userId");
			 finder.setParam("userId", userId);
		 }else if(StringUtils.hasText(imei)) {
			 finder.append(" and imei=:imei");
			 finder.setParam("imei", imei);
		 }else {
			 return Lists.newArrayList();
		 }
		 finder.append("  order by sort asc ");
	     List<AppUserRCategory> list = (List<AppUserRCategory>) appUserRCategoryDao.find(finder);
	       
	     return list==null?Lists.newArrayList():list;
	}
	@Override
	public boolean deletUserRCategory(Long userId, String imei) {
		
		  Finder finder = Finder.create(" delete from AppUserRCategory s ");
		  if(userId!=null) {
				 finder.append(" where userId=:userId");
				 finder.setParam("userId", userId);
			 }else if(StringUtils.hasText(imei)) {
				 finder.append(" where imei=:imei");
				 finder.setParam("imei", imei);
			 }else {
				 return true;
			 }
		  return appUserRCategoryDao.update(finder);
	        
	}
	
	
	@Override
	public boolean deletUserRCategoryByColumnId(Long columnId) {
		 Finder finder = Finder.create(" delete from AppUserRCategory s ");
				 finder.append(" where columnId=:columnId");
				 finder.setParam("columnId", columnId);
		  return appUserRCategoryDao.update(finder);
	}
	
	
	
	
	
	
	/**
	 * @author XiaoBG
	 * 自频道自动排序：查询用户字频道数量
	 */
	@Override
	public AppUserRCategory maxUserCategory(AppUserRCategory appUserRCategory) {
		
		return appUserRCategoryDao.maxUserColumn(appUserRCategory);
	}

	/**
	 * @author XiaoBG
	 * 单点删除：用户删除自频道信息
	 */
	@Override
	public void deleteUserCategory(AppCategory appCategory, Long userId, String imei) {

		appUserRCategoryDao.deleteUserCategory(appCategory, userId, imei);
	}

	/**
	 * @author XiaoBG
	 * 用户查询自频道信息
	 */
	@Override
	public List<HashMap<String, Object>> selectUserCategoryByColumnName(String columnName, Long userId, String imei) {
		
		return appUserRCategoryDao.selectUserCategoryByColumnName(columnName, userId, imei);
	}

	@Override
	public AppUserRCategory confirmUserCategory(Long columnId, Long userId, String imei) {
		
		return appUserRCategoryDao.confirmUserCategory(columnId, userId, imei);
	}
 
	@Override
	/** 
	* <p>Title: </p> 
	* <p>Description: </p>  
	*/
	public List<AppUserRCategory> selectUserSubCategory(Long userId, String imei) {
		return appUserRCategoryDao.selectUserSubCategory(userId, imei);
	}



}
