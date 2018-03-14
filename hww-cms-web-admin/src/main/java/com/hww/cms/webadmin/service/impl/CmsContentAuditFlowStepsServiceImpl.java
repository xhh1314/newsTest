package com.hww.cms.webadmin.service.impl;

import javax.transaction.Transactional;

import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.cms.common.entity.CmsContentAudit;
import com.hww.cms.common.entity.CmsContentAuditFlow;
import com.hww.cms.common.entity.CmsContentAuditFlowSteps;
import com.hww.cms.common.manager.CmsContentAuditFlowStepsMng;
import com.hww.cms.common.vo.CmsContentAuditFlowStepsVo;
import com.hww.cms.common.vo.CmsContentAuditFlowVo;
import com.hww.cms.common.vo.CmsContentAuditVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hww.cms.webadmin.service.CmsContentAuditFlowStepsService;
import com.hww.sys.common.dto.SysRoleDto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service("cmsContentAuditFlowStepsService")
@Transactional
public class CmsContentAuditFlowStepsServiceImpl implements CmsContentAuditFlowStepsService {

	@Autowired
	CmsContentAuditFlowStepsMng cmsContentAuditFlowStepsMng;

	@Override
	public void save(CmsContentAuditFlowStepsVo vo) {
		vo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		vo.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
		vo.setStatus(Short.valueOf("1"));
		// CmsContentAuditFlowSteps entitiy = BeanMapper.map(vo,
		// CmsContentAuditFlowSteps.class);
		CmsContentAuditFlowSteps entity = this.changeVoToEntity(vo);
		entity.setSiteId(1);
		List<CmsContentAuditFlowSteps> list = getList(vo.getFlowId());
		for (CmsContentAuditFlowSteps e : list) {
			if (e.getStepIndex() >= vo.getStepIndex()) {
				e.setStepIndex(e.getStepIndex() + 1);
				cmsContentAuditFlowStepsMng.update(e);
			}
		}
		cmsContentAuditFlowStepsMng.save(entity);

	}

	@Override
	public void delete(Long stepsId) {

		CmsContentAuditFlowSteps entity = cmsContentAuditFlowStepsMng.find(stepsId);
		entity.setStatus(Short.valueOf("0"));
		cmsContentAuditFlowStepsMng.update(entity);

		List<CmsContentAuditFlowSteps> list = getList(entity.getFlow().getFlowId());
		for (CmsContentAuditFlowSteps e : list) {
			if (e.getStepIndex() > entity.getStepIndex()) {
				e.setStepIndex(e.getStepIndex() - 1);
				cmsContentAuditFlowStepsMng.update(e);
			}
		}

	}

	@Override
	public void deleteSteps(Long flowId) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create("from CmsContentAuditFlowSteps where 1=1");
		finder.append(" and flow.flowId=:flow_id").setParam("flow_id", flowId);
		List<CmsContentAuditFlowSteps> list = (List<CmsContentAuditFlowSteps>) cmsContentAuditFlowStepsMng.find(finder);
		for (CmsContentAuditFlowSteps entity : list) {
			entity.setStatus(Short.valueOf("0"));
			cmsContentAuditFlowStepsMng.update(entity);
		}
	}

	public CmsContentAuditFlowSteps changeVoToEntity(CmsContentAuditFlowStepsVo vo) {
		CmsContentAuditFlowSteps entity = new CmsContentAuditFlowSteps();

		if (vo.getAuditFlowStepsId() != null) {
			entity = cmsContentAuditFlowStepsMng.find(vo.getAuditFlowStepsId());
		}
		if (vo.getFlowId() != null) {
			CmsContentAuditFlow flow = new CmsContentAuditFlow();
			flow.setFlowId(vo.getFlowId());
			entity.setFlow(flow);
		}
		if (vo.getStatus() != null) {
			entity.setStatus(vo.getStatus());
		}
		if (vo.getSepDesc() != null) {
			entity.setSepDesc(vo.getSepDesc());
		}

		if (vo.getStepIndex() != null) {
			entity.setStepIndex(vo.getStepIndex());
		}
		if (vo.getStepAuditRole() != null) {
			entity.setStepAuditRole(vo.getStepAuditRole());
		}
		if (vo.getStepAutidRoleName() != null) {
			entity.setStepAutidRoleName(vo.getStepAutidRoleName());
		}
		if (vo.getCreateTime() != null) {
			entity.setCreateTime(vo.getCreateTime());
		}
		if (vo.getLastModifyTime() != null) {
			entity.setLastModifyTime(vo.getLastModifyTime());
		}
		if(vo.getRoleId()!=null) {
			entity.setRoleId(vo.getRoleId());
		}
		return entity;
	}

	@Override
	public List<CmsContentAuditFlowSteps> getList(Long flowId) {
		// TODO Auto-generated method stub
		if (flowId == null) {
			return new ArrayList<CmsContentAuditFlowSteps>();
		}
		return cmsContentAuditFlowStepsMng.getList(flowId);
	}
	
	@Override
	public List<SysRoleDto> getAllRoleList() {
		// TODO Auto-generated method stub
//		Finder hql = Finder.create("from ");
//		hql.append(SysRole.class.getName());
//		hql.append(" where 1=1");
//		hql.append(" and status>0");
		Finder hql=Finder.create("select * from sys_role where status>0");
		return (List<SysRoleDto>) cmsContentAuditFlowStepsMng.findSql(hql,SysRoleDto.class);
	}

}
