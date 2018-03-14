package com.hww.cms.common.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.util.Finder;
import com.hww.cms.common.dao.CmsSpecialDao;
import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.entity.CmsContentType;
import com.hww.cms.common.entity.CmsSpecial;
import com.hww.cms.common.manager.CmsSpecialMng;
import com.hww.cms.common.vo.CmsSpecialVo;

@Service("cmsSpecialMng")
@Transactional
public class CmsSpecialMngImpl extends BaseEntityMngImpl<Long, CmsSpecial, CmsSpecialDao> implements CmsSpecialMng {

	CmsSpecialDao cmsSpecialDao;
	@Autowired
	public void setCmsSpecialDao(CmsSpecialDao cmsSpecialDao) {
		super.setEntityDao(cmsSpecialDao);
		this.cmsSpecialDao = cmsSpecialDao;
	}
	
	
	@Override
	public List<CmsSpecial> loadFirstLeveSpecialList(Short status) {
		Finder finder = Finder.create("from CmsSpecial where  parent is null");
		if(status!=null) {
			finder.append("and status=:status");
			finder.setParam("status", status);
		}
//		finder.append(" order by last_modify_time desc");
		List<CmsSpecial> cmsSpecialList = (List<CmsSpecial>) cmsSpecialDao.find(finder);
		return cmsSpecialList;
	}
	@Override
	public  List<CmsSpecial> loadNotTopLeveSpecialList(Short status){
		Finder finder = Finder.create("from CmsSpecial where  parent is not null ");
		if(status!=null) {
			finder.append("and status=:status");
			finder.setParam("status", status);
		}
		List<CmsSpecial> cmsSpecialList = (List<CmsSpecial>) cmsSpecialDao.find(finder);
		return cmsSpecialList;
	}
	
	
	@Override
	public List<CmsSpecial> loadByParentId(Long parentId){
		
		return cmsSpecialDao.selectByParentId(parentId);
	}


	@Override
	public List<CmsSpecial> getSpecialByJson(Long parentId) {
		List<CmsSpecial> list = cmsSpecialDao.getSpecialByJson(parentId);
		if (list == null) {
			return new ArrayList<CmsSpecial>();
		}
		return list;
	}


	/**
	 * 更改special状态 0：禁用 ，1：使用
	 * 
	 * @param specialId
	 *            id
	 * @param status
	 *            状态
	 * @return
	 */
	@Override
	public boolean updateSpecialStatus(Long specialId, Short status) {
		Finder hql = Finder.create("update");
		hql.append(CmsSpecial.class.getName());

		if (status != null) {
			hql.append(" set status=:statusP");
			hql.setParam("statusP", status);
		}

		hql.append(" where specialId=:specialIdP");
		hql.setParam("specialIdP", specialId);

		cmsSpecialDao.update(hql);
		return true;
	}



}
