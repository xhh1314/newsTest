package com.hww.cms.common.manager;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.cms.common.dao.CmsCategoryDao;
import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.vo.CmsCategoryVo;
import com.hww.cms.common.vo.CmsContentVo;

import java.util.List;
import java.util.Map;

public interface CmsCategoryMng
	extends
		IBaseEntityMng<Long, CmsCategory, CmsCategoryDao> {
    List<CmsCategory> getChildList(Long parentId, Short display);

    List<CmsCategory> getTopList(Long siteId, Short display);

	// 返回完整的树
    List<CmsCategory> getRetrievingFullTree(Long userId, Integer siteId, Short status);
	
	// 根据类型查分类
    List<CmsCategory> getCategorysByType(Short typeId, Integer siteId, Long parentId);

	// 返回所有父路径
    List<CmsCategory> getRetrievingASinglePath(Integer categoryId, Integer siteId);

    Boolean publishCategory(Long categoryId);

    void saveOrUpdateCategoryAndAndRelationshipWidthTpl(
            CmsCategory entity, CmsCategoryVo form, boolean b);
	
	CmsCategoryVo getCategory(Long categoryId);
	
	List<CmsCategoryVo> categoryDetail(CmsCategoryVo vo);
	
	List<CmsContentVo> cmsContentByCategoryId(CmsCategoryVo vo);
	
	/**
	 * 推荐的专题
	 *
	 * @author lyb
	 * @email 674142624@qq.com
	 * @date 2017年12月14日 下午7:58:39
	 * @return 
	 * @version v0.1
	 */
	List<Map<String,Object>> cmsCategoryNum();
	
	List<Map<String,Object>> cmsCategorys(Integer num);
	
	List<Map<String,Object>> cmsTopicNum();

    List<Map<String, Object>> cmsContentByCatId(Integer categoryId, Integer num);

}
