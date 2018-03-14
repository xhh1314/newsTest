package com.hww.cms.common.manager;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.base.common.util.Finder;
import com.hww.cms.common.dao.CmsCategoryDao;
import com.hww.cms.common.dao.CmsContentAuditDao;
import com.hww.cms.common.dao.CmsContentAuditFlowDao;
import com.hww.cms.common.dao.CmsContentAuditFlowStepsDao;
import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.entity.CmsContentAudit;
import com.hww.cms.common.entity.CmsContentAuditFlow;
import com.hww.cms.common.entity.CmsContentAuditFlowSteps;
import com.hww.cms.common.vo.CmsCategoryVo;
import com.hww.cms.common.vo.CmsContentVo;

import java.util.List;
import java.util.Map;

public interface CmsContentAuditFlowStepsMng extends IBaseEntityMng<Long, CmsContentAuditFlowSteps,CmsContentAuditFlowStepsDao> {

	public List<CmsContentAuditFlowSteps> getList(Long flowId);
	
	public List<?> findSql(Finder finder,Class<?> o);
	
}
