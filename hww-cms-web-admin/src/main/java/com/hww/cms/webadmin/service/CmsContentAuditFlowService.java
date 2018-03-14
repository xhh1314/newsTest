package com.hww.cms.webadmin.service;

import java.util.List;

import com.hww.base.util.R;
import com.hww.cms.common.entity.CmsContentAuditFlow;
import com.hww.cms.common.vo.CmsContentAuditFlowVo;

public interface CmsContentAuditFlowService {

	R save(CmsContentAuditFlowVo vo);
	void delete(Long flowId);
	R update(CmsContentAuditFlowVo vo);
	List<CmsContentAuditFlow> getList(CmsContentAuditFlowVo vo);
}
