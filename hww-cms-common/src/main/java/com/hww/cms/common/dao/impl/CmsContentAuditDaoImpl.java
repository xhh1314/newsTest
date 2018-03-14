package com.hww.cms.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.hww.cms.common.dao.CmsContentAuditDao;
import com.hww.cms.common.entity.CmsContentAudit;

@Repository("cmsContentAuditDao")
public class CmsContentAuditDaoImpl extends LocalDataBaseDaoImpl<Long, CmsContentAudit>
implements CmsContentAuditDao {

}
