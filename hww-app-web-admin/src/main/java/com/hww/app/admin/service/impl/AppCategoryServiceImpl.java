package com.hww.app.admin.service.impl;

import com.hww.app.admin.service.AppCategoryService;
import com.hww.app.common.entity.AppCategory;
import com.hww.app.common.entity.AppCategoryRCmsCategory;
import com.hww.app.common.manager.AppCategoryMng;
import com.hww.app.common.manager.AppCategoryRCmsCategoryMng;
import com.hww.app.common.vo.AppCategoryVo;
import com.hww.base.common.util.Finder;
import com.hww.base.common.vo.BaseTreeVo;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.CopyBean;
import com.hww.base.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service("appCategoryService")
@Transactional
public class AppCategoryServiceImpl implements AppCategoryService {

	@Autowired
	private AppCategoryMng appCategoryMng;

	@Autowired
	private AppCategoryRCmsCategoryMng appCategoryRCmsCategoryMng;
	
	

	@Override
	// 管理员接口
	public void saveCategory(AppCategoryVo appCategoryVo) {

		// 方法：按照管理员输入的顺序添加
		// AppCategory insertAppCategory = new AppCategory();
		if (appCategoryVo != null) {
			AppCategory insertAppCategory = BeanMapper.map(appCategoryVo, AppCategory.class);
			// 默认为1,0为读报和推荐两个栏目,不可修改
			insertAppCategory.setSpecialType(1);
			insertAppCategory.setCreateTime(new Timestamp(System.currentTimeMillis()));
			insertAppCategory.setParent(appCategoryMng.find(appCategoryVo.getParentId()));
			AppCategory entity = appCategoryMng.save(insertAppCategory);
			if (entity != null && appCategoryVo.getCmsCategoryIds() != null
					&& appCategoryVo.getCmsCategoryIds().length() > 0) {
				String[] ids = appCategoryVo.getCmsCategoryIds().split(",");
				for (String id : ids) {
					AppCategoryRCmsCategory r = new AppCategoryRCmsCategory();
					r.setCategoryId(Long.parseLong(id));
					r.setColumnId(entity.getColumnId());
					r.setStatus(Short.valueOf("1"));
					r.setSiteId(1);
					r.setCreateTime(new Timestamp(System.currentTimeMillis()));
					r.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
					appCategoryRCmsCategoryMng.save(r);
				}
			}
		}

	}

	@Override
	public List<AppCategory> selectAppCategoryInfo(AppCategory appCategory) {

		if (appCategory != null) {
			List<AppCategory> selectAppCategoryInfo = appCategoryMng.selectAppCategoryInfo(appCategory);
			return selectAppCategoryInfo;
		}
		List<AppCategory> list = new ArrayList<AppCategory>();
		return list;
	}

	@Override
	public void deleteCategory(Long columnId) {
		if (columnId != null) {
			AppCategory appCate = appCategoryMng.find(columnId);
			if (appCate.getSpecialType() == null || appCate.getSpecialType() != 0) {
				List<AppCategory> list = new ArrayList<>();
				list.add(appCate);
				while (list.size() > 0) {
					AppCategory cur = list.get(0);
					Finder hql = Finder.create("from AppCategory where 1=1");
					hql.append(" and parent.columnId=:ColumnIdP").setParam("ColumnIdP", cur.getColumnId());
					hql.append(" and status>0");
					List<AppCategory> listAdd = (List<AppCategory>) appCategoryMng.find(hql);
					list.addAll(listAdd);
					cur.setStatus(Short.valueOf("0"));
					appCategoryMng.update(cur);
					list.remove(0);
				}
			}
			
			List<AppCategoryRCmsCategory>  list=appCategoryRCmsCategoryMng.findByCategoryId(columnId);
			for(AppCategoryRCmsCategory l:list) {
				l.setStatus(Short.valueOf("0"));
				appCategoryRCmsCategoryMng.update(l);
			}
			
			
			
		}
	}

