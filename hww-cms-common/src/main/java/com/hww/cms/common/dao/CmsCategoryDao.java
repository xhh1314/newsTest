package com.hww.cms.common.dao;

import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.vo.CmsCategoryVo;
import com.hww.cms.common.vo.CmsContentVo;

import java.util.List;
import java.util.Map;

public interface CmsCategoryDao
	extends
		IBaseEntityDao<Long, CmsCategory> {
    List<CmsCategory> getChildList(Long parentId, Short display);

	// 返回完整的树
    List<CmsCategory> getRetrievingFullTree(Long userId, Integer siteId, Short status);

    List<CmsCategory> getRetrievingASinglePath(Integer categoryId, Integer siteId);

    List<CmsCategory> getCategorysByType(Short typeId, Integer siteId, Long parentId);

	List<CmsCategoryVo> querySpecials();

	List<CmsCategoryVo> queryChildSpecialList(CmsCategoryVo vo);
	
	List<CmsCategoryVo> categoryDetail(CmsCategoryVo vo);
	
	List<CmsContentVo> cmsContentByCategoryId(CmsCategoryVo vo);
	
	List<Map<String,Object>> cmsCategoryNum();
	
	List<Map<String,Object>> cmsCategorys(Integer num);
	
	List<Map<String,Object>> cmsTopicNum();

    List<Map<String, Object>> cmsContentByCatId(Integer categoryId, Integer num);
}
