package com.hww.cms.common.manager;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.cms.common.dao.CmsCategoryDao;
import com.hww.cms.common.dao.CmsContentAuditDao;
import com.hww.cms.common.dao.CmsContentAuditFlowDao;
import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.entity.CmsContentAudit;
import com.hww.cms.common.entity.CmsContentAuditFlow;
import com.hww.cms.common.vo.CmsCategoryVo;
import com.hww.cms.common.vo.CmsContentAuditFlowVo;
import com.hww.cms.common.vo.CmsContentVo;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

public interface CmsContentAuditFlowMng extends IBaseEntityMng<Long, CmsContentAuditFlow,CmsContentAuditFlowDao> {

	
	/**
	 * 获取审核流程列表
	 * @param vo vo参数
	 * @return 审核流程列表
	 */
	public List<CmsContentAuditFlow> getList(CmsContentAuditFlowVo vo) ;
	
	public void saveEntity(CmsContentAuditFlow entity);
}
