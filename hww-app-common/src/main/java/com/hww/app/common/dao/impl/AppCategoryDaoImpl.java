package com.hww.app.common.dao.impl;

import java.util.HashMap;
import java.util.List;
import com.hww.app.common.entity.AppCategory;
import com.hww.app.common.entity.AppUserRCategory;
import com.hww.app.common.vo.AppCategoryVo;
import org.springframework.stereotype.Repository;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.StringUtils;
import com.hww.app.common.dao.AppCategoryDao;

@Repository("appCategoryDao")
public class AppCategoryDaoImpl extends LocalEntityDaoImpl<Long, AppCategory> implements AppCategoryDao {

	@Override
	public List<AppCategory> selectAppCategoryInfo(AppCategory appCategory) {
		
		Finder f = Finder.create("from AppCategory where 1=1");
		if(appCategory.getColumnId()!=null) {
			f.append(" and columnId =:id");
			f.setParam("id", appCategory.getColumnId());
		}
		if(appCategory.getSpecialType()!=null) {
			f.append(" and specialType =:specialType");
			f.setParam("specialType", appCategory.getSpecialType());
		}
		if(appCategory.getColumnName()!=null) {
			f.append(" and columnName =:ColumnName");
			f.setParam("ColumnName", appCategory.getColumnName());
		}
		if(appCategory.getSort()!=null) {
			f.append(" and sort =:sort");
			f.setParam("sort", appCategory.getSort());
		}
		if(appCategory.getStatus()!=null) {
			f.append(" and status =:status");
			f.setParam("status", appCategory.getStatus());
		}
		if(appCategory.getParentId()!=null) {
			f.append(" and parentId =:parentId");
			f.setParam("parentId", appCategory.getParentId());
		}

		List<AppCategory> appCategoryList = (List<AppCategory>) find(f);

		return appCategoryList;
	}

	@Override
	public AppCategory deleteUserCategory(AppCategory appCategory, Long userId, String imei) {
		
		Finder f = Finder.create("from AppUserRCategory where 1=1");
		f.append(" and columnId =:id");
		f.setParam("id", appCategory.getColumnId());
		if(userId!=null) {
			f.append(" and user_id=:userId");
			f.setParam("userId", userId);
		}
		else if(userId==null&imei!=null) {
			f.append(" and imei=:imei");
			f.setParam("imei", imei);
		}
		else {
			f.append(" and user_id=:userId");
			f.setParam("userId", Long.parseLong("-1"));
		}
		List<AppUserRCategory> appUserRCategoryList = (List<AppUserRCategory>) find(f);

		if (null != appUserRCategoryList || appUserRCategoryList.size() != 0) {
			for (AppUserRCategory a : appUserRCategoryList) {
				delete(a.getColumnId());
			}
		}
		AppCategory appCategory2 = new AppCategory();
		return appCategory2;

	}
	@Override
	public List<HashMap<String, Object>> userSubscribedColumn(Long userId, String imei) {
		
	    Finder f = Finder.create("SELECT app_category.* FROM app_category LEFT JOIN app_user_r_category ON");
	    f.append("  app_user_r_category.column_id = app_category.column_id");
	    f.append(" WHERE 1 = 1 ");
	    f.append(" AND special_type =:specialType");
	    f.append(" AND app_category.column_id in ");
	    f.append(" (");
	    f.append("SELECT column_id FROM app_user_r_category WHERE 1=1");
	    f.append(" )");
		if(userId!=null) {
			f.append(" and user_id=:userId");
			f.setParam("userId", userId);
		}
		else if(userId==null&imei!=null) {
			f.append(" and imei=:imei");
			f.setParam("imei", imei);
		}
		else {
			f.append(" and user_id=:userId");
			f.setParam("userId", Long.parseLong("-1"));
		}
		f.append(" AND app_user_r_category.status =:status");
		f.setParam("status", new Short("1"));
		//不包含读报和推荐两个栏目
		f.setParam("specialType", 1);
		//不包含默认栏目
	    f.append("ORDER BY app_user_r_category.sort asc");
		
	    List<HashMap<String,Object>> list = (List<HashMap<String, Object>>) findJoin(f, HashMap.class);
	    return list;
	    
	}

