package com.hww.cms.webadmin.service;

import java.util.List;

import com.hww.cms.common.entity.CmsContent;
import com.hww.cms.common.entity.CmsContentData;
import com.hww.cms.common.vo.CmsContentVo;

public interface CmsContentService {

	CmsContentVo findCmsContentById(long contentId,Long categoryId);
	
	/**
	 * 管理员：更新新闻内容
	 *
	 * @author XiaoBG
	 * @date 2018年1月11日 下午9:45:14
	 * param CmsContentVo
	 * @return null
	 */
	void updateNewWithContent(CmsContentVo cmsContentVo);
	/**
	 * 更新审核状态，审核阶段，当前阶段状态
	 */
	void updateAuditstatus(CmsContentVo cmsContentVo);

	void saveESContentByContentId(Long contentId);
	
	CmsContentData getContentById(Long contentId);
	
	List<CmsContent> getListByLocation(CmsContentVo vo);
	
	Long countContent();
	
	void updateContent(CmsContentVo cmsContentVo);
}
