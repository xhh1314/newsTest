package com.hww.cms.common.dao;

import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.cms.common.entity.CmsContent;
import com.hww.cms.common.vo.CmsCategoryVo;
import com.hww.cms.common.vo.CmsContentVo;
import com.hww.cms.common.vo.query.QueryContentVo;

import java.util.List;
import java.util.Map;

public interface CmsContentDao extends IBaseEntityDao<Long, CmsContent> {

    List<CmsContentVo> querySpecialNewList(CmsCategoryVo vo);

    List<CmsContentVo> querySpecialNewForIndex(CmsCategoryVo vos);
    
    List<CmsContentVo> topCmsContentList();

    List<Map<String, Object>> cmsContentByColum(Integer columnId, Integer num);
    
    List<Map<String,Object>> cmsContentColum();
    
    List<CmsContentVo> contentsBycategoryId(CmsContentVo vo);
    
    String noInterests(QueryContentVo vo);
    
    List<CmsContentVo> cmsContentByColumId(QueryContentVo vo);
    
    List<Map<String,Object>> cmsContentByColums(CmsContentVo vo);
    
    List<CmsContentVo> nearContents(CmsContentVo vo);

	CmsContent findOneByContentId(Long contentId);
}
