package com.hww.cms.webadmin.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.CopyBean;
import com.hww.cms.common.Constants;
import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.entity.CmsSpecial;
import com.hww.cms.common.entity.CmsSpecialRCategory;
import com.hww.cms.common.manager.CmsCategoryMng;
import com.hww.cms.common.manager.CmsSpecialMng;
import com.hww.cms.common.manager.CmsSpecialRCategoryMng;
import com.hww.cms.common.vo.CmsCategoryVo;
import com.hww.cms.common.vo.CmsSpecialRCategoryVo;
import com.hww.cms.common.vo.CmsSpecialVo;
import com.hww.cms.webadmin.service.CmsSpecialRCategoryService;
import com.hww.cms.webadmin.service.CmsSpecialService;

@Service("cmsSpecialService")
@Transactional
public class CmsSpecialServiceImpl implements CmsSpecialService {

	private static final Logger log = LoggerFactory.getLogger(CmsSpecialServiceImpl.class);

	@Autowired
	CmsCategoryMng cmsCategoryMng;
	@Autowired
	CmsSpecialMng cmsSpecialMng;
	@Autowired
	CmsSpecialRCategoryMng cmsSpecialRCategoryMng;

	@Override
	@Transactional
	public void saveOrUpdate(CmsSpecialVo vo) {
		Long specialId = vo.getSpecialId();
		if (specialId != null) {
			// 更新
			CmsSpecial entity = cmsSpecialMng.find(vo.getSpecialId());
			if (entity != null) {
				CopyBean.copyNotNull(vo, entity);
				cmsSpecialMng.update(entity);
			}
			cmsSpecialMng.save(entity);
		}
		/*
		 * //专题下的自定义栏目(分类树) List<CmsCategoryVo> childCategorys = vo.getCmsCategoryVos();
		 * if(childCategorys!=null&&childCategorys.size()>0) { for(CmsCategoryVo
		 * categoryVo :childCategorys) { if(categoryVo.getCategoryId()!=null) { //更新
		 * CmsCategory categoryEntity = cmsCategoryMng.find(categoryVo.getCategoryId());
		 * if(categoryEntity!=null) { //categoryVo.getStatus()删除标示
		 * CopyBean.copyNotNull(categoryVo, categoryEntity);
		 * cmsCategoryMng.update(categoryEntity); } }else { //新增 CmsCategory
		 * categoryEntity = BeanMapper.map(categoryVo, CmsCategory.class);
		 * categoryEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
		 * //设置父分类 CmsCategory parent = new CmsCategory();
		 * parent.setCategoryId(categoryId); categoryEntity.setParent(parent);
		 * 
		 * cmsCategoryMng.save(categoryEntity); } } } }else { //新增 CmsCategory entity =
		 * BeanMapper.map(vo, CmsCategory.class); //发布人,从session获取
		 * entity.setCreator("何伟"); entity.setCreateTime(new
		 * Timestamp(System.currentTimeMillis())); cmsCategoryMng.save(entity); }
		 */

	}

	@Override
	@Transactional
	public void saveOrUpdateChild(CmsCategoryVo vo) {
		Long categoryId = vo.getCategoryId();
		if (categoryId != null) {
			// 更新
			CmsCategory entity = cmsCategoryMng.find(vo.getCategoryId());
			if (entity != null) {
				CopyBean.copyNotNull(vo, entity);
				cmsCategoryMng.update(entity);
			}
		} else {
			// 新增
			CmsCategory entity = BeanMapper.map(vo, CmsCategory.class);
			// 发布人,从session获取
			entity.setCreator("何伟");
			entity.setCreateTime(new Timestamp(System.currentTimeMillis()));

			// 设置父分类
			Long parentId = vo.getParentId();
			if (parentId != null) {
				CmsCategory parent = new CmsCategory();
				parent.setCategoryId(parentId);
				entity.setParent(parent);
			}
			// 专题类型
			entity.setTypeId(Constants.categoryType.special);
			cmsCategoryMng.save(entity);
		}

	}

	@Override
	public Pagination listSpecial(CmsSpecialVo vo, int pageNo, int pageSize) {
		Finder hql = Finder.create(Finder.FROM);
		hql.append(CmsSpecial.class.getName());
		hql.append("where 1=1");
		if (StringUtils.isNotEmpty(vo.getSpecialName())) {
			hql.append("and specialName like :specialName").setParam("specialName", "%" + vo.getSpecialName() + "%");
		}
		if (vo.getParentId() != null) {
			hql.append("and parent.specialId = :parentId").setParam("parentId", vo.getParentId());
		} else {
			// 当没有传parentid 赋值-1 列表显示 没有数据
			hql.append("and parent.specialId = :parentId").setParam("parentId", -1L);
		}
		hql.append("and status=1");
		// 正序排序,未审核的放前面
		hql.append("order by lastModifyTime asc");

		Pagination pagination = cmsSpecialMng.find(hql, pageNo, pageSize);
		if (pagination.getList() != null) {
			log.info("获取到专题列表");
			List<CmsSpecialVo> specials = BeanMapper.mapList(pagination.getList(), CmsSpecialVo.class);
			pagination.setList(specials);
		}
		return pagination;
	}