	@Override
	public List<AppCategory> selectDefaultColumn(Long userId, String imei) {
	    Finder f = Finder.create("FROM AppCategory WHERE isDefault = 1 AND");
	    f.append("columnId NOT IN ");
	    f.append(" (");
	    f.append(" SELECT columnId from AppUserRCategory WHERE 1=1");
		if(userId!=null) {
			f.append(" and userId=:userId");
			f.setParam("userId", userId);
		}
		else if(userId==null&imei!=null) {
			f.append(" and imei=:imei");
			f.setParam("imei", imei);
		}
		else {
			f.append("and userId=:userId");
			/*f.setParam("userId", Long.parseLong("-1"));*/
			f.setParam("userId", Long.parseLong("-1"));
		}
	    f.append(")");
	    List<AppCategory> list = (List<AppCategory>) find(f);
	    return list;
	}

	@Override
	public List<AppCategory> selectReaderPaperColumn() {
	    Finder f = Finder.create("FROM AppCategory WHERE columnId =:columnId");
	    f.append(" and specialType=:specialType");
	    f.setParam("columnId", Long.parseLong("2"));
	    f.setParam("specialType", 0);
	    List<AppCategory> list = (List<AppCategory>) find(f);
	    return list;
	}

	@Override
	public List<AppCategory> selectRecommendColumn() {
	    Finder f = Finder.create("FROM AppCategory WHERE columnId =:columnId");
	    f.append(" and specialType=:specialType");
	    f.setParam("columnId", Long.parseLong("1"));
	    f.setParam("specialType", 0);
	    List<AppCategory> list = (List<AppCategory>) find(f);
	    return list;
	}
	
	@Override
	public List<HashMap<String,Object>> confirmCategoryOfReadPaper(Long userId, String imei) {
	    Finder f = Finder.create("SELECT app_user_r_category.user_id, app_user_r_category.imei, app_user_r_category.column_id,");
	    f.append("  app_category.column_name, app_user_r_category.sort FROM app_user_r_category");
	    f.append("LEFT JOIN app_category ON app_user_r_category.column_id = app_category.column_id");
	    f.append("WHERE 1=1 AND app_user_r_category.column_id IN ");
	    f.append("( SELECT column_id FROM app_category WHERE 1 = 1 AND column_name = '读报' OR column_name = '推荐' )");
		if(userId!=null) {
			f.append(" and user_id=:userId");
			f.setParam("userId", userId);
		}
		else if(userId==null&imei!=null) {
			f.append(" and imei=:imei");
			f.setParam("imei", imei);
		}
		else {
			f.append(" and user_id=:userId");
			f.setParam("userId", Long.parseLong("-1"));
		}
		
		List<HashMap<String,Object>> list = (List<HashMap<String, Object>>) findJoin(f, HashMap.class);
		
		return list;
	}

	public List<AppCategory> getRetrievingFullTree() {
		
	    Finder f = Finder.create("FROM AppCategory WHERE 1=1");
	    f.append(" and status !=:status" );
	    
	    f.setParam("status", Short.valueOf("0"));
	    List<AppCategory> list = (List<AppCategory>) find(f);
	    return list;
	}

	@Override
	public List<Long> selectCateIdsByColumnId(Long columnId) {

	    Finder f = Finder.create("select categoryId FROM AppCategoryRCmsCategory  WHERE 1=1");
	    f.append(" and columnId =:columnId");
	    f.setParam("columnId", columnId);
	    
	    List<Long> categoryIds = (List<Long>) find(f);
	    
	    return categoryIds;
	    
	}

