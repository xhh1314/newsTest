//package com.hww.cms.webservice.service.impl;
//
//import com.hww.base.common.util.Finder;
//import com.hww.base.util.BeanMapper;
//import com.hww.base.util.BeanUtils;
//import com.hww.base.util.StringUtils;
//import com.hww.base.util.TimeUtils;
//import com.hww.cms.common.dao.CmsCategoryDao;
//import com.hww.cms.common.dao.CmsContentDao;
//import com.hww.cms.common.entity.CmsCategory;
//import com.hww.cms.common.manager.CmsCategoryMng;
//import com.hww.cms.common.vo.CmsCategoryVo;
//import com.hww.cms.common.vo.CmsContentVo;
//import com.hww.cms.common.vo.query.QueryContentVo;
//import com.hww.cms.webservice.feign.FileInfoFeignClient;
//import com.hww.cms.webservice.feign.SnsFeignClient;
//import com.hww.cms.webservice.service.CmsCategoryService;
//import com.hww.file.common.entity.FileInfo;
//import com.hww.sns.common.dto.HBaseSnsQueryDto;
//import com.hww.sns.common.vo.SnsTopicVo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.lang.reflect.InvocationTargetException;
//import java.util.List;
//
//@Service("cmsCategoryService")
//@Transactional
//public class CmsCategoryServiceImpl implements CmsCategoryService{
//
//	@Autowired
//	private CmsCategoryDao cmsCategoryDao;
//	@Autowired
//	private FileInfoFeignClient fileInfoFeignClient;
////	@Autowired
////	private CmsMemberBehaviorService cmsMemberBehaviorService;
//	@Autowired
//	private SnsFeignClient snsFeignClient;
//	@Autowired
//	private CmsContentDao cmsContentDao;
//	@Autowired
//	CmsCategoryMng cmsCategoryMng;
//
//	@Override
//	public List<CmsCategoryVo> querySpecials() {
//		return cmsCategoryDao.querySpecials();
//	}
//
//	@Override
//	public CmsCategoryVo queryNewsBySpecialContentId(CmsCategoryVo vo) {
//		CmsCategoryVo cmsCategoryVo = buildCmsCategoryVo(vo);
//		List<CmsContentVo> cmsContentVoList = cmsContentDao.querySpecialNewList(vo);
//		for (CmsContentVo contentVo : cmsContentVoList) {
//			QueryContentVo vos = new QueryContentVo();
//			vos.setImei(vo.getImei());
//			vos.setUserId(vo.getUserId());
//			putTimeStrAndSomeFlagForContent(vos, contentVo);
//			putFileInfoListForContent(contentVo);
//			putSnsTopicCountsForContent(vos, contentVo);
//		}
//		cmsCategoryVo.setCmsContentList(cmsContentVoList);
//		return cmsCategoryVo;
//	}
//
//	private CmsCategoryVo buildCmsCategoryVo(CmsCategoryVo vo) {
//		CmsCategory cmsCategory = cmsCategoryDao.find(vo.getCategoryId());
//		CmsCategoryVo cmsCategoryVo = new CmsCategoryVo();
//		try {
//			BeanUtils.copyProperties(cmsCategoryVo, cmsCategory);
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//		return cmsCategoryVo;
//	}
//
//	@Override
//	public List<CmsCategoryVo> querySpecialNewIndex(QueryContentVo vo) {
//		List<CmsCategoryVo> cmsCategoryVoList = buildParentSpecialVo();
//		if (null == cmsCategoryVoList || cmsCategoryVoList.size() == 0) {
//			return null;
//		}
//		return buildChildSpecialVoList(vo, cmsCategoryVoList);
//	}
//
//	@Override
//	public CmsCategoryVo querySpecialInfo(CmsCategoryVo vo) {
//		CmsCategoryVo cmsCategoryVo = buildCmsCategoryVo(vo);
//		List<CmsContentVo> cmsContentVoList = cmsContentDao.querySpecialNewList(vo);
//		for (CmsContentVo contentVo : cmsContentVoList) {
//			QueryContentVo vos = new QueryContentVo();
//			vos.setImei(vo.getImei());
//			vos.setUserId(vo.getUserId());
//			putTimeStrAndSomeFlagForContent(vos, contentVo);
//			putFileInfoListForContent(contentVo);
//			putSnsTopicCountsForContent(vos, contentVo);
//		}
//		cmsCategoryVo.setCmsContentList(cmsContentVoList);
//		return cmsCategoryVo;
//	}
//
//	private void putTimeStrAndSomeFlagForContent(QueryContentVo vo, CmsContentVo cmsContentVo) {
//		if (cmsContentVo.getReleaseTime() != null) {
//			cmsContentVo.setReleaseTimeStr(TimeUtils.simpleTimeConverter(cmsContentVo.getReleaseTime()));
//			cmsContentVo.setRangeNowTimeStr(TimeUtils.calculateTime(cmsContentVo.getReleaseTime()));
//		}
//		if (cmsContentVo.getCreateTime() != null) {
//			cmsContentVo.setCreateTimeStr(TimeUtils.simpleTimeConverter(cmsContentVo.getCreateTime()));
//		}
////		boolean collectionFlag = cmsMemberBehaviorService.queryNewCollectionFlag(cmsContentVo.getContentId(),
////				vo.getUserId(), vo.getImei());
////		cmsContentVo.setCollectionFlag(collectionFlag);
//	}
//
//	private void putFileInfoListForContent(CmsContentVo cmsContentVo) {
//		String ids = cmsContentVo.getThumbIds();
//		if (StringUtils.isNotEmpty(ids)) {
//			List<FileInfo> fileInfoList = fileInfoFeignClient.queryFileInfoListByIds(ids);
//			cmsContentVo.setFileInfoList(fileInfoList);
//		} else {
//			cmsContentVo.setFileInfoList(null);
//		}
//	}
//
//	private void putSnsTopicCountsForContent(QueryContentVo vo, CmsContentVo cmsContentVo) {
//		if(cmsContentVo.getContentId()!=null) {
//			HBaseSnsQueryDto snsQueryDto=HBaseSnsQueryDto.newForNewsQuery(cmsContentVo.getContentId(), vo.getUserId());
//			List<SnsTopicVo> snsTopicList = snsFeignClient.newsTpoicList(snsQueryDto);
//			Integer topicCounts = (null == snsTopicList || snsTopicList.size() == 0) ? 0 : snsTopicList.size();
//			cmsContentVo.setTopicCounts(topicCounts);
//		}else {
//			cmsContentVo.setTopicCounts(0);
//		}
//	}
//
//	private List<CmsCategoryVo> buildChildSpecialVoList(QueryContentVo v, List<CmsCategoryVo> cmsCategoryVoList) {
//		for (CmsCategoryVo vo : cmsCategoryVoList) {
//			List<CmsCategoryVo> cmsCategoryVos = cmsCategoryDao.queryChildSpecialList(vo);
//			if (null == cmsCategoryVos || cmsCategoryVos.size() == 0) {
//				continue;
//			}
//			for (CmsCategoryVo vos : cmsCategoryVos) {
//				List<CmsContentVo> cmsContentVoList = cmsContentDao.querySpecialNewForIndex(vos);
//				if (null == cmsContentVoList || cmsContentVoList.size() == 0) {
//					continue;
//				}
//				for (CmsContentVo contentVo : cmsContentVoList) {
//					putTimeStrAndSomeFlagForContent(v, contentVo);
//					putFileInfoListForContent(contentVo);
//					putSnsTopicCountsForContent(v, contentVo);
//				}
//				vos.setCmsContentList(cmsContentVoList);
//			}
//			vo.setCmsCategoryVos(cmsCategoryVos);
//		}
//		return cmsCategoryVoList;
//	}
//
//	private List<CmsCategoryVo> buildParentSpecialVo() {
//		Finder finder = Finder.create("from CmsCategory where 1=1");
//		finder.append(" and typeId=3 and parent.categoryId=null");
//		finder.append(" and auditStatus=1");
//		List<CmsCategory> categoryList = (List<CmsCategory>) cmsCategoryDao.find(finder);
//		if (null == categoryList || categoryList.size() ==0) {
//			return null;
//		}
//		List<CmsCategoryVo> cmsCategoryVoList = BeanMapper.mapList(categoryList, CmsCategoryVo.class);
//		return cmsCategoryVoList;
//	}
//
//  @Override
//  public List<CmsCategoryVo> categoryDetail(CmsCategoryVo vo) {
//    //获取专题栏目下的新闻
//    List<CmsCategoryVo> list = cmsCategoryMng.categoryDetail(vo);
//    for(CmsCategoryVo cvo : list) {
//      
//      Long categoryId = cvo.getCategoryId();
//      List<CmsContentVo> contents = cmsCategoryMng.cmsContentByCategoryId(cvo);
//      //处理新闻图片爆料数
//      for(CmsContentVo content : contents) {
//        
//        Integer count = snsFeignClient.queryTopicCountsByNewId(content.getContentId());
//        content.setTopicCounts(count);
//      }
//      cvo.setCmsContentList(contents);
//    }
//    return list;
//  }
//
//  @Override
//  public CmsCategory categoryById(CmsCategoryVo vo) {
//    
//    return cmsCategoryMng.find(vo.getCategoryId());
//  }
//}
