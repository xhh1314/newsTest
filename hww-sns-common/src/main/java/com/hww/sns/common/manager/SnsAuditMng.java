package com.hww.sns.common.manager;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.sns.common.dao.SnsAuditDao;
import com.hww.sns.common.entity.SnsAudit;

public interface SnsAuditMng
	extends
		IBaseEntityMng<Long, SnsAudit, SnsAuditDao>
{

}
