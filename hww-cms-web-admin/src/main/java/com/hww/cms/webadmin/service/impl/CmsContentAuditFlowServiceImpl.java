package com.hww.cms.webadmin.service.impl;

import javax.transaction.Transactional;

import com.hww.base.util.R;
import com.hww.cms.common.entity.CmsContentAuditFlow;
import com.hww.cms.common.manager.CmsContentAuditFlowMng;
import com.hww.cms.common.vo.CmsContentAuditFlowVo;
import com.hww.cms.webadmin.service.CmsContentAuditFlowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.List;

@Service("cmsContentAuditFlowService")
@Transactional
public class CmsContentAuditFlowServiceImpl implements CmsContentAuditFlowService {

	@Autowired
	CmsContentAuditFlowMng cmsContentAuditFlowMng;

	@Override
	@Transactional
	public R save(CmsContentAuditFlowVo vo) {
		
		vo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		vo.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
		vo.setStatus(Short.valueOf("1"));
		// CmsContentAuditFlow entity = BeanMapper.map(vo, CmsContentAuditFlow.class);
		CmsContentAuditFlow entity = this.changeVoToEntity(vo);
		entity.setSiteId(1);
		CmsContentAuditFlow r=cmsContentAuditFlowMng.save(entity);
		if(r==null) {
			return R.error("保存失败！").put("result",0);
		}
		return R.ok("保存成功！").put("result", 1);
	}

	@Override
	@Transactional
	public void delete(Long flowId) {
		CmsContentAuditFlow entity = cmsContentAuditFlowMng.find(flowId);
		entity.setStatus(Short.valueOf("0"));
		cmsContentAuditFlowMng.update(entity);
	}

	@Override
	@Transactional
	public List<CmsContentAuditFlow> getList(CmsContentAuditFlowVo vo) {
		// TODO Auto-generated method stub
		return cmsContentAuditFlowMng.getList(vo);
	}

	@Override
	@Transactional
	public R update(CmsContentAuditFlowVo vo) {
		// TODO Auto-generated method stub
		if(vo.getFlowId()==null) {
			return R.error("参数错误！").put("result",0);
		}
		
		CmsContentAuditFlow entity=cmsContentAuditFlowMng.find(vo.getFlowId());
		if(vo.getFlowName()!=null) {
			entity.setFlowName(vo.getFlowName());
		}
		if(vo.getRemark()!=null) {
			entity.setRemark(vo.getRemark());
		}
		entity.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
		// CmsContentAuditFlow entity = BeanMapper.map(vo, CmsContentAuditFlow.class);
		CmsContentAuditFlow r=cmsContentAuditFlowMng.update(entity);
		if(r==null) {
			return R.error("修改失败！").put("result",0);
		}
		return R.ok("修改成功！").put("result", 1);
	}

	public CmsContentAuditFlow changeVoToEntity(CmsContentAuditFlowVo vo) {
		CmsContentAuditFlow entity = new CmsContentAuditFlow();
		if (vo.getFlowId() != null) {
			entity = cmsContentAuditFlowMng.find(vo.getFlowId());
		}
		if (vo.getFlowName() != null) {
			entity.setFlowName(vo.getFlowName());
		}
		if (vo.getStatus() != null) {
			entity.setStatus(vo.getStatus());
		}
		if (vo.getRemark() != null) {
			entity.setRemark(vo.getRemark());
		}
		if (vo.getCreateTime() != null) {
			entity.setCreateTime(vo.getCreateTime());
		}
		if (vo.getLastModifyTime() != null) {
			entity.setLastModifyTime(vo.getLastModifyTime());
		}
		return entity;
	}

}
