package com.hww.cms.common.manager.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hww.els.common.vo.SearchNewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import com.hww.base.common.listener.IModifyListener;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.CopyBean;
import com.hww.base.util.SnowFlake;
import com.hww.base.util.StringUtils;
import com.hww.cms.common.dao.CmsContentAuditDao;
import com.hww.cms.common.dao.CmsContentAuditFlowDao;
import com.hww.cms.common.dao.CmsContentAuditFlowStepsDao;
import com.hww.cms.common.dao.CmsContentDao;
import com.hww.cms.common.entity.CmsContent;
import com.hww.cms.common.entity.CmsContentAudit;
import com.hww.cms.common.entity.CmsContentAuditFlow;
import com.hww.cms.common.entity.CmsContentAuditFlowSteps;
import com.hww.cms.common.entity.CmsContentData;
import com.hww.cms.common.entity.CmsContentType;
import com.hww.cms.common.manager.CmsContentAuditFlowMng;
import com.hww.cms.common.manager.CmsContentAuditFlowStepsMng;
import com.hww.cms.common.manager.CmsContentAuditMng;
import com.hww.cms.common.manager.CmsContentDataMng;
import com.hww.cms.common.manager.CmsContentMng;
import com.hww.cms.common.vo.CmsContentDataVo;

@Service("cmsContentAuditFlowStepsMng")
@Transactional
public class CmsContentAuditFlowStepsMngImpl
		extends BaseEntityMngImpl<Long, CmsContentAuditFlowSteps, CmsContentAuditFlowStepsDao>
		implements CmsContentAuditFlowStepsMng {

	CmsContentAuditFlowStepsDao cmsContentAuditFlowStepsDao;

	public CmsContentAuditFlowStepsDao getCmsContentAuditFlowStepsDao() {
		return cmsContentAuditFlowStepsDao;
	}

	@Autowired
	public void setCmsContentAuditFlowStepsDao(CmsContentAuditFlowStepsDao cmsContentAuditFlowStepsDao) {
		super.setEntityDao(cmsContentAuditFlowStepsDao);
		this.cmsContentAuditFlowStepsDao = cmsContentAuditFlowStepsDao;
	}

	@Override
	public List<CmsContentAuditFlowSteps> getList(Long flowId) {
		// TODO Auto-generated method stub
		return cmsContentAuditFlowStepsDao.getList(flowId);
	}

	@Override
	public List<?> findSql(Finder finder,Class<?> o) {
		// TODO Auto-generated method stub
		return cmsContentAuditFlowStepsDao.findJoin(finder, o);
	}

}