	@Override
	public R updateCategory(AppCategoryVo appCategoryVo) {
		if (appCategoryVo != null) {
			AppCategory find = appCategoryMng.find(appCategoryVo.getColumnId());
			// 特殊频道不允许修改
			if (find.getSpecialType() == 1) {
				AppCategory appCategory = new AppCategory();
				CopyBean.copyNotNull(appCategoryVo, appCategory);
				// 以下内容允许修改
				appCategory.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
				appCategory.setColumnName(appCategoryVo.getColumnName());
				appCategory.setColumnDesc(appCategoryVo.getColumnDesc());
				// 以下内容不允许修改

				appCategory.setIsCustom(appCategoryVo.getIsCustom());
				appCategory.setIsDefault(appCategoryVo.getIsDefault());
				appCategory.setSort(appCategoryVo.getSort());

				appCategory.setLogo(find.getLogo());
				appCategory.setSiteId(appCategoryVo.getSiteId());

				appCategory.setColumnId(find.getColumnId());
				appCategory.setParent(appCategoryMng.find(find.getParentId()));
				appCategory.setColumnTypeId(find.getColumnTypeId());
				appCategory.setCreateTime(find.getCreateTime());
				appCategory.setSpecialType(find.getSpecialType());
				appCategory.setStatus(appCategoryVo.getStatus());
				appCategory.setIsDisplay(appCategoryVo.getIsDisplay());
				appCategoryMng.update(appCategory);
				if (appCategory.getColumnId() != null) {
					List<AppCategoryRCmsCategory> list = appCategoryRCmsCategoryMng
							.findByCategoryId(appCategory.getColumnId());

					if (appCategoryVo.getCmsCategoryIds() != null && appCategoryVo.getCmsCategoryIds().length() > 0) {
						String[] ids = appCategoryVo.getCmsCategoryIds().split(",");
						for (String id : ids) {
							int i=0;
							for (i = 0; i < list.size(); i++) {
								AppCategoryRCmsCategory r = list.get(i);
								if (r.getCategoryId() == Long.parseLong(id)) {
									r.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
									r.setStatus((short) 1);
									appCategoryRCmsCategoryMng.update(r);
									list.remove(i);
									i--;
									break;
								}
							}
							if(i==list.size()) {
								AppCategoryRCmsCategory newr = new AppCategoryRCmsCategory();
								newr.setCategoryId(Long.parseLong(id));
								newr.setColumnId(appCategory.getColumnId());
								newr.setStatus(Short.valueOf("1"));
								newr.setSiteId(1);
								newr.setCreateTime(new Timestamp(System.currentTimeMillis()));
								newr.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
								appCategoryRCmsCategoryMng.save(newr);
							}

						}
						for(AppCategoryRCmsCategory deleteR:list) {
							deleteR.setStatus((short) 0);
							deleteR.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
							appCategoryRCmsCategoryMng.update(deleteR);
						}
						
					}
				}

				return R.ok("更新成功");
			}
			CopyBean.copyNotNull(appCategoryVo, find);
			find.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
			find.setParent(appCategoryMng.find(appCategoryVo.getParentId()));
			appCategoryMng.update(find);
			return R.ok("更新成功");
		}
		return R.ok(" ");

	}

	@Override
	public List<BaseTreeVo> loadAppCategoryTree(Integer node) {

		if (node == null) {
			node = 0;
		}
		List<BaseTreeVo> tree = new ArrayList<BaseTreeVo>();
		List<AppCategory> categoryList = appCategoryMng.getRetrievingFullTree();

		for (AppCategory AppCategory : categoryList) {
			BaseTreeVo baseTreeVo = new BaseTreeVo();
			baseTreeVo.setId(AppCategory.getColumnId());
			baseTreeVo.setChkDisabled(false);
			if (AppCategory.getParentId() != null) {
				baseTreeVo.setIsParent(false);
			} else {
				baseTreeVo.setIsParent(true);
			}
			if (AppCategory.getParent() != null) {
				baseTreeVo.setpId(AppCategory.getParent().getColumnId());
			} else {
				baseTreeVo.setpId(Long.parseLong("0"));
			}

			baseTreeVo.setName(AppCategory.getColumnName());
			baseTreeVo.setAccessPath("category/v_list.do?categoryId=" + AppCategory.getColumnId());
			baseTreeVo.setChecked(false);
			tree.add(baseTreeVo);
		}
		return tree;
	}

	@Override
	public List<Integer> sort(Integer sort) {
		// TODO Auto-generated method stub
		return appCategoryMng.sort(sort);
	}

}
