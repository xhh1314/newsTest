package com.hww.cms.common.manager;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.base.common.page.Pagination;
import com.hww.cms.common.dao.CmsContentDataDao;
import com.hww.cms.common.entity.CmsContentData;
import com.hww.cms.common.vo.CmsContentVo;

public interface CmsContentDataMng extends IBaseEntityMng<Long, CmsContentData, CmsContentDataDao> {
	void saveOrUpdateContentAndRelationshipWithCategory(CmsContentData entity, CmsContentVo form,
                                                        Boolean isSource);

	/**
	 * 新闻列表分页,带内容
	 * @param vo
	 */
	Pagination listCmsContent(CmsContentVo vo);
	
	CmsContentVo getDetail(CmsContentVo vo);
	
	
	 CmsContentData loadCmsContentDataByContentId(Long  contentId) ;
}
