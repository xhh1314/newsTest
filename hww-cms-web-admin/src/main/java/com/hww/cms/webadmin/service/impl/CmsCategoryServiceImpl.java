package com.hww.cms.webadmin.service.impl;

import javax.transaction.Transactional;

import com.hww.base.common.util.Finder;
import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.manager.CmsCategoryMng;
import com.hww.cms.common.vo.CmsCategoryVo;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hww.cms.webadmin.service.CmsCategoryService;

import java.util.ArrayList;
import java.util.List;

@Service("cmsCategoryService")
@Transactional
public class CmsCategoryServiceImpl implements CmsCategoryService{

	@Autowired
	CmsCategoryMng cmsCategoryMng;
	
	@Override
	public List<CmsCategory> querySpecialList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteCategory(CmsCategoryVo vo) {
		List<CmsCategory> list=new ArrayList<>();
		CmsCategory entity=cmsCategoryMng.find(vo.getCategoryId());
		list.add(entity);
		while(list.size()>0) {
			CmsCategory cur=list.get(0);
			Finder finder=Finder.create("from CmsCategory");
			finder.append("where 1=1");
			finder.append("and parent.categoryId=:PId").setParam("PId", cur.getCategoryId());
			List<CmsCategory> l=(List<CmsCategory>) cmsCategoryMng.find(finder);
			list.addAll(l);
			cur.setDisplay(Short.valueOf("0"));
			cur.setStatus(Short.valueOf("-1"));
			cmsCategoryMng.update(cur);
			list.remove(0);
		}
		return false;
	}

}