	@Override
	public HashMap<String, Object> selectAppCategoryListByPage(AppCategoryVo vo) {
		
		Integer siteId=1;
		
		Finder hql = Finder.create("from");
		hql.append(AppCategory.class.getName());
		
		hql.append(" where 1=1");
		
		if (vo.getParentId()!=null) {
			hql.append(" and parent.categoryId=:pCategoryId ").setParam("pCategoryId", vo.getParentId());
		}
		
		
		hql.append(" and siteId=:siteIdP").setParam("siteIdP", siteId);
		if (StringUtils.isNotBlank(vo.getColumnName())) {
			hql.append(" and categoryName like :categoryNameP").setParam("categoryNameP",
					"%" + vo.getColumnName() + "%");
		}
		if (vo.getStatus() != null) {
			hql.append(" and status=:statusP").setParam("statusP", vo.getStatus());
		}
		Pagination p = find(hql, 1, 10);
		HashMap<String, Object> resultHash = new HashMap<String, Object>();
		resultHash.put("page", p);
		resultHash.put("vo", vo);
		return resultHash;

	}

	@Override
	public List<AppCategory> selectMidelNodeColumn(Long columnId) {
	    Finder f = Finder.create("FROM AppCategory WHERE 1 = 1");
	    f.append("AND columnId=:columnId ");
	    f.setParam("columnId", columnId);
		
	    List<AppCategory> list = (List<AppCategory>) find(f);
	    return list;
	}
	
	@Override
	public List<HashMap<String, Object>> selectLeafNodeColumnByParentId(Long parentId) {
	    Finder f = Finder.create("SELECT app_category.* FROM app_category WHERE 1=1 ");
	    f.append("AND parent_id=:parentId ");
	    /*f.append("AND is_default =:default");*/
	    f.append("ORDER BY sort");
	    f.setParam("parentId", parentId);
	    /*f.setParam("default", 0);*/
		
		List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) findJoin(f, HashMap.class);
	    return list;
	} 

	@Override
	public AppCategory selectRootNode() {
		
	    Finder f = Finder.create("FROM AppCategory WHERE 1=1 AND columnId =:columnId");
	    f.setParam("columnId", Long.parseLong("19"));
	    List<AppCategory> list = (List<AppCategory>) find(f);
	    if(list!=null&&list.size()==1) {
	    	return list.get(0);
	    }
	    AppCategory appCategory = new AppCategory();
	    return appCategory;
	}

	@Override
	public List<HashMap<String, Object>> userSubscribedColumnAll(Long userId, String imei) {
		 
	    Finder f = Finder.create("SELECT app_category.* FROM app_category LEFT JOIN app_user_r_category ON");
	    f.append("  app_user_r_category.column_id = app_category.column_id");
	    f.append(" WHERE 1 = 1 ");
	    f.append(" AND app_category.column_id in ");
	    f.append(" (");
	    f.append("SELECT column_id FROM app_user_r_category WHERE 1=1");
	    f.append(" )");
		if(userId!=null) {
			f.append(" and user_id=:userId");
			f.setParam("userId", userId);
		}
		else if(userId==null&imei!=null) {
			f.append(" and imei=:imei");
			f.setParam("imei", imei);
		}
		else {
			f.append(" and user_id=:userId");
			f.setParam("userId", Long.parseLong("-1"));
		}
		f.append(" AND app_user_r_category.status =:status");
		f.setParam("status", new Short("1"));
	    f.append("ORDER BY app_user_r_category.sort asc");
		
	    List<HashMap<String,Object>> list = (List<HashMap<String, Object>>) findJoin(f, HashMap.class);
	    return list;
	}

	@Override
	public List<HashMap<String, Object>> selectLeafNodeColumnByParentIdNotContain(long parentId) {
		
	    Finder f = Finder.create("SELECT app_category.* FROM app_category WHERE 1=1 ");
	    f.append("AND parent_id=:parentId ");
	    f.append("AND is_default =:default");
	    f.append("ORDER BY sort");
	    f.setParam("parentId", parentId);
	    f.setParam("default", 0);
		
		List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) findJoin(f, HashMap.class);
	    return list;
	}
	
	
}
