package com.hww.cms.common.manager.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.cms.common.dao.CmsContentAuditFlowDao;
import com.hww.cms.common.entity.CmsContentAuditFlow;
import com.hww.cms.common.manager.CmsContentAuditFlowMng;
import com.hww.cms.common.vo.CmsContentAuditFlowVo;

@Service("cmsContentAuditFlowMng")
@Transactional
public class CmsContentAuditFlowMngImpl extends BaseEntityMngImpl<Long, CmsContentAuditFlow, CmsContentAuditFlowDao>
		implements CmsContentAuditFlowMng {

	CmsContentAuditFlowDao cmsContentAuditFlowDao;

	public CmsContentAuditFlowDao getCmsContentAuditFlowDao() {
		return cmsContentAuditFlowDao;
	}
	@Autowired
	public void setCmsContentAuditFlowDao(CmsContentAuditFlowDao cmsContentAuditFlowDao) {
		super.setEntityDao(cmsContentAuditFlowDao);
		this.cmsContentAuditFlowDao = cmsContentAuditFlowDao;
	}

	@Override
	public List<CmsContentAuditFlow> getList(CmsContentAuditFlowVo vo) {
		// TODO Auto-generated method stub
		return cmsContentAuditFlowDao.getList(vo);
	}

	@Override
	@Transactional
	public void saveEntity(CmsContentAuditFlow entity) {
		// TODO Auto-generated method stub
		cmsContentAuditFlowDao.save(entity);
	}

}
