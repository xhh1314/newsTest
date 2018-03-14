package com.hww.cms.common.manager;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.base.common.page.Pagination;
import com.hww.cms.common.dao.CmsCategoryDao;
import com.hww.cms.common.dao.CmsContentAuditDao;
import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.entity.CmsContent;
import com.hww.cms.common.entity.CmsContentAudit;
import com.hww.cms.common.vo.CmsCategoryVo;
import com.hww.cms.common.vo.CmsContentVo;

import java.util.List;
import java.util.Map;

public interface CmsContentAuditMng extends IBaseEntityMng<Long, CmsContentAudit,CmsContentAuditDao> {

	public List<CmsContent> getMyAuditContent(CmsContentVo vo, Long roleId);
	
	public Long getMyAuditContentCount(CmsContentVo vo, Long roleId);
}
