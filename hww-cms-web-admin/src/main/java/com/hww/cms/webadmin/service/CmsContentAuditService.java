package com.hww.cms.webadmin.service;

import java.util.List;

import com.hww.base.common.page.Pagination;
import com.hww.cms.common.entity.CmsContent;
import com.hww.cms.common.entity.CmsContentData;
import com.hww.cms.common.vo.CmsContentAuditVo;
import com.hww.cms.common.vo.CmsContentVo;
import com.hww.sys.common.dto.SysUserDto;

public interface CmsContentAuditService {
	/**
	 * 新闻审核添加
	 * 
	 * @param vo
	 */
	void save(CmsContentAuditVo vo);

	/**
	 * 新闻审核表查询
	 * 
	 * @param ContentId
	 *            新闻id
	 * @param CategortId
	 *            新闻分类id
	 * @return
	 */
	CmsContentAuditVo findByContentIdAndCategoryId(Long ContentId, Long CategortId);

	/**
	 * 新闻审核表更新
	 * 
	 * @param vo
	 */
	void ContentAuditupdate(CmsContentAuditVo vo);

	/**
	 * 获取某一角色可审核的新闻列表
	 * 
	 * @param vo
	 *            新闻
	 * @param roleId
	 *            角色Id
	 * @return
	 */
	Pagination getMyAuditContent(CmsContentVo vo,SysUserDto user);

	boolean auditResultUpdate(CmsContentVo vo, Long roleId, SysUserDto user);
	
	boolean auditAllContent(String contentIds,Integer result,Long roleId,SysUserDto user);
	
	CmsContent getOneContent(CmsContentVo vo);
	
	CmsContentData getContentDate(CmsContentVo vo);
}
