package com.hww.cms.common.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hww.base.common.util.Finder;
import com.hww.cms.common.dao.CmsContentAuditFlowDao;
import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.entity.CmsContentAuditFlow;
import com.hww.cms.common.vo.CmsContentAuditFlowVo;

@Repository("cmsContentAuditFlowDao")
public class CmsContentAuditFlowDaoImpl extends LocalDataBaseDaoImpl<Long, CmsContentAuditFlow>
		implements CmsContentAuditFlowDao {

	@Override
	public List<CmsContentAuditFlow> getList(CmsContentAuditFlowVo vo) {
		// TODO Auto-generated method stub
		Finder f = Finder.create("from CmsContentAuditFlow bean");
		f.append(" where 1=1 and bean.status=1");
		if (vo.getFlowId() != null) {
			String s = " and bean.flowId =" + vo.getFlowId();
			f.append(s);
		}

		if (vo.getFlowName() != null) {
			String s = " and bean.flowName like '%" + vo.getFlowName() + "%'";
			f.append(s);
		}

		if (vo.getRemark() != null) {
			String s = " and bean.remark like '%" + vo.getRemark() + "%'";
			f.append(s);
		}

		f.append(" order by bean.createTime desc,bean.lastModifyTime desc");
		return (List<CmsContentAuditFlow>) find(f);
	}

}
