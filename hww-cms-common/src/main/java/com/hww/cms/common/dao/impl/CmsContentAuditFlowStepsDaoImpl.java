package com.hww.cms.common.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hww.base.common.util.Finder;
import com.hww.cms.common.dao.CmsContentAuditFlowStepsDao;
import com.hww.cms.common.entity.CmsContentAuditFlowSteps;

@Repository("cmsContentAuditFlowStepsDao")
public class CmsContentAuditFlowStepsDaoImpl extends LocalDataBaseDaoImpl<Long, CmsContentAuditFlowSteps>
		implements CmsContentAuditFlowStepsDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<CmsContentAuditFlowSteps> getList(Long flowId) {
		// TODO Auto-generated method stub
		Finder f = Finder.create("from CmsContentAuditFlowSteps bean");
		f.append(" where 1=1 and bean.status=1");
		f.append(" and bean.flow.flowId=:FlowIdP").setParam("FlowIdP", flowId);
		f.append(" order by bean.stepIndex asc");
		return (List<CmsContentAuditFlowSteps>) find(f);
	}

}