	@Override
	public CmsSpecialVo getSpecialView(Long specialId) {
		CmsSpecial cmsSpecial = cmsSpecialMng.find(specialId);
		CmsSpecialVo vo = BeanMapper.map(cmsSpecial, CmsSpecialVo.class);
		// 获取新闻专题内容(自定义栏目)
		// CmsCategory parent = new CmsCategory();
		// parent.setCategoryId(specialId);

		// Finder hql = Finder.create(Finder.FROM);
		// hql.append(CmsCategory.class.getName());
		// hql.append("where parent=:parent").setParam("parent", parent);
		// hql.append("and status=:status").setParam("status",
		// Constants.CATEGORY_STATUS_ENABLE);
		//
		// List<?> contents = cmsCategoryMng.find(hql);
		// if(contents!=null&&contents.size()>0){
		// List<CmsCategoryVo> cmsCategoryVos = BeanMapper.mapList(contents,
		// CmsCategoryVo.class);
		// vo.setCmsCategoryVos(cmsCategoryVos);
		// }

		return vo;
	}

	@Override
	@Transactional
	public void deleteSpecial(Long specialId) {
		cmsCategoryMng.delete(specialId);
	}

	@Override
	@Transactional
	public void batchDeleteSpecial(Long[] specialIds) {
		for (int i = 0; i < specialIds.length; i++) {
			if (specialIds[i] != null) {
				cmsCategoryMng.delete(specialIds[i]);
			}
		}
	}

//	@Override
//	public List<CmsSpecial> findList(CmsSpecialVo vo) {
//		return cmsSpecialMng.findList(vo);
//	}

	@Override
	public List<CmsSpecial> getSpecialByJson(Long parentId) {
		List<CmsSpecial> list = cmsSpecialMng.getSpecialByJson(parentId);
		if (list == null) {
			return new ArrayList<CmsSpecial>();
		}
		return list;
	}

	@Override
	public boolean saveSpecialType(CmsSpecialVo vo) {
		vo.setCreateTime(new Timestamp(System.currentTimeMillis()));

		// 将vo转换成实体类进行保存
		CmsSpecial cmsSpecial = this.turnVoToEntity(vo);
		// 保存
		CmsSpecial special = cmsSpecialMng.save(cmsSpecial);

		if (special != null) {

			special.setSpecialUrl(special.getSpecialUrl() + special.getSpecialId());
			cmsSpecialMng.update(special);

			// 获得专题Id
			// Long specialId = saveCmsSpecialJustNow.get(0).getSpecialId();
			Long specialId = special.getSpecialId();

			String categoryIds = vo.getCategoryIds();
			// 保存专题和新闻之间的关系
			if (categoryIds != null) {
				String[] categoryIds_s = categoryIds.trim().split(",");
				for (String categoryId : categoryIds_s) {
					CmsSpecialRCategory cmsSpecialRCategory = new CmsSpecialRCategory();
					cmsSpecialRCategory.setCategoryId(Long.parseLong(categoryId));
					cmsSpecialRCategory.setSpecialId(special.getSpecialId());
					cmsSpecialRCategory.setCreateTime(new Timestamp(System.currentTimeMillis()));
					cmsSpecialRCategory.setSiteId(1);
					cmsSpecialRCategory.setStatus(Short.valueOf("1"));
					cmsSpecialRCategoryMng.save(cmsSpecialRCategory);
				}
			}
		} else {
			return false;
		}
		return true;

	}

	@Override
	public boolean updateSpecialType(CmsSpecialVo vo) {
		if (vo == null || vo.getSpecialId() == null) {
			return false;
		}

		CmsSpecial entity = this.turnVoToEntity(vo);
		entity.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
		CmsSpecial result = cmsSpecialMng.update(entity);
		if (result == null) {
			return false;
		}
		if (vo.getCategoryIds() == null) {
			return true;
		}
		List<Long> categoryIds = new ArrayList<>();
		String[] categotyIdsStr = vo.getCategoryIds().split(",");
		for (String id : categotyIdsStr) {
			categoryIds.add(Long.parseLong(id));
		}

		Finder f = Finder.create("from CmsSpecialRCategory where 1=1");
		f.append("and specialId=:SpecialIdS").setParam("SpecialIdS", result.getSpecialId());
		List<CmsSpecialRCategory> listR = (List<CmsSpecialRCategory>) cmsSpecialRCategoryMng.find(f);
		for (CmsSpecialRCategory r : listR) {
			if (!categoryIds.contains(r.getCategoryId())) {
				r.setStatus(Short.valueOf("0"));
			} else {
				categoryIds.remove(r.getCategoryId());
			}
			r.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
			cmsSpecialRCategoryMng.update(r);
		}
		for (Long id : categoryIds) {
			CmsSpecialRCategory cmsSpecialRCategory = new CmsSpecialRCategory();
			cmsSpecialRCategory.setCategoryId(id);
			cmsSpecialRCategory.setSpecialId(result.getSpecialId());
			cmsSpecialRCategory.setCreateTime(new Timestamp(System.currentTimeMillis()));
			cmsSpecialRCategory.setSiteId(1);
			cmsSpecialRCategory.setStatus(Short.valueOf("1"));
			cmsSpecialRCategoryMng.save(cmsSpecialRCategory);
		}
		return true;

	}

