package com.hww.sns.webadmin.service;

import java.util.List;

import com.hww.sns.common.vo.SnsAuditVo;


public interface SnsAuditService {

	//审核后插入一条审核记录
	void save(SnsAuditVo snsAuditVo);
	
	//通用审核详情
	List<SnsAuditVo> queryAuditDetail(Long topicId);

	void batchSaveAudit(List<SnsAuditVo> asList);
	
}
