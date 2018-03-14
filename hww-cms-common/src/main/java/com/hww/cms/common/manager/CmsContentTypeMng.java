package com.hww.cms.common.manager;

import java.util.List;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.cms.common.dao.CmsContentTypeDao;
import com.hww.cms.common.entity.CmsContentType;
import com.hww.cms.common.vo.CmsContentTypeVo;

public interface CmsContentTypeMng extends IBaseEntityMng<Long, CmsContentType, CmsContentTypeDao> {
	CmsContentTypeVo getContentType(Long contentTypeId);
	
	CmsContentType findContentType(Long contentTypeId);
	
	/**
	 * 获取新闻内容类型列表(下拉框等异步加载)
	 * @return
	 */
	List<CmsContentTypeVo> getAllContentTypes();  
}
