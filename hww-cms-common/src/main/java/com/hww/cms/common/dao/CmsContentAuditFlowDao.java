package com.hww.cms.common.dao;

import java.util.List;

import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.cms.common.entity.CmsContentAuditFlow;
import com.hww.cms.common.vo.CmsContentAuditFlowVo;

public interface CmsContentAuditFlowDao extends IBaseEntityDao<Long, CmsContentAuditFlow> {

	/**
	 * 获取审核流程列表
	 * @param vo vo参数
	 * @return 审核流程列表
	 */
	public List<CmsContentAuditFlow> getList(CmsContentAuditFlowVo vo);

}
