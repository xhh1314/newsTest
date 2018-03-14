package com.hww.cms.common.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hww.base.common.util.Finder;
import com.hww.cms.common.dao.CmsNewEsSyncFailDao;
import com.hww.cms.common.entity.CmsNewEsSyncFail;


@Repository("cmsNewEsSyncFailDao")
public class CmsNewEsSyncFailDaoImpl extends LocalDataBaseDaoImpl<Long, CmsNewEsSyncFail> implements  CmsNewEsSyncFailDao{

	
	
	public List<CmsNewEsSyncFail> selectAllFialRecords() {
		Finder finder =Finder.create(" from CmsNewEsSyncFail ");
		finder.append(" where isDealSuccess=:isDealSuccess").setParam("isDealSuccess", 0);
		return (List<CmsNewEsSyncFail>) find(finder);
	}

	
	public void updateIsDealSuccess(Long id) {
		Finder finder=Finder.create(" update CmsNewEsSyncFail ");
		finder.append(" set isDealSuccess=:isDealSuccess").setParam("isDealSuccess", 1);
		finder.append(" where id=:id").setParam("id", id);
		update(finder);
	}

}
