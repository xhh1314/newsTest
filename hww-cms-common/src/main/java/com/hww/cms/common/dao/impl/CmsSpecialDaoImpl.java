package com.hww.cms.common.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.hww.base.common.util.Finder;
import com.hww.cms.common.dao.CmsSpecialDao;
import com.hww.cms.common.entity.CmsSpecial;

@Repository("cmsSpecialDao")
public class CmsSpecialDaoImpl extends LocalDataBaseDaoImpl<Long, CmsSpecial> implements CmsSpecialDao {


	@Override
	public List<CmsSpecial> selectByParentId(Long parentId){
		if(parentId==null) {
			return Lists.newArrayList();
		}
		Finder f = Finder.create(" from CmsSpecial ");
		f.append("where  parent=:parent ");
		f.append("and status=1 ");
		CmsSpecial parent = new CmsSpecial();
		parent.setSpecialId(parentId);
		f.setParam("parent", parent);
		return (List<CmsSpecial>) find(f);
	}
	@Override
	public List<CmsSpecial> getSpecialByJson(Long parentId) {
		Finder f = Finder.create(Finder.FROM);
		f.append(CmsSpecial.class.getName());
		f.append("where 1=1");
		if (parentId != null) {
			if (parentId == 0) {
				f.append("and parent=:parent").setParam("parent", null);
			} else {
				CmsSpecial parent = new CmsSpecial();
				parent.setSpecialId(parentId);
				f.append("and parent=:parent").setParam("parent", parent);
			}
		}
		f.append("and status=1");
		return (List<CmsSpecial>) find(f);
	}

	
//	@Override
//	public List<CmsSpecial> selectAllSpecialList(Short status) {
//		Finder finder = Finder.create("from CmsSpecial where 1=1");
//		if(status!=null) {
//			finder.append("and status=:status");
//			finder.setParam("status", status);
//		}
//		finder.append(" order by last_modify_time desc");
//		List<CmsSpecial> cmsSpecialList = (List<CmsSpecial>) find(finder);
//		return cmsSpecialList;
//	}
	
//	@Override
//	public List<CmsSpecial> findList(CmsSpecialVo vo) {
//		Finder finder = Finder.create("from CmsSpecial where 1=1");
//		finder.append("and status=1");
//		finder.append(" order by last_modify_time desc");
//		List<CmsSpecial> cmsSpecialList = (List<CmsSpecial>) find(finder);
//		return cmsSpecialList;
//	}
	
//	@Override
//	// 多条件查询专题信息
//	public List<CmsSpecial> getCmsSpecialByMultiCondition(CmsSpecial cmsSpecial) {
//
//		Finder finder = Finder.create("from CmsSpecial where 1=1");
//		if (cmsSpecial.getParent() != null) {
//			finder.append("and Parent=:parentId");
//			finder.setParam("parentId", cmsSpecial.getParent().getId());
//		}
//		if (cmsSpecial.getSpecialName() != null) {
//			finder.append("and SpecialName=:specialName");
//			finder.setParam("specialName", cmsSpecial.getSpecialName());
//		}
//		if (cmsSpecial.getLogo() != null) {
//			finder.append("and Logo=:logo");
//			finder.setParam("logo", cmsSpecial.getLogo());
//		}
//		if (cmsSpecial.getSummary() != null) {
//			finder.append("and Summary=:summary");
//			finder.setParam("summary", cmsSpecial.getSummary());
//		}
//		if (cmsSpecial.getSortNum() != null) {
//			finder.append("and SortNum=:sortNum");
//			finder.setParam("sortNum", cmsSpecial.getSortNum());
//		}
//		if (cmsSpecial.getKeywords() != null) {
//			finder.append("and Keywords=:Keywords");
//			finder.setParam("Keywords", cmsSpecial.getKeywords());
//		}
//		if (cmsSpecial.getCategoryIds() != null) {
//			finder.append("and CategoryIds=:categoryIds");
//			finder.setParam("categoryIds", cmsSpecial.getCategoryIds());
//		}
//		if (cmsSpecial.getCreateTime() != null) {
//			finder.append("and CreateTime=:createTime");
//			finder.setParam("createTime", cmsSpecial.getCreateTime());
//		}
//		finder.append(" order by CreateTime desc");
//		List<CmsSpecial> cmsSpecialList = (List<CmsSpecial>) find(finder);
//
//		return cmsSpecialList;
//	}
	
	
//	Finder f = Finder.create(Finder.FROM);
//	f.append(CmsSpecial.class.getName());
//	f.append("where 1=1");
//	if (parentId != null) {
//		if (parentId == 0) {
//			f.append("and parent=:parent").setParam("parent", null);
//		} else {
//			CmsSpecial parent = new CmsSpecial();
//			parent.setSpecialId(parentId);
//			f.append("and parent=:parent").setParam("parent", parent);
//		}
//	}
//	f.append("and status=1");
}
