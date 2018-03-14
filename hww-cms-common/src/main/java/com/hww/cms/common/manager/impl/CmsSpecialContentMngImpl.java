package com.hww.cms.common.manager.impl;

//import com.hww.base.common.manager.impl.BaseEntityMngImpl;
//import com.hww.base.common.util.Updater;
//import com.hww.cms.common.dao.CmsSpecialContentDao;
//import com.hww.cms.common.dao.CmsSpecialDao;
//import com.hww.cms.common.entity.CmsSpecial;
//import com.hww.cms.common.entity.CmsSpecialContent;
//import com.hww.cms.common.manager.CmsSpecialContentMng;
//import com.hww.cms.common.manager.CmsSpecialMng;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collection;
//import java.util.List;
//
//@Service("cmsSpecialContentMng")
//@Transactional
//public class CmsSpecialContentMngImpl extends BaseEntityMngImpl<Long, CmsSpecialContent, CmsSpecialContentDao>
//implements CmsSpecialContentMng {
//
//	CmsSpecialContentDao cmsSpecialContentDao;
//
//	@Override
//	public List<CmsSpecialContent> querySpecialContentList(Long specialId) {
//		return cmsSpecialContentDao.querySpecialContentList(specialId);
//	}
//
//	@Override
//	public CmsSpecialContent queryNewsBySpecialContentId(Long specialContentId) {
//		return cmsSpecialContentDao.queryNewsBySpecialContentId(specialContentId);
//	}
//
//	@Autowired
//	public void setCmsSpecialContentDao(CmsSpecialContentDao cmsSpecialContentDao) {
//		super.setEntityDao(cmsSpecialContentDao);
//		this.cmsSpecialContentDao = cmsSpecialContentDao;
//	}
//	
//	
//	
//}
