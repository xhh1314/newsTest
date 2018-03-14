package com.hww.app.common.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.util.Finder;
import com.google.common.collect.Lists;
import com.hww.app.common.dao.AppCategoryDao;
import com.hww.app.common.entity.AppCategory;
import com.hww.app.common.entity.AppUserRCategory;
import com.hww.app.common.manager.AppCategoryMng;
import com.hww.app.common.vo.AppCategoryVo;

@Service("appCategoryMng")
@Transactional
public class AppCategoryMngImpl extends BaseEntityMngImpl<Long, AppCategory, AppCategoryDao> implements AppCategoryMng {

	AppCategoryDao appCategoryDao;

	public AppCategoryDao getAppCategoryDao() {
		return appCategoryDao;
	}

	@Autowired
	public void setAppCategoryDao(AppCategoryDao appCategoryDao) {
		super.setEntityDao(appCategoryDao);
		this.appCategoryDao = appCategoryDao;
	}

	@Override
	public List<AppCategory> loadAppCategoryByIds(List<Long> columnIds) {
		if (columnIds == null || columnIds.isEmpty()) {
			return Lists.newArrayList();
		}
		Finder finder = Finder.create("from AppCategory where 1=1");
		finder.append(" and columnId in(:columnIds)");
		finder.setParamList("columnIds", columnIds);
		List<AppCategory> list = (List<AppCategory>) find(finder);

		return list == null ? Lists.newArrayList() : list;
	}

	@Override
	public List<AppCategory> loadDefaultColumn() {

		Finder finder = Finder.create("from AppCategory where 1=1");
		finder.append(" and isDefault = 1  and status=1 ");
		List<AppCategory> list = (List<AppCategory>) find(finder);
		return list == null ? Lists.newArrayList() : list;
	}

	@Override
	public List<AppCategory> loadAllColumns() {

		Finder finder = Finder.create("from AppCategory where 1=1");
		finder.append("  and status=1 ");
		List<AppCategory> list = (List<AppCategory>) find(finder);
		return list == null ? Lists.newArrayList() : list;
	}

	public List<AppCategory> selectReaderPaperColumn() {

		return appCategoryDao.selectReaderPaperColumn();

	}

	public List<AppCategory> selectRecommendColumn() {

		return appCategoryDao.selectRecommendColumn();

	}

	@Override
	public AppCategory deleteUserCategory(AppCategory appCategory, Long userId, String imei) {

		return appCategoryDao.deleteUserCategory(appCategory, userId, imei);
	}

	@Override
	public List<HashMap<String, Object>> userSubscribedColumn(Long userId, String imei) {
		return appCategoryDao.userSubscribedColumn(userId, imei);
	}

	@Override
	public List<AppCategory> selectDefaultColumn(Long userId, String imei) {
		return appCategoryDao.selectDefaultColumn(userId, imei);
	}

	@Override
	public List<AppCategory> selectAppCategoryInfo(AppCategory appCategory) {
		return appCategoryDao.selectAppCategoryInfo(appCategory);
	}

	public List<HashMap<String, Object>> confirmCategoryOfReadPaper(Long userId, String imei) {

		return appCategoryDao.confirmCategoryOfReadPaper(userId, imei);
	}

	@Override
	public List<AppCategory> getRetrievingFullTree() {

		return appCategoryDao.getRetrievingFullTree();
	}

	@Override
	public List<Long> selectCateIdsByColumnId(Long columnId) {

		return appCategoryDao.selectCateIdsByColumnId(columnId);
	}

	@Override
	public HashMap<String, Object> selectAppCategoryListByPage(AppCategoryVo vo) {

		return appCategoryDao.selectAppCategoryListByPage(vo);
	}

	public List<AppCategory> selectCustomCategoryList() {
		Finder f = Finder.create("FROM AppCategory WHERE 1 = 1");
		f.append("AND isCustom=1 and status=1 ");
		List<AppCategory> list = (List<AppCategory>) appCategoryDao.find(f);
		return list;
	}

	@Override
	public List<AppCategory> selectMidelNodeColumn(Long columnId) {

		return appCategoryDao.selectMidelNodeColumn(columnId);
	}

	@Override
	public List<HashMap<String, Object>> selectLeafNodeColumnByParentId(Long parentId) {

		return appCategoryDao.selectLeafNodeColumnByParentId(parentId);
	}

	@Override
	public AppCategory selectRootNode() {

		return appCategoryDao.selectRootNode();
	}

	@Override
	public List<HashMap<String, Object>> userSubscribedColumnAll(Long userId, String imei) {

		return appCategoryDao.userSubscribedColumnAll(userId, imei);

	}

	@Override
	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:查询叶子节点，不包含固定频道和默认频道
	 * </p>
	 */
	public List<HashMap<String, Object>> selectLeafNodeColumnByParentIdNotContain(long midleNodeId) {

		return appCategoryDao.selectLeafNodeColumnByParentIdNotContain(midleNodeId);
	}

	@Override
	public List<Integer> sort(Integer sort) {
		Finder sql = Finder.create("from AppCategory");
		sql.append("where 1=1");
		sql.append(" and status>0");
		List<AppCategory> listAC = (List<AppCategory>) appCategoryDao.find(sql);
		List<Integer> list = new ArrayList<Integer>();
		if (listAC != null && listAC.size() > 0) {
			for (AppCategory a : listAC) {
				if (a.getSort() != null) {
					list.add(a.getSort());
				}
			}
		}
		HashSet h = new HashSet(list);
		list.clear();
		list.addAll(h);
		return list;
	}
}
