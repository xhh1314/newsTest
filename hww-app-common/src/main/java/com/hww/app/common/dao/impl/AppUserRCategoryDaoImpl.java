package com.hww.app.common.dao.impl;

import com.hww.app.common.dao.AppUserRCategoryDao;
import com.hww.app.common.entity.AppCategory;
import com.hww.app.common.entity.AppUserRCategory;
import com.hww.base.common.util.Finder;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;


@Repository("appUserRCategoryDao")
public class AppUserRCategoryDaoImpl extends LocalEntityDaoImpl<Long, AppUserRCategory> implements AppUserRCategoryDao {

	@Override
	public AppUserRCategory maxUserColumn(AppUserRCategory appUserRCategory) {
		
	    Finder f = Finder.create("select user_column_id as userColumnId,sort from app_user_r_category");
	    f.append(" where 1 = 1");
	    if(appUserRCategory.getUserId()!=null) {
	      f.append(" and user_id = :userId");
	      f.setParam("userId", appUserRCategory.getUserId());
	    }
	    if(appUserRCategory.getImei()!=null) {
		      f.append(" and imei = :imei");
		      f.setParam("imei", appUserRCategory.getImei());
		}
		f.append(" AND app_user_r_category.status =:status");
		f.setParam("status", new Short("1"));
	    f.append(" order by sort desc");

	    List<AppUserRCategory> list = (List<AppUserRCategory>) findJoin(f, AppUserRCategory.class);
	    if(list!=null&&list.size()>0) {
	      return list.get(0);
	    }
	    AppUserRCategory appUserRCategory2 = new AppUserRCategory();
	    return appUserRCategory2;
	} 

	@Override
	public void deleteUserCategory(AppCategory appCategory, Long userId, String imei) {
		
		Finder f = Finder.create("from AppUserRCategory where 1=1");
		f.append(" and columnId =:id");
		f.setParam("id", appCategory.getColumnId());
		//如果用户已登录，则优先采用用户id作为查询信息
		if(userId!=null) {
			f.append(" and userId=:userId");
			f.setParam("userId", userId);
		}
		//否则根据imei查询
		else {
			f.append(" and imei=:imei");
			f.setParam("imei", imei);
		}
		List<AppUserRCategory> appUserRCategoryList = (List<AppUserRCategory>) find(f);

		for (AppUserRCategory a : appUserRCategoryList) {
			delete(a.getUserColumnId());
		}
		
	}

	@Override
	public List<HashMap<String, Object>> selectUserCategoryByColumnName(String columnName, Long userId, String imei) {
	
		//查询用户频道关联表找到频道id
		Finder f = Finder.create("SELECT app_category.* FROM app_category");
		f.append("LEFT JOIN app_user_r_category ON app_category.column_id = app_user_r_category.column_id");
		f.append("WHERE 1=1");
		f.append("AND column_name =:columnName");
		f.setParam("columnName", columnName);
		//如果用户已登录，则优先采用用户id作为查询信息
		if(userId!=null) {
			f.append(" and user_id=:userId");
			f.setParam("userId", userId);
		}
		//否则根据imei查询
		else {
			f.append(" and imei=:imei");
			f.setParam("imei", imei);
		}
		f.append(" AND app_user_r_category.status =:status");
		f.setParam("status", new Short("1"));
		List<HashMap<String, Object>> appUserRCategoryList = (List<HashMap<String, Object>>) findJoin(f, HashMap.class);
		
		return appUserRCategoryList;
	}

	@Override
	public AppUserRCategory confirmUserCategory(Long columnId, Long userId, String imei) {
		
		Finder f = Finder.create("from AppUserRCategory where 1=1");
		f.append(" and columnId =:id");
		f.setParam("id", columnId);
		//如果用户已登录，则优先采用用户id作为查询信息
		if(userId!=null) {
			f.append(" and userId=:userId");
			f.setParam("userId", userId);
		}
		//否则根据imei查询
		else {
			f.append(" and Imei=:imei");
			f.setParam("imei", imei);
		}
		List<AppUserRCategory> appUserRCategoryList = (List<AppUserRCategory>) find(f);
		
		if(appUserRCategoryList.size() != 0) {
			return appUserRCategoryList.get(0);
		} else {
			AppUserRCategory appUserRCategory = new AppUserRCategory();
			return appUserRCategory;
		}
		
	}

	@Override
	public List<AppUserRCategory> selectUserSubCategory(Long userId, String imei) {
		
		Finder f = Finder.create("from AppUserRCategory where 1=1");
		//如果用户已登录，则优先采用用户id作为查询信息
		if(userId!=null) {
			f.append(" and userId=:userId");
			f.setParam("userId", userId);
		}
		else if(userId==null&imei!=null) {
			f.append(" and imei=:imei");
			f.setParam("imei", imei);
		}
		else {
			f.append(" and userId=:userId");
			f.setParam("userId", Long.parseLong("-1"));
		}
		f.append(" AND status =:status");
		f.setParam("status", new Short("1"));
		List<AppUserRCategory> appUserRCategoryList = (List<AppUserRCategory>) find(f);
		
		return appUserRCategoryList;
	} 
	
}
