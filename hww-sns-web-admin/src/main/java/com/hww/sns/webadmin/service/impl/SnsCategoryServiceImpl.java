/*package com.hww.sns.webadmin.service.impl;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.sns.common.entity.SnsCategory;
import com.hww.sns.common.manager.SnsCategoryMng;
import com.hww.sns.common.vo.SnsCategoryVo;
import com.hww.sns.webadmin.service.SnsCategoryService;

@Service
public class SnsCategoryServiceImpl implements SnsCategoryService {

	private static final Log log = LogFactory.getLog(SnsCategoryServiceImpl.class);

	@Resource
	SnsCategoryMng snsCategoryMng;

	@Override
	public Pagination list(SnsCategoryVo searchVo, Integer pageNo, Integer pageSize) {

		Finder hql = Finder.create(Finder.FROM);
		hql.append(SnsCategory.class.getName());
		hql.append("where 1=1");
		if (searchVo.getCreator() != null) {
			hql.append("and creator=:creator").setParam("creator", searchVo.getCreator());
		}
		hql.append("order by create_time desc");// 倒序排序

		Pagination pagination = snsCategoryMng.find(hql, pageNo, pageSize);

		if (pagination.getList() != null) {
			log.info("获取到分类");
			List<?> entitys = pagination.getList();
			List<SnsCategoryVo> vos = new LinkedList<SnsCategoryVo>();
			for (int i = 0; i < entitys.size(); i++) {
				SnsCategory entity = (SnsCategory) entitys.get(i);
				SnsCategoryVo vo = BeanMapper.map(entity, SnsCategoryVo.class);
				if (entity.getParent() != null) {
					vo.setParentId(entity.getParent().getId());
				}
				vos.add(vo);
			}
			pagination.setList(vos);
			return pagination;
		}
		return null;
	}

	@Override
	@Transactional
	public void saveCategory(SnsCategoryVo vo) {
		if (vo.getCategoryId() != null) {
			// 更新
			SnsCategory entity = snsCategoryMng.find(vo.getCategoryId());
			if (entity != null) {
				if (!StringUtils.isEmpty(vo.getCategoryName())) {
					entity.setCategoryName(vo.getCategoryName());
				}
				if (vo.getParentId() != null && vo.getParentId() != -1) {
					SnsCategory parent = new SnsCategory();
					parent.setCategoryId(vo.getParentId());
					entity.setParent(parent);
				} else {
					entity.setParent(null);
				}
				entity.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
				snsCategoryMng.update(entity);
			}
		} else {
			// 新增
			SnsCategory entity = BeanMapper.map(vo, SnsCategory.class);
			if (entity.getSiteId() == null) {
				// TODO
				entity.setSiteId(1);
			}
			if (StringUtils.isEmpty(entity.getKeywords())) {
				entity.setKeywords(entity.getCategoryName());
			}
			entity.setCreateTime(new Timestamp(System.currentTimeMillis()));

			if (vo.getParentId() != null && vo.getParentId() != -1) {
				SnsCategory parent = new SnsCategory();
				parent.setCategoryId(vo.getParentId());
				entity.setParent(parent);
			}
			snsCategoryMng.save(entity);
		}

	}

	@Override
	@Transactional
	public void deleteCategory(SnsCategoryVo vo) {
		if (vo.getCategoryId() != null) {
			SnsCategory entity = BeanMapper.map(vo, SnsCategory.class);
			snsCategoryMng.delete(entity);
		}

	}

	@Override
	@Transactional
	public void batchDeleteCategory(List<SnsCategoryVo> vos) {
		if (vos != null && vos.size() > 0) {
			List<SnsCategory> entitys = BeanMapper.mapList(vos, SnsCategory.class);
			snsCategoryMng.delete(entitys);
		}

	}

	@Override
	public List<SnsCategoryVo> listSnsCategorys(SnsCategoryVo vo) {

		Finder hql = Finder.create(Finder.FROM);
		hql.append(SnsCategory.class.getName());
		hql.append("where 1=1");
		if (vo != null) {
			if (vo.getCategoryId() != null) {
				// 只加载这一条
				if (vo.getLft() != null && vo.getLft() == 1) {
					// 加载该分类的父分类
					// TODO
				} else if (vo.getRgt() != null && vo.getRgt() == 1) {
					// 加载该分类的子分类
					// TODO
				} else {
					// 加载该分类
					hql.append("and categoryId=:categoryId").setParam("categoryId", vo.getCategoryId());
				}
			} else {
				// 其他
				if (vo.getParentId() != null) {
					SnsCategory parent = new SnsCategory();
					parent.setCategoryId(vo.getParentId());
					hql.append("and Parent =:Parent").setParam("Parent", parent);
				}
				if (vo.getSiteId() != null) {
					hql.append(" and siteId=:siteIdP").setParam("siteIdP", vo.getSiteId());
				}
				if (vo.getDisplay() != null) {
					hql.append(" and is_display=:displayP").setParam("displayP", vo.getDisplay());
				}
				if (vo.getStatus() != null) {
					hql.append(" and status=:statusP").setParam("statusP", vo.getStatus());
				}
			}
		}
		hql.append(" order by lft asc");
		List<?> entitys = snsCategoryMng.find(hql);
		List<SnsCategoryVo> vos = new LinkedList<SnsCategoryVo>();
		if (entitys != null) {
			log.info("获取到分类");

			for (int i = 0; i < entitys.size(); i++) {
				SnsCategory entity = (SnsCategory) entitys.get(i);
				SnsCategoryVo scv = BeanMapper.map(entity, SnsCategoryVo.class);
				if (entity.getParent() != null) {
					scv.setParentId(entity.getParent().getId());
				}
				vos.add(scv);
			}
		}
		return vos;
	}

	@Override
	public SnsCategoryVo findById(Long categoryId) {
		// Finder hql = Finder.create(Finder.FROM);
		// hql.append(SnsCategory.class.getName());
		// hql.append("where categoryId=:categoryId").setParam("categoryId",
		// categoryId);
		SnsCategory entity = snsCategoryMng.find(categoryId);
		if (entity != null) {
			log.info("获取到分类");
			SnsCategoryVo vo = BeanMapper.map(entity, SnsCategoryVo.class);
			return vo;
		}
		return null;
	}

	@Override
	public String getCategoryTreeJson(List<SnsCategoryVo> vos) {

		JSONArray allNodes = new JSONArray();

		if (vos != null) {
			// 一级分类
			for (int i = 0; i < vos.size(); i++) {
				SnsCategoryVo category = vos.get(i);
				// if(category.getParentId()==null) {
				JSONObject oneGroupNode = new JSONObject();
				oneGroupNode.put("text", category.getCategoryName());
				oneGroupNode.put("id", category.getCategoryId());
				oneGroupNode.put("nodeId", category.getCategoryId());
				allNodes.add(oneGroupNode);
				vos.remove(category);
				i--;
				// }
			}
			if (vos.size() > 0) {
				getJSONTree(allNodes, vos);
			}
		}

		return allNodes.toJSONString();
	}

	*//**
	 * 递归
	 * 
	 * @param firstJson
	 * @param vos
	 * @return
	 *//*
	public void getJSONTree(JSONArray firstJson, List<SnsCategoryVo> vos) {
		JSONArray sencondJson = new JSONArray();
		for (int i = 0; i < firstJson.size(); i++) {
			JSONObject object = firstJson.getJSONObject(i);
			for (int j = 0; j < vos.size(); j++) {
				SnsCategoryVo category = vos.get(j);
				if (category.getParentId() != null) {
					if (category.getParentId().equals(object.getIntValue("id"))) {
						JSONObject sencondObj = new JSONObject();
						sencondObj.put("text", category.getCategoryName());
						sencondObj.put("id", category.getCategoryId());
						sencondObj.put("nodeId", category.getCategoryId());

						Object subObjs = object.get("nodes");// 子节点
						if (subObjs != null) {
							JSONArray subNodes = (JSONArray) subObjs;
							subNodes.add(sencondObj);
						} else {
							// 初始化子节点并添加
							JSONArray nodes = new JSONArray();
							nodes.add(sencondObj);
							object.put("nodes", nodes);
						}

						sencondJson.add(sencondObj);
						vos.remove(category);
						j--;
					}
				}
			}
		}
		if (vos.size() > 0 && sencondJson.size() > 0) {
			getJSONTree(sencondJson, vos);
		}
		return;
	}

}
*/