	@Override
	public boolean deleteSpecialType(CmsSpecialVo vo) {
		if (vo == null || vo.getSpecialId() == null) {
			return false;
		}
		//cmsSpecialMng.updateSpecialStatus(vo.getSpecialId(), (short) 0);

		CmsSpecial special = cmsSpecialMng.find(vo.getSpecialId());
		if(special==null) {
			return false;
		}
		
		List<CmsSpecial> list = new ArrayList<>();
		list.add(special);
		while (list != null && list.size() > 0) {
			// 删除
			CmsSpecial specialEntity = list.get(0);
			specialEntity.setStatus(Short.parseShort("0"));
			specialEntity.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
			cmsSpecialMng.update(specialEntity);
			
			Finder f = Finder.create("from CmsSpecialRCategory where 1=1");
			f.append("and specialId=:SpecialIdS").setParam("SpecialIdS", specialEntity.getSpecialId());
			f.append("and status>0");
			List<CmsSpecialRCategory> listR = (List<CmsSpecialRCategory>) cmsSpecialRCategoryMng.find(f);
			for(CmsSpecialRCategory r:listR) {
				r.setStatus(Short.valueOf("0"));
				r.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
				cmsSpecialRCategoryMng.update(r);
			}

			// 获取子节点
			Finder hql2 = Finder.create(Finder.FROM);
			hql2.append(CmsSpecial.class.getName());
			hql2.append("where 1=1");
			hql2.append("and parent.specialId=:specialIdP").setParam("specialIdP", specialEntity.getSpecialId());
			hql2.append("and status=1");
			list.addAll((List<CmsSpecial>) cmsSpecialMng.find(hql2));
			list.remove(0);
		}
		return true;
	}

	private CmsSpecial turnVoToEntity(CmsSpecialVo vo) {
		CmsSpecial cmsSpecial = new CmsSpecial();
		if (vo.getSpecialId() != null) {
			cmsSpecial = cmsSpecialMng.find(vo.getSpecialId());
			cmsSpecial.setSpecialId(vo.getSpecialId());
		}
		if (vo.getCreateTime() != null) {
			cmsSpecial.setCreateTime(vo.getCreateTime());
		}
		cmsSpecial.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
		if (vo.getKeywords() != null) {
			cmsSpecial.setKeywords(vo.getKeywords());
		}
		if (vo.getCreator() != null) {
			cmsSpecial.setCreator(vo.getCreator());
		}
		if (vo.getSummary() != null) {
			cmsSpecial.setSummary(vo.getSummary());
		}
		if (vo.getLogo() != null) {
			cmsSpecial.setLogo(vo.getLogo());
		}
		// 保存父专题信息
		if (vo.getParentId() != null) {
			// CmsSpecial parent = cmsSpecialMng.find(vo.getParentId());
			CmsSpecial parent = new CmsSpecial();
			parent.setSpecialId(vo.getParentId());
			cmsSpecial.setParent(parent);
		}
		if (vo.getStatus() != null) {
			cmsSpecial.setStatus(vo.getStatus());
		}
		if (vo.getSpecialName() != null) {
			cmsSpecial.setSpecialName(vo.getSpecialName());
		}
		if (vo.getSortNum() != null) {
			cmsSpecial.setSortNum(vo.getSortNum());
		}
		if (vo.getCategoryIds() != null) {
			cmsSpecial.setCategoryIds(vo.getCategoryIds());
		}
		if (vo.getRecommPriority() != null) {
			cmsSpecial.setRecommPriority(vo.getRecommPriority());
		}
		if (vo.getLogo() != null) {
			cmsSpecial.setLogo(vo.getLogo());
		}
		if (vo.getSpecialUrl() != null) {
			cmsSpecial.setSpecialUrl(vo.getSpecialUrl());
		}
		cmsSpecial.setSiteId(1);

		return cmsSpecial;
	}

	@Override
	public List<CmsSpecial> getChildList(CmsSpecialVo vo) {
		// TODO Auto-generated method stub
		Finder hql = Finder.create(Finder.FROM);
		hql.append(CmsSpecial.class.getName());
		hql.append("where 1=1 and status>0");
		hql.append(" and parent.specialId=:ParentIdP").setParam("ParentIdP", vo.getSpecialId());

		return (List<CmsSpecial>) cmsSpecialMng.find(hql);
	}

}
