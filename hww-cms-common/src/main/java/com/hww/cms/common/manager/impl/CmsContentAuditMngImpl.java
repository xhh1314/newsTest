package com.hww.cms.common.manager.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.util.Finder;
import com.hww.cms.common.dao.CmsContentAuditDao;
import com.hww.cms.common.entity.CmsContent;
import com.hww.cms.common.entity.CmsContentAudit;
import com.hww.cms.common.manager.CmsContentAuditMng;
import com.hww.cms.common.vo.CmsContentVo;

@Service("cmsContentAuditMng")
@Transactional
public class CmsContentAuditMngImpl extends BaseEntityMngImpl<Long, CmsContentAudit, CmsContentAuditDao>
		implements CmsContentAuditMng {

	CmsContentAuditDao cmsContentAuditDao;

	public CmsContentAuditDao getCmsContentAuditDao() {
		return cmsContentAuditDao;
	}

	@Autowired
	public void setCmsContentAuditDao(CmsContentAuditDao cmsContentAuditDao) {
		super.setEntityDao(cmsContentAuditDao);
		this.cmsContentAuditDao = cmsContentAuditDao;
	}

	@Override
	public List<CmsContent> getMyAuditContent(CmsContentVo vo, Long roleId) {
		// TODO Auto-generated method stub
		/*
		 * String s = "select cms_content.*" +
		 * "from cms_content left join cms_content_audit_flow_steps " +
		 * "on cms_content.audit_flow_id=cms_content_audit_flow_steps.flow_id " +
		 * "left join cms_content_audit on cms_content_audit.content_id=cms_content.content_id "
		 * + "where " +
		 * "((((cms_content.audit_step is null and cms_content_audit_flow_steps.step_index=1) or "
		 * + "cms_content_audit_flow_steps.step_index-1 = cms_content.audit_step) " +
		 * "and (cms_content.audit_step_result=1 or cms_content.audit_step_result is null)) or "
		 * +
		 * "(cms_content.audit_step_result = 0 and cms_content_audit.audit_his_record<cms_content.audit_his_record)) "
		 * + "and (cms_content.auditstatus=0 or cms_content.auditstatus is null) " +
		 * "and cms_content_audit_flow_steps.role_id=" + roleId + " " +
		 * "and cms_content.status>0 and cms_content_audit_flow_steps.status>0 " +
		 * "group by cms_content.content_id " + "order by cms_content.content_id asc";
		 */
		String s =null;
		 if(vo.getCategoryId()==null) {
		 s = "select cms_content.* " 
				+ "from cms_content left join cms_content_data "
				+ "on cms_content.content_id=cms_content_data.content_id " 
				+ "left join cms_category "
				+ "on cms_content_data.src_category_id=cms_category.category_id "
				+ "left join cms_content_audit_flow_steps "
				+ "on cms_category.flow_id=cms_content_audit_flow_steps.flow_id "
				+ "where ((cms_content.audit_step is null and cms_content_audit_flow_steps.step_index=1) "
				+ "or cms_content.audit_step+1=cms_content_audit_flow_steps.step_index) "
				+ "and cms_content.auditstatus=0 and cms_content.status>0 "
				+ "and cms_content_audit_flow_steps.status>0 and cms_content_audit_flow_steps.role_id=" + roleId + " "
				
				+ "group by cms_content.content_id " + "order by cms_content_data.create_time desc,cms_content.content_id asc " + "limit "
				+ (vo.getPageNo() - 1) * vo.getPageSize() + "," + vo.getPageSize();
		 }else {
			 s = "select cms_content.* " 
						+ "from cms_content left join cms_content_data "
						+ "on cms_content.content_id=cms_content_data.content_id " 
						+ "left join cms_category "
						+ "on cms_content_data.src_category_id=cms_category.category_id "
						+ "left join cms_content_audit_flow_steps "
						+ "on cms_category.flow_id=cms_content_audit_flow_steps.flow_id "
						+ "where ((cms_content.audit_step is null and cms_content_audit_flow_steps.step_index=1) "
						+ "or cms_content.audit_step+1=cms_content_audit_flow_steps.step_index) "
						+ "and cms_content.auditstatus=0 and cms_content.status>0 "
						+ "and cms_content_audit_flow_steps.status>0 and cms_content_audit_flow_steps.role_id=" + roleId + " "
						+"and cms_category.category_id="+vo.getCategoryId()+" "
						+ "group by cms_content.content_id " + "order by cms_content_data.create_time desc,cms_content.content_id asc " + "limit "
						+ (vo.getPageNo() - 1) * vo.getPageSize() + "," + vo.getPageSize();
		 }
		Finder finder = Finder.create(s);

		List<CmsContent> p = (List<CmsContent>) cmsContentAuditDao.findJoin(finder, CmsContent.class);
		return p;
	}

	@Override
	public Long getMyAuditContentCount(CmsContentVo vo, Long roleId) {
		String s =null;
if(vo.getCategoryId()==null) {
		 s = "select count(distinct cms_content.content_id) "
				+ "from cms_content left join cms_content_data "
				+ "on cms_content.content_id=cms_content_data.content_id " 
				+ "left join cms_category "
				+ "on cms_content_data.src_category_id=cms_category.category_id "
				+ "left join cms_content_audit_flow_steps "
				+ "on cms_category.flow_id=cms_content_audit_flow_steps.flow_id "
				+ "where ((cms_content.audit_step is null and cms_content_audit_flow_steps.step_index=1) "
				+ "or cms_content.audit_step+1=cms_content_audit_flow_steps.step_index) "
				+ "and cms_content.auditstatus=0 and cms_content.status>0 "
				+ "and cms_content_audit_flow_steps.status>0 and cms_content_audit_flow_steps.role_id=" + roleId + " "
				+ "order by cms_content.content_id asc";
}else {
		
		s = "select count(distinct cms_content.content_id) "
				+ "from cms_content left join cms_content_data "
				+ "on cms_content.content_id=cms_content_data.content_id " 
				+ "left join cms_category "
				+ "on cms_content_data.src_category_id=cms_category.category_id "
				+ "left join cms_content_audit_flow_steps "
				+ "on cms_category.flow_id=cms_content_audit_flow_steps.flow_id "
				+ "where ((cms_content.audit_step is null and cms_content_audit_flow_steps.step_index=1) "
				+ "or cms_content.audit_step+1=cms_content_audit_flow_steps.step_index) "
				+ "and cms_content.auditstatus=0 and cms_content.status>0 "
				+ "and cms_content_audit_flow_steps.status>0 and cms_content_audit_flow_steps.role_id=" + roleId + " "
				+"and cms_category.category_id="+vo.getCategoryId()+" "
				+ "order by cms_content.content_id asc";
		
}
		
		Finder finder = Finder.create(s);

		List<?> list = cmsContentAuditDao.findJoin(finder, Object.class);
		Object o = list.get(0);
		Map<String, Integer> map = (Map<String, Integer>) o;

		Long result = Long.parseLong(map.get("count(distinct cms_content.content_id)") + "");

		return result;
	}

}
