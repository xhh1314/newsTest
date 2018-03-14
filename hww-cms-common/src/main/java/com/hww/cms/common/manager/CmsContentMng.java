package com.hww.cms.common.manager;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.StringUtils;
import com.hww.cms.common.dao.CmsContentDao;
import com.hww.cms.common.dto.HCmsQueryDto;
import com.hww.cms.common.entity.CmsContent;
import com.hww.cms.common.vo.CmsContentDataVo;
import com.hww.cms.common.vo.CmsContentVo;
import com.hww.cms.common.vo.query.QueryContentVo;
import com.hww.els.common.vo.SearchNewsVo;

import java.util.List;
import java.util.Map;

public interface CmsContentMng extends IBaseEntityMng<Long, CmsContent, CmsContentDao> {
	
	List<CmsContent> loadCmsContentByCateIds(HCmsQueryDto cmsQueryDto, List<Long> categoryIds, List<Long> uninterestedContentIds);
	
	
//	List<CmsContent> loadCmsContentByContentType(HCmsQueryDto cmsQueryDto, List<Long> uninterestedContentIds);
	
	
	CmsContent findOneByContentId(Long contentId);
	
	List<CmsContent> loadAbountCmsContentList(String aboutNewIds);
	
	List<CmsContent> loadCmsContentList(List<Long> contentIds);
	
	public  List<CmsContent> loadCmsContentLists(HCmsQueryDto cmsQueryDto,List<Long> contentIds);
	

    List<CmsContent> updateRelationshipStatus(Long categoryId, List<Long> contentIds, Short status);
	
	/**
	 * 新闻列表分页,不带内容
	 * @param vo
	 * @return
	 */
	Pagination listCmsContent(CmsContentVo vo);

    List<CmsContent> queryUserNewCollection(QueryContentVo vo);

	List<CmsContent> queryPromoteNewsByPage(List<Long> contentIds, QueryContentVo vo);

	List<CmsContent> queryTopNewList(HCmsQueryDto cmsQueryDto,List<Long> uninterestedContentIds);

    List<CmsContent> queryCmsContentPageList(QueryContentVo vo, List<Long> categoryIds, List<Long> contentIds);

    List<CmsContent> queryNewsByEls(SearchNewsVo vo);

	void saveOrUpdateCmsContent(CmsContentVo form);
	
	List<CmsContentVo> topCmsContentList();
	
	/**
	 * 栏目下(推荐新闻)
	 *
	 * @author lyb
	 * @email 674142624@qq.com
	 * @date 2017年12月14日 下午7:46:44
	 * @return 
	 * @version v0.1
	 */
    List<Map<String, Object>> cmsContentByColum(Integer columnId, Integer num);
    
    List<Map<String,Object>> cmsContentColum();
    
    List<Map<String,Object>> cmsContentByColums(CmsContentVo vo);
    
    List<CmsContentVo> contentsBycategoryId(CmsContentVo vo);
    
    String noInterests(QueryContentVo vo);
    
    List<CmsContentVo> cmsContentByColumId(QueryContentVo vo);
    
    List<CmsContentVo> nearContents(CmsContentVo vo);

	CmsContent findById(Long long1);


	CmsContentVo updateTop(Long contentId, Long categoryId);

	List<?> findJoin(Finder f,Class<?> o);

	

}
