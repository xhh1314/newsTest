package com.hww.cms.webadmin.service;

import java.util.List;

import com.hww.base.common.util.Finder;
import com.hww.cms.common.entity.CmsContentAuditFlowSteps;
import com.hww.cms.common.vo.CmsContentAuditFlowStepsVo;
import com.hww.sys.common.dto.SysRoleDto;

public interface CmsContentAuditFlowStepsService {

	void save(CmsContentAuditFlowStepsVo vo);
	void delete(Long stepsId);
	void deleteSteps(Long flowId);
	List<CmsContentAuditFlowSteps> getList(Long flowId);
	public List<SysRoleDto> getAllRoleList();
}
