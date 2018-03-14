package com.hww.cms.common.dao;

import java.util.List;

import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.cms.common.entity.CmsContentAuditFlow;
import com.hww.cms.common.entity.CmsContentAuditFlowSteps;


public interface CmsContentAuditFlowStepsDao extends IBaseEntityDao<Long, CmsContentAuditFlowSteps> {
	
	/**
	 * 根据flowId获取审核步骤
	 * @param flowId
	 * @return
	 */
	public List<CmsContentAuditFlowSteps> getList(Long flowId);

}
