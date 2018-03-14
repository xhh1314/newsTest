//package com.hww.cms.webservice.service.impl;
//
//import com.alibaba.fastjson.JSONArray;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.google.common.collect.Sets;
//import com.hww.app.common.dto.AppBehaviorDto;
//import com.hww.app.common.dto.AppRecommConfigDto;
//import com.hww.base.util.*;
//import com.hww.cms.common.dto.HCmsIndexDto;
//import com.hww.cms.common.dto.HCmsQueryDto;
//import com.hww.cms.common.entity.CmsContent;
//import com.hww.cms.common.entity.CmsContentData;
//import com.hww.cms.common.entity.CmsSpecial;
//import com.hww.cms.common.manager.CmsContentMng;
//import com.hww.cms.common.vo.CmsContentDataVo;
//import com.hww.cms.common.vo.CmsContentVo;
//import com.hww.cms.common.vo.HCmsSpecialListVo;
//import com.hww.cms.webservice.feign.*;
//import com.hww.cms.webservice.service.CmsContentCacheProxyService;
//import com.hww.cms.webservice.service.CmsContentService;
//import com.hww.cms.webservice.service.CmsSpecialService;
//import com.hww.els.common.HSearchDto;
//import com.hww.els.common.entity.ESRecommendHis;
//import com.hww.sns.common.dto.HBaseSnsQueryDto;
//import com.hww.sns.common.dto.HBaseSnsQueryFeginApiDto;
//import com.hww.sns.common.util.LocationUtils;
//import com.hww.sns.common.vo.SnsPostVo;
//import com.hww.sns.common.vo.SnsTopicVo;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
////import com.hww.sns.common.vo.NearTopicVo;
////@Service("cmsContentService")
////@Transactional
//public class CmsContentServiceImpl_back_2018_0210_2301 implements CmsContentService {
//
//	private static final Log log = LogFactory.getLog(CmsContentServiceImpl_back_2018_0210_2301.class);
//	
//	@Autowired
//	private CmsContentMng cmsContentMng;
//
//	@Autowired
//	private SnsFeignClient snsFeignClient;
//	@Autowired
//	private FileInfoFeignClient fileInfoFeignClient;
//	
//	@Autowired
//	private ElsFeignClient elsFeignClient;
//
//	@Autowired
//	private AppFeignClient appFeignClient;
//	
//	@Autowired
//	private  CmsSpecialService cmsSpecialService;
//    
//    @Autowired 
//    private CmsContentCacheProxyService cmsContentCacheProxyService;
//	//------------------------------新闻详情加载--------------------
//  
//    
//	@Override
//	public CmsContentDataVo loadNewsDetailWithoutPostAndTopic(HCmsQueryDto cmsQueryDto) {
//		CmsContent cmsContent =cmsContentCacheProxyService.loadCmsContentById(cmsQueryDto.getContentId()) ;
//		if (cmsContent == null||cmsContent.getId()==null) {
//			return null;
//		}
//		CmsContentData cmsContentData = cmsContentCacheProxyService.loadCmsContentDataById(cmsQueryDto.getContentId());
//		if (cmsContentData == null) {
//			cmsContentData = new CmsContentData();
//		}
//		
//		//复制cmsContent到cmsContentDataVo
//		CopyBean.copyNotNull(cmsContent, cmsContentData);
//		CmsContentDataVo cmsContentDataVo = new CmsContentDataVo();
//		try {
//			BeanUtils.copyProperties(cmsContentDataVo, cmsContentData);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		} 
//
//		Long memberId=cmsQueryDto.getMemberId();
//		
//		constructCmsContentDataVoForDetail( cmsContentDataVo, cmsContent , memberId );
//		
//		
//		return cmsContentDataVo;
//	}
//	
//	
//	@Override
//	public CmsContentDataVo loadNewsDetail(HCmsQueryDto cmsQueryDto) {
//		CmsContent cmsContent =cmsContentCacheProxyService.loadCmsContentById(cmsQueryDto.getContentId()) ;
//		if (cmsContent == null||cmsContent.getId()==null) {
//			return null;
//		}
//		CmsContentData cmsContentData = cmsContentCacheProxyService.loadCmsContentDataById(cmsQueryDto.getContentId());
//		if (cmsContentData == null) {
//			cmsContentData = new CmsContentData();
//		}
//		
//		//复制cmsContent到cmsContentDataVo
//		CopyBean.copyNotNull(cmsContent, cmsContentData);
//		CmsContentDataVo cmsContentDataVo = new CmsContentDataVo();
//		try {
//			BeanUtils.copyProperties(cmsContentDataVo, cmsContentData);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		} 
//
//		Long memberId=cmsQueryDto.getMemberId();
//		
//		constructCmsContentDataVoForDetail( cmsContentDataVo, cmsContent , memberId );
//		
//		// 加载爆料
//		List<SnsTopicVo> newsTopicVoList=  loadNewsTopicListByNewsId(cmsContent.getId(), memberId);
//		cmsContentDataVo.setSnsTopicList(newsTopicVoList);
//		
//	
//		// 加载评论列表
//		HBaseSnsQueryDto snsQueryDto=HBaseSnsQueryDto.newForNewsQuery(cmsContent.getId(), memberId).setPageNo(cmsQueryDto.getPageNo()).setPageSize(cmsQueryDto.getPageSize());
//		
//		 List<SnsPostVo> postVoList=loadNewsPostListByNewsId(snsQueryDto);
//		 cmsContentDataVo.setSnsPostVoList(postVoList);
//		 
//    	try {
//    		 AppBehaviorDto behaviorDto =new AppBehaviorDto( cmsQueryDto.getMemberId(), 
//    				 cmsContent.getContentId(),  AppBehaviorDto.BevType.b0_xq, AppBehaviorDto.BevValue.b1_ok,  AppBehaviorDto.PlatType.b0_news);
//    		 careateDetaiBehavir( behaviorDto );
//    	}catch (Exception e) {
////    		e.printStackTrace();
//    	}
//		return cmsContentDataVo;
//	}
//	
//    @Async
//    public void careateDetaiBehavir(AppBehaviorDto behaviorDto ) {
//        try {
//        	 appFeignClient.createBehavior(behaviorDto);
//        }catch (Exception e) {
//		}
//    }
//    
//	@Scheduled(fixedDelay = 1000*60*10)
//	   @CacheEvict(value = "cms_loadNewsDetailForShare",allEntries=true)
//	   public void loadNewsDetailForShare_delete_from_cache() {
//		   log.debug("----loadNewsDetailForShare_delete_from_cache------------");
//	   }
//	@Cacheable(value = "cms_loadNewsDetailForShare",key="'loadNewsDetailForShare_'+#contentId")
//	@Override
//	public CmsContentDataVo loadNewsDetailForShare(Long  contentId) {
//		CmsContent cmsContent =cmsContentCacheProxyService.loadCmsContentById(contentId) ;
//		if (cmsContent == null||cmsContent.getId()==null) {
//			return null;
//		}
//		CmsContentData cmsContentData =cmsContentCacheProxyService.loadCmsContentDataById(contentId);
//		if (cmsContentData == null) {
//			cmsContentData = new CmsContentData();
//		}
//		
//		//复制cmsContent到cmsContentDataVo
//		CopyBean.copyNotNull(cmsContent, cmsContentData);
//		CmsContentDataVo cmsContentDataVo = new CmsContentDataVo();
//		try {
//			BeanUtils.copyProperties(cmsContentDataVo, cmsContentData);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		} 
//		constructCmsContentDataVoForDetail( cmsContentDataVo, cmsContent , null );
//		return cmsContentDataVo;
//	}
//	
//
////=========================列表相关================================
//
//     @Override
//  	public List<CmsContentVo> loadCmsContentByIds(List<Long> contentIds,Long memberId,boolean enableUnintrested){
//    	 if(contentIds==null) {
//    		 return Lists.newArrayList();
//    	 }
//    	 List<Long> uninterestedContentIds=Lists.newArrayList();
//    	 if(enableUnintrested) {
//    		 if(memberId!=null) {
//      			uninterestedContentIds =cmsContentCacheProxyService.loadUninterestedContentIds(memberId);
//      			contentIds.removeAll(uninterestedContentIds);//除去不感兴趣的数据====暂时先直接除去
//    		 }
//    	 }
//     	
//    	 List<CmsContent> cmsContentList= cmsContentMng.loadCmsContentList(contentIds);
//    	 if(cmsContentList==null||cmsContentList.isEmpty()) {
// 			return Lists.newArrayList();
// 		}
//    	 
//    	 List<CmsContentVo> cmsContentVoList = BeanMapper.mapList(cmsContentList, CmsContentVo.class);
//    	 
// 		 if(cmsContentVoList==null||cmsContentVoList.isEmpty()) {
// 			return Lists.newArrayList();
// 		 }
// 		 //用户收藏状态---显示？
// 		for(CmsContentVo cmsContentVo: cmsContentVoList) {
// 			constructContentVoForList( cmsContentVo, memberId);
//		}
// 		 return cmsContentVoList;
//     }
//     
//      @Scheduled(fixedDelay = 1000*60*1)
//     @CacheEvict(value = "cms_loadCmsContentByColumnId",allEntries=true)
//	  public void loadCmsContentByColumnId_delete_from_cache() {
//		   log.debug("----oadCmsContentByColumnId_delete_from_cache------------");
//	  }
//   //按照频道查询
//     @Cacheable(value = "cms_loadCmsContentByColumnId",key="'loadCmsContentByColumnId_'+#cmsQueryDto.tocacheKey()")
//     @Override
// 	public List<CmsContentVo> loadCmsContentByColumnId(HCmsQueryDto cmsQueryDto){
//    	 log.debug("==========cms_loadCmsContentByColumnId====================");
//   		List<Long> cmsCategoryIds=cmsContentCacheProxyService.loadCmsCateIdsByColumnId(cmsQueryDto.getColumnId());
//   		if(cmsCategoryIds==null) {
//   			return Lists.newArrayList();
//   		}
//   		List<Long> uninterestedContentIds=Lists.newArrayList();
//		if(cmsQueryDto.getMemberId()!=null) {
//			uninterestedContentIds =cmsContentCacheProxyService.loadUninterestedContentIds( cmsQueryDto.getMemberId());
//		}
//		List<CmsContent> cmsContentList=cmsContentMng.loadCmsContentByCateIds(cmsQueryDto, cmsCategoryIds, uninterestedContentIds);
//		if(cmsContentList==null||cmsContentList.isEmpty()) {
//			return Lists.newArrayList();
//		}
//		
//		List<CmsContentVo> cmsContentVoList = BeanMapper.mapList(cmsContentList, CmsContentVo.class);
//
//		if(cmsContentVoList==null||cmsContentVoList.isEmpty()) {
//			return Lists.newArrayList();
//		}
//		//====================manuallySortList=========start============================================
//		List<CmsContentVo> manuallySortList=cmsContentVoList.stream()
//				.filter(val->(val.getManuallySortNum()!=null&&val.getManuallySortNum().intValue()<=cmsContentVoList.size()))//获取有排序字段的新闻,超过链表size的 sortnum 直接忽略
//				.sorted((v1,v2)->{return v1.getManuallySortNum().intValue()-v2.getManuallySortNum().intValue();})//按照位置进行排序
//				.collect(Collectors.toList());
//		if(manuallySortList!=null&&!manuallySortList.isEmpty()) {
//			cmsContentVoList.removeAll(manuallySortList);//先从列表删除
//			//按照位置重新插入列表
//			for(CmsContentVo manuallySort: manuallySortList) {
//				cmsContentVoList.add(manuallySort.getManuallySortNum()-1, manuallySort);
//			}
//		}
//		//====================manuallySortList=======end===============================================
//		
//		for(CmsContentVo cmsContentVo: cmsContentVoList) {
//			constructContentVoForList( cmsContentVo, cmsQueryDto.getMemberId());
//		}
//		return cmsContentVoList;
//   	}
//
//       @Scheduled(fixedDelay = 1000*60*2)
//	   @CacheEvict(value = "cms_cmsContentBySpecilId",allEntries=true)
//	   public void loadCmsContentBySpecilId_delete_from_cache() {
//		   log.debug("----loadCmsContentBySpecilId_delete_from_cache------------");
//	   }
//       
//     //按照专题查询
//     @Cacheable(value = "cms_cmsContentBySpecilId",key="'loadCmsContentBySpecilId_'+#cmsQueryDto.tocacheKey()")
//     @Override
// 	public List<CmsContentVo> loadCmsContentBySpecilId(HCmsQueryDto cmsQueryDto){
//   		List<Long> cmsCategoryIds=cmsSpecialService.loadCmsCateIdsBySpecialId(cmsQueryDto.getSpecialId());
//   		if(cmsCategoryIds==null) {
//   			return null;
//   		}
//   		List<Long> uninterestedContentIds=Lists.newArrayList();
//		if(cmsQueryDto.getMemberId()!=null) {
//			uninterestedContentIds =cmsContentCacheProxyService.loadUninterestedContentIds( cmsQueryDto.getMemberId());
//		}
//		List<CmsContent> cmsContentList=cmsContentMng.loadCmsContentByCateIds(cmsQueryDto, cmsCategoryIds, uninterestedContentIds);
//		
//		List<CmsContentVo> cmsContentVoList = BeanMapper.mapList(cmsContentList, CmsContentVo.class);
//		if(cmsContentVoList==null||cmsContentVoList.isEmpty()) {
//			return Lists.newArrayList();
//		}
//		for(CmsContentVo cmsContentVo: cmsContentVoList) {
//			constructContentVoForList( cmsContentVo, cmsQueryDto.getMemberId());
//		
//		}
//		return cmsContentVoList;
//   	}
//    
//    
//
//       @Scheduled(fixedDelay = 1000*60*3)
//	   @CacheEvict(value = "cms_topNews",allEntries=true)
//	   public void loadTopNews_delete_from_cache() {
//		   log.debug("----loadTopNews_delete_from_cache------------");
//	   }
//     
//     
//    @Cacheable(value = "cms_topNews",key="'loadTopNews_'+#cmsQueryDto.memberId")
//	@Override
//	public List<CmsContentVo> loadTopNews(HCmsQueryDto cmsQueryDto,boolean enableUnintrested) {
//		List<Long> uninterestedContentIds=Lists.newArrayList();
//		if(enableUnintrested) {
//			if(cmsQueryDto.getMemberId()!=null) {
//				uninterestedContentIds =cmsContentCacheProxyService.loadUninterestedContentIds( cmsQueryDto.getMemberId());
//			}
//		}
//		
//		List<CmsContent> cmsContentList = cmsContentMng.queryTopNewList( cmsQueryDto,uninterestedContentIds);
//		//直接去掉重复
//		cmsContentList=cmsContentList.stream().distinct().collect(Collectors.toList());
//		
//		List<CmsContentVo> cmsContentVoList = BeanMapper.mapList(cmsContentList, CmsContentVo.class);
//		if(cmsContentVoList==null||cmsContentVoList.isEmpty()) {
//			return Lists.newArrayList();
//		}
//		//validateNewKeywordsExist-
//		for(CmsContentVo cmsContentVo: cmsContentVoList) {
//			constructContentVoForList( cmsContentVo, cmsQueryDto.getMemberId());
//		}
//		return cmsContentVoList;
//	}
//
//	@Override
//	public List<CmsContentVo> loadUserCollectNews(HCmsQueryDto cmsQueryDto) {
//		if(cmsQueryDto.getMemberId()==null) {
//			return Lists.newArrayList();
//		}
//		//用户行为类别0 详情查看， 1点赞 ，2 收藏，3评论，4爆料，5 (不感兴趣 内容太水，看过了)
//		List<Long> contentIdList = appFeignClient.loadBehaviorContentIds(
//				new AppBehaviorDto().setMemberId(cmsQueryDto.getMemberId()).setBevType(AppBehaviorDto.BevType.b2_sc)
//				.setPlateType(AppBehaviorDto.PlatType.b0_news));
//		if(contentIdList!=null&&!contentIdList.isEmpty()) {
//			
//			contentIdList=contentIdList.stream().distinct().collect(Collectors.toList());
//		}
//		List<CmsContent> cmsContentList = cmsContentMng.loadCmsContentList(contentIdList);
//		
//		List<CmsContentVo> cmsContentVoList = BeanMapper.mapList(cmsContentList, CmsContentVo.class);
//		if(cmsContentVoList==null||cmsContentVoList.isEmpty()) {
//			return Lists.newArrayList();
//		}
//		for(CmsContentVo cmsContentVo: cmsContentVoList) {
//			constructContentVoForList( cmsContentVo, cmsQueryDto.getMemberId());
//			cmsContentVo.setCollectionFlag(true);
//		}		
//		return cmsContentVoList;
//	}
//	
//
////================================private==========================================================	
//	
//	
//    private CmsContentVo constructContentVoForList(CmsContentVo cmsContentVo,Long memberId) {
//			//如果是视频的话--直接加载视频
////			if(cmsContentVo.getContenttypeId()!=null&&cmsContentVo.getContenttypeId().equals(new Long(6))) {
////				String thumIds=cmsContentVo.getThumbIds();
////				String vedioUrl=fileInfoFeignClient.getVedioUrlsByids(thumIds);
////				cmsContentVo.setVedioUrl(vedioUrl);
////			}
//			//如果是视频
//			if(cmsContentVo.getContenttypeId()!=null&&cmsContentVo.getContenttypeId().equals(new Long(6))) {
//				String attIds=cmsContentVo.getAttachmentIds();
//				if(org.springframework.util.StringUtils.hasLength(attIds)) {
//					attIds=attIds.replaceAll(",", "");
//					String vedioUrl=fileInfoFeignClient.getVedioUrlsByids(attIds);
//	 				cmsContentVo.setVedioUrl(vedioUrl);
//				}
//			}
//			//处理封面--支持多封面
//			if(org.springframework.util.StringUtils.hasLength(cmsContentVo.getThumbIds())) {
//				String thumUrls=fileInfoFeignClient.getUrlByFileId(cmsContentVo.getThumbIds());
//				cmsContentVo.setThumbUrl(thumUrls);
//			}
//			
//			//用户行为类别0 详情查看， 1点赞 ，2 收藏，3评论，4爆料，5 不感兴趣，6内容太水，7看过了
//			Map<String,Integer> behaviorCountMap=appFeignClient.behaviorCount(cmsContentVo.getContentId(),AppBehaviorDto.PlatType.b0_news);
//			cmsContentVo.setNewsCollectCounts(behaviorCountMap.get("2"));
//			cmsContentVo.setNewsFollowCounts(behaviorCountMap.get("1"));
//			cmsContentVo.setNewsPostCounts(behaviorCountMap.get("3")); 
//			cmsContentVo.setTopicCounts(behaviorCountMap.get("4"));
//			if(memberId!=null) {
//				String collectionFlag = appFeignClient.behaviorExist(
//						new AppBehaviorDto(memberId, cmsContentVo.getContentId(), AppBehaviorDto.BevType.b2_sc).setPlateType(AppBehaviorDto.PlatType.b0_news));
//				cmsContentVo.setCollectionFlag("y".equals(collectionFlag));
//			}
//			return cmsContentVo;
//    }
//    
//    private void constructCmsContentDataVoForDetail(CmsContentDataVo cmsContentDataVo,CmsContent cmsContent ,Long memberId ) {
//		cmsContentDataVo.setContenttypeId(cmsContent.getContenttypeId());
//		cmsContentDataVo.setThumbUrl(cmsContent.getThumbUrl());
//		cmsContentDataVo.setThumIds(cmsContent.getThumbIds());
//		cmsContentDataVo.setLongitude(cmsContent.getLongitude());
//		cmsContentDataVo.setLatitude(cmsContent.getLatitude());
//		
//		cmsContentDataVo.setSummary(cmsContent.getSummary());
//		
//		if (cmsContent.getReleaseTime() != null) {
//			cmsContentDataVo.setReleaseTimeStr(TimeUtils.simpleTimeConverter(cmsContent.getReleaseTime()));
//			cmsContentDataVo.setRangeNowTimeStr(TimeUtils.calculateTime(cmsContent.getReleaseTime()));
//		}
//		cmsContentDataVo.setLinkUrl(cmsContent.getLinkUrl());
//		//如果是视频的话--直接加载视频
//		if(cmsContent.getContenttypeId()!=null&&cmsContent.getContenttypeId().equals(new Long(6))) {
//			String attachmentIds=cmsContent.getAttachmentIds();
//			if(attachmentIds!=null) {
//				attachmentIds=attachmentIds.replaceAll(",", "");
//				String vedioUrl=fileInfoFeignClient.getVedioUrlsByids(attachmentIds);
//				cmsContentDataVo.setVedioUrl(vedioUrl);
//			}
//			
//		}
//		//如果是图集
//		if(cmsContent.getContenttypeId()!=null&&cmsContent.getContenttypeId().equals(new Long(5))) {
//	    	if(StringUtils.isNotEmpty(cmsContent.getAttachmentIds())) {
//	    	     String byids = fileInfoFeignClient.getUrlAndDescJsonsByIds(cmsContent.getAttachmentIds());
//	    	      if(StringUtils.isNotEmpty(byids)) {
//	    	    	  //JSONArray sss=JSONArray.parseArray(byids);
//	    	    	  List<Map> atlasContent = JSONArray.parseArray(byids, Map.class);// 过时方法
//	    	    	  cmsContentDataVo.setAtlasContent(atlasContent);
//	    	    	  //cmsContentDataVo.setContent(obj.toString());
//	    	      }
//	    	}
//		}
//		
//		//ThumbIds--缩略图
////		 List<FileInfo> thumbFileList= loadThumbFileInfoList(cmsContent.getThumbIds());
////		 cmsContentDataVo.setFileInfoList(thumbFileList);
//		//处理封面--支持多封面
//		if(org.springframework.util.StringUtils.hasLength(cmsContent.getThumbIds())) {
//				String thumUrls=fileInfoFeignClient.getUrlByFileId(cmsContent.getThumbIds());
//				cmsContentDataVo.setThumbUrl(thumUrls);
//		}
//		//相关新闻
////		List<CmsContent> abountNewList=cmsContentMng.loadAbountCmsContentList(cmsContent.getAboutNewIds());
////		cmsContentDataVo.setCmsContentList(abountNewList);
//		
//		////用户行为类别用户行为类别0 详情查看， 1点赞 ，2 收藏，3评论，4爆料，5不感兴趣 (不感兴趣，内容太水，看过了)
//		Map<String,Integer> behaviorCountMap=appFeignClient.behaviorCount(cmsContent.getId(),AppBehaviorDto.PlatType.b0_news);
//		
//		cmsContentDataVo.setNewsCollectCounts(behaviorCountMap.get("2"));
//		cmsContentDataVo.setNewsFollowCounts(behaviorCountMap.get("1"));
//		cmsContentDataVo.setNewsPostCounts(behaviorCountMap.get("3")); 
//		cmsContentDataVo.setTopicCounts(behaviorCountMap.get("4")); 
//		
//		if(memberId!=null) {
//			AppBehaviorDto appBehaviorDto=new AppBehaviorDto().setMemberId(memberId).setContentId(cmsContent.getId()).setBevType(2);
//	        String collectionFlag = appFeignClient.behaviorExist(appBehaviorDto.setPlateType(AppBehaviorDto.PlatType.b0_news));
//	        cmsContentDataVo.setCollectionFlag("y".equals(collectionFlag));
//		}
//		
//        if(memberId!=null) {
//        	AppBehaviorDto appBehaviorDto2=new AppBehaviorDto().setMemberId(memberId).setContentId(cmsContent.getId()).setBevType(1);
//            String followFlag = appFeignClient.behaviorExist(appBehaviorDto2.setPlateType(AppBehaviorDto.PlatType.b0_news));
//            cmsContentDataVo.setFollowFlag("y".equals(followFlag));
//        }
//    }
//    
//    
//	private List<SnsPostVo> loadNewsPostListByNewsId(HBaseSnsQueryDto snsQueryDto) {
//		if(snsQueryDto.getPageSize()==null||snsQueryDto.getPageNo()==null) {
//			return Lists.newArrayList();
//		}
//		List<SnsPostVo> snsPostVoList = snsFeignClient.newsPostList(snsQueryDto);
//		return snsPostVoList;
//	}
//	
//	private List<SnsTopicVo> loadNewsTopicListByNewsId(Long contentId,Long currentUserId){
//		List<SnsTopicVo> snsTopicList = new ArrayList<SnsTopicVo>();
//		if(contentId==null) {
//			return snsTopicList;
//		}
//		//最多给三个爆料
//		HBaseSnsQueryDto snsQueryDto=HBaseSnsQueryDto.newForNewsQuery(contentId, currentUserId).setPageNo(1).setPageSize(3);
//		snsTopicList = snsFeignClient.newsTpoicList(snsQueryDto);
//		return snsTopicList;
//		
//	}
////	private List<FileInfo> loadThumbFileInfoList(String ids ){
////		if (StringUtils.isNotEmpty(ids)) {
////			List<FileInfo> fileInfoList = fileInfoFeignClient.queryFileInfoListByIds(ids);
////			return fileInfoList;
////		} else {
////			return Lists.newArrayList();
////		}
////	}
//	
////	private List<Long> loadCmsCateIdsByColumnId(Long columnId){
////		if(columnId!=null) {
////			return appFeignClient.loadCmsCateIdsByColumnId(columnId);
////		}
////		return Lists.newArrayList();
////	}
////=====================================home======================================================================================================
//	//上滑动--查询推荐历史
//	@Override
//	 public List<Map<String, Object>> homeCmsSnsUpPull(HCmsIndexDto queryDto) {
//		 Set<Long> uniqueSet=Sets.newHashSet();
//		 List<Map<String, Object>> dataSource=Lists.newArrayList();
//		 
//		 String indexIds=queryDto.getIndexIds();
//		 List<Long> indexIdList=Lists.newArrayList();
//		 if(org.springframework.util.StringUtils.hasText(indexIds)) {
//			 indexIdList=Stream.of(indexIds.split(",")).map(val->Long.valueOf(val)).collect(Collectors.toList());
//		 }
//		 //从es里获取推荐历史
//		 HSearchDto HSearchDto=new HSearchDto().setMemberId(queryDto.getMemberId()).setImei(queryDto.getImei())
//				 	.setHisids(indexIdList).setPageNo(queryDto.getPageNo()).setPageSize(queryDto.getPageSize());
//		 
//		 List<ESRecommendHis> hisList=elsFeignClient.searchRecommHisByPage(HSearchDto);
//		
//		 if(hisList==null||hisList.isEmpty()) {
//			 return dataSource;
//		 }
//		 log.debug("AAAAA===hisList:"+String.join(",", hisList.stream().map(val->String.valueOf(val.getContentId())).collect(Collectors.toList())));
//		 
//		 //分三类进行处理
//		 List<Long> cmsContentVoIdList = hisList.stream().filter(val->val.getType().intValue()==1).map(val->val.getContentId()).collect(Collectors.toList());
//		 List<Long> cmsSpecialVoIdList = hisList.stream().filter(val->val.getType().intValue()==2).map(val->val.getContentId()).collect(Collectors.toList());
//		 List<Long> topicVoIdList = hisList.stream().filter(val->val.getType().intValue()==3).map(val->val.getContentId()).collect(Collectors.toList());
//		 log.debug("AAAAA===cmsContentVoIdList:"+String.join(",", cmsContentVoIdList.stream().map(val->String.valueOf(val)).collect(Collectors.toList())));
//		 log.debug("AAAAA===cmsSpecialVoIdList:"+String.join(",", cmsSpecialVoIdList.stream().map(val->String.valueOf(val)).collect(Collectors.toList())));
//		 log.debug("AAAAA===topicVoIdList:"+String.join(",", topicVoIdList.stream().map(val->String.valueOf(val)).collect(Collectors.toList())));
//		 
//		 cmsContentVoIdList=cmsContentVoIdList.stream().distinct().collect(Collectors.toList());
//		 cmsSpecialVoIdList=cmsSpecialVoIdList.stream().distinct().collect(Collectors.toList());
//		 topicVoIdList=topicVoIdList.stream().distinct().collect(Collectors.toList());
//		 log.debug("BBBBB===cmsContentVoIdList:"+String.join(",", cmsContentVoIdList.stream().map(val->String.valueOf(val)).collect(Collectors.toList())));
//		 log.debug("BBBBB===cmsSpecialVoIdList:"+String.join(",", cmsSpecialVoIdList.stream().map(val->String.valueOf(val)).collect(Collectors.toList())));
//		 log.debug("BBBBB===topicVoIdList:"+String.join(",", topicVoIdList.stream().map(val->String.valueOf(val)).collect(Collectors.toList())));
//		 
//		 //加载新闻
//		 List<CmsContentVo> cmsContentVoList=this.loadCmsContentByIds(cmsContentVoIdList,queryDto.getMemberId(),true);
//		 log.debug("CCCCC===cmsContentVoList:"+String.join(",", cmsContentVoList.stream().map(val->String.valueOf(val.getContentId())).collect(Collectors.toList())));
//		 for(CmsContentVo vo: cmsContentVoList) {
//			 Map<String,Object> cmsContentVoItem=Maps.newHashMap();
//			  cmsContentVoItem.put("type", "1");
//			  cmsContentVoItem.put("content", vo);
//			  if(!uniqueSet.contains(vo.getContentId())) {
//				  dataSource.add(cmsContentVoItem);
//				  uniqueSet.add(vo.getContentId());
//			  }
//			  
//		 }
//		 log.debug("DDDDDD===dataSource:"+String.join(",", dataSource.stream().map(val->""+((CmsContentVo)val.get("content")).getContentId()).collect(Collectors.toList())));
//		 
//		 //加载专题
//		 for(Long spid:cmsSpecialVoIdList) {
//			 HCmsSpecialListVo cmsSpecialVo = cmsSpecialService.loadCmsSpecialListVoById(
//					  new HCmsQueryDto().setSpecialId(spid).setMemberId(queryDto.getMemberId())
//					  .setPageNo(1).setPageSize(1));
//			 if(cmsSpecialVo!=null) {
//				 Map<String,Object> cmsSpecialVoItem=Maps.newHashMap();
//				  cmsSpecialVoItem.put("type", "2");
//				  cmsSpecialVoItem.put("contents",cmsSpecialVo);
//				  dataSource.add(cmsSpecialVoItem);
//			 }
//		 }
//		 log.debug("EEEE===dataSource:"+String.join(",", dataSource.stream().map(val->""+((CmsContentVo)val.get("content")).getContentId()).collect(Collectors.toList())));
//		 
//		 List<Long> topicVoIdList_2=Lists.newArrayList();
//		 
//		 Map<Long ,CmsContentData> dataContainer=Maps.newHashMap();
//		 
//		 for( Long cmsContentId : topicVoIdList) {
//			 CmsContentData cmsDataFromSns= cmsContentCacheProxyService.loadCmsContentDataById( cmsContentId);
//			 if(cmsDataFromSns!=null) {
//				 topicVoIdList_2.add(cmsDataFromSns.getSnsOrginId());
//				 dataContainer.put(cmsDataFromSns.getSnsOrginId(), cmsDataFromSns);
//			 }
//		 }
//		 //加载新鲜事
//		 HBaseSnsQueryFeginApiDto queryFeginApiDto= new HBaseSnsQueryFeginApiDto()
//        		 .setMemberId(queryDto.getMemberId()).setImei(queryDto.getImei())
//        		 .setLatitude(queryDto.getLatitude()).setLongitude(queryDto.getLongitude())
//        		 .setTopicIds(topicVoIdList_2);
//		 
//		 //vo.getContentId()
//		 List<SnsTopicVo> recommTopicHisList=snsFeignClient.loadTopicByIdsFeginApi(queryFeginApiDto);
//		  for(SnsTopicVo vo: recommTopicHisList) {
//			  CmsContentData cmsDataFromSns=  dataContainer.get(vo.getTopicId());
//			  vo.setTitle(cmsDataFromSns.getContent());
//			  vo.setContent(cmsDataFromSns.getContent());
//			  Map<String,Object> topicVoItem=Maps.newHashMap();
//			  topicVoItem.put("type", "3");
//			  topicVoItem.put("content", vo); 
//			  dataSource.add(topicVoItem);
//		  }
//		  log.debug("FFFFFF===dataSource:"+String.join(",", dataSource.stream().map(val->""+((CmsContentVo)val.get("content")).getContentId()).collect(Collectors.toList())));
//			 
//		 return dataSource;
//	 }
//
////===============================================================================
////	0~200m  权重需要 ≥1  近在咫尺，目光可及
////	200m~2km 权重需要 ≥2  就在身边，步行可及
////	2km~10km 权重需要 ≥3 同一片区域
////	10km~40km（市）  权重需要≥4 同一个低级行政单位
////	40km~300km（省/州/欧洲一个国）  权重需要≥5 同一个中级行政单位
////	300km~3000km（国/欧盟） 权重需要≥6 同一个高级行政单位
////	3000km+ 权重需要≥7 全球范围
//	private double priorityToDistance(Integer priority){
//		priority=priority==null?10:priority;
//		switch (priority) {
//		case 1:return 200D;
//		case 2:return 2000D;
//		case 3:return 10000D;
//		case 4:return 40000D;
//		case 5:return 300000D;
//		case 6:return 3000000D;
//		case 7:return 3000001D;
//		default:return 3000001D;
//		}
//	}
//    private double cacuateScore(HCmsIndexDto queryDto,CmsContentVo vo) {
//    	  Double lat=queryDto.getLatitude();
//    	  Double lon=queryDto.getLongitude();
//    	  if(lat==null||lon==null) {
//    		  lat=39.924896D; lon=116.403694D;
//    	  }
//    	  double distance =1;
//    	
//    		try {
//    		 distance =LocationUtils.getDistance(lat.doubleValue(), lon.doubleValue(),
//                   Double.valueOf(vo.getLatitude()), Double.valueOf(vo.getLongitude()));
//    		}catch (Exception e) {
//    			distance=1;
//    		}
////    		double distanceF=1D/distance;
////    		long time = (System.currentTimeMillis() - vo.getReleaseTime().getTime())/1000/60/60;//小时
////    			 time=time==0?1:time;
////    		double timeF=(1D/(time*1.0D));
////    		double priorityF=(vo.getPriority()==null?1D:vo.getPriority().doubleValue())*0.1;
////    		double res=distanceF*0.2+timeF*0.3 +priorityF*0.5;
//    		
//
//    		double priorityToDistance= priorityToDistance(vo.getPriority());
//    		
//    	    double res=Math.sqrt(Math.pow(distance,2) +Math.pow(priorityToDistance,2));
//    	   return res;
//      }
//    
//    private List<CmsContentVo> filterAndConstructColumnData(int finalPutDataCount,Set<Long> contentIdsHaveUsed,List<Long> contentIdsInReHis,List<CmsContentVo> cmsContentVoList){
//    	
//    	List<CmsContentVo> voList=new ArrayList<CmsContentVo>();
//    	//--第一次循环-------本页面没有而且历史里也没有的--
//    	for(int i=0;i<finalPutDataCount;i++) {//要凑够这个数
//    		for(CmsContentVo vo: cmsContentVoList) {
//    			
//    			if(!contentIdsHaveUsed.contains(vo.getContentId())&&!contentIdsInReHis.contains(vo.getContentId())) {
//    				voList.add(vo);
//    				if(voList.size()>=finalPutDataCount) {
//    					return voList;
//    				}
//        		}
//    		}
//    	}
//    	//循环2-数据没有凑够数的话----本页面没有没有的--
//    	if(voList.size()<finalPutDataCount) {//经过循环1之后还不够的话---
//    		for(CmsContentVo vo: cmsContentVoList) {
//    			if(!contentIdsHaveUsed.contains(vo.getContentId())&&(!voList.contains(vo))) {
//    				voList.add(vo);
//    				if(voList.size()>=finalPutDataCount) {
//    					return voList;
//    				}
//        		}
//    		}
//    	}
//    	//循环3-数据没有凑够数的话----把数据填充进去--
////    	if(voList.size()<finalPutDataCount) {//经过循环2之后还不够的话---返回数据列表没有的就加进去
////    		for(CmsContentVo vo: cmsContentVoList) {
////    			if((!voList.contains(vo))) {
////    				voList.add(vo);
////    				if(voList.size()>=finalPutDataCount) {
////    					return voList;
////    				}
////        		}
////    		}
////    	}
//    	return voList;
//    }
//    @Override
//    public  List<Map<String, Object>> homeCmsSns(HCmsIndexDto queryDto,List<Long> toNewsIds) {
//		long a=System.currentTimeMillis();
//		log.debug("-----------start-||"+a/1000);
//		Long memberId=queryDto.getMemberId();
//		String imei=queryDto.getImei();
//		//最近的500条推荐记录--尽量不重复出现在首页中
//	  List<Long> contentIdsInReHis = elsFeignClient.searchRecommHis( 
//			  new HSearchDto().setMemberId(memberId).setImei(imei).setPageNo(1).setPageSize(500));//.setPlateType(0)
//	  long b=System.currentTimeMillis();
//	  log.debug("-----after---elsFeignClient.searchRecommHis.----|t1: +"+(b-a)/1000+"||t2: "+(b-a)/1000+"");
//	  Set<Long> contentIdsIndexHaveUsed=Sets.newHashSet();//用来存放在频道里已经有的新闻id，防止后面的频道里 出现重复的新闻
//	  contentIdsIndexHaveUsed.addAll(toNewsIds);
//	  //返回数据
//	  List<Map<String,Object>> dataListGloble=Lists.newArrayList();
//	  //本次推荐的id---要同步到es中
//	  List<ESRecommendHis> thisTimeRecommendHis = Lists.newArrayList();
////	  List<Long> indexIds=Lists.newArrayList();//存放本屏数据
//	  List<AppRecommConfigDto>  recommAllLists = cmsContentCacheProxyService.loadAllRecomm();
//	  recommAllLists=recommAllLists.stream().filter(val->val.getRecommNum()!=null&&val.getRecommNum()>0).collect(Collectors.toList());
//	  long c=System.currentTimeMillis();
//	  log.debug("-----after---elsFeignClient.loadAllRecomm.----|t1: +"+(c-b)/1000+"||t2: "+(c-a)/1000+"");
////栏目推荐=================================》目列表  1 栏目 2 专题 3 新鲜事================================
//	  List<Map<String,Object>> dataListForColumnRecomm=Lists.newArrayList();//专题数据容器
//	  
//	  List<AppRecommConfigDto> recommColumnLists=recommAllLists.stream().filter(val->val.getType().intValue()==1).collect(Collectors.toList());
//	  if(recommColumnLists!=null&&recommColumnLists.size()>0) {
//		  for(AppRecommConfigDto recomm : recommColumnLists) {//在这里是频道循环
//			  int recommNum =recomm.getRecommNum()==null?0:recomm.getRecommNum();
//			  log.debug("================recommNum:"+recommNum);
//			  Long columnId=recomm.getColumnId();
//			  //是直接查询的
//			  List<CmsContentVo> cmsContentVoList = loadCmsContentByColumnId(new HCmsQueryDto().setMemberId(queryDto.getMemberId()).setColumnId(columnId).setOrderBy(1)
//					  .setPageNo(1).setPageSize(recommNum*10));//按照时间顺序查询10倍数据
//			 
//			  if(cmsContentVoList!=null&&cmsContentVoList.size()>0) {//这里频道内的数据循环
//				  //计算到底放多少条数据
//				  int finalPutDataCount=recommNum<cmsContentVoList.size()?recommNum:cmsContentVoList.size();
//				  //排序逻辑
//				  if(queryDto.getLongitude()!=null&&queryDto.getLatitude()!=null) {
//					  //按照公式进行排序
//					  cmsContentVoList=cmsContentVoList.stream().sorted((v1,v2)->{
//						   double s1=cacuateScore( queryDto, v1);
//						   double s2=cacuateScore( queryDto, v2);	   
//						   return (int)(s2-s1);
//					  }).collect(Collectors.toList());
//				  }
//				 List<CmsContentVo> voListAfterFilter = filterAndConstructColumnData( finalPutDataCount,contentIdsIndexHaveUsed,contentIdsInReHis, cmsContentVoList);
//				  for(CmsContentVo vo: voListAfterFilter) {
//					  if(vo.getSnsOrginId()!=null) {//内容化过来的数据
//						  
//						  HBaseSnsQueryFeginApiDto queryFeginApiDto= new HBaseSnsQueryFeginApiDto()
//					        		 .setMemberId(queryDto.getMemberId()).setImei(queryDto.getImei())
//					        		 .setLatitude(queryDto.getLatitude()).setLongitude(queryDto.getLongitude())
//					        		 .setTopicIds(Lists.newArrayList(vo.getSnsOrginId()));
//						  
//							 List<SnsTopicVo> recommTopicList=cmsContentCacheProxyService.loadTopicByIds(queryFeginApiDto);
//							 if(recommTopicList!=null&&!recommTopicList.isEmpty()) {
//								 SnsTopicVo snsvo=recommTopicList.get(0);
////								 snsvo.setTopicId(vo.getSnsOrginId());
//								 snsvo.setTitle(vo.getContent());
//								 snsvo.setContent(vo.getContent());
//								  Map<String,Object> topicVoItem=Maps.newHashMap();
//								  topicVoItem.put("type", "3");
//								  topicVoItem.put("content", snsvo); 
//								  dataListForColumnRecomm.add(topicVoItem);
//								  thisTimeRecommendHis.add(new ESRecommendHis( memberId, imei,3,vo.getContentId(),0));
//								  contentIdsIndexHaveUsed.add(vo.getContentId());
//							 }
//							 
//					  }else {
//						  
//						  Map<String,Object> cmsContentVoItem=Maps.newHashMap();
//						  cmsContentVoItem.put("type", "1");
//						  cmsContentVoItem.put("content", vo); 
//						  dataListForColumnRecomm.add(cmsContentVoItem);
//						  thisTimeRecommendHis.add(new ESRecommendHis( memberId, imei,1,vo.getContentId(),0));
//						  contentIdsIndexHaveUsed.add(vo.getContentId());
//					  }
//					 
//				  }
//			  }
//		  }
//		  
//	  }
//	  dataListGloble.addAll(dataListForColumnRecomm);
//	  long d=System.currentTimeMillis();
//	  log.debug("-----after---dataListForColumnRecomm----|t1: +"+(d-c)/1000+"||t2: "+(d-a)/1000+"");
////=============栏目推荐结束=====================================
//
////专题 =======================》  1 栏目 2 专题 3 新鲜事 ======================================================
//	  List<Map<String,Object>> dataListForSpecial=Lists.newArrayList();//专题数据容器
//	  
//	  List<AppRecommConfigDto> specialRecommList = recommAllLists.stream().filter(val->val.getType().intValue()==2).collect(Collectors.toList());
//	  if(specialRecommList!=null&&specialRecommList.size()>0) {
//		  Integer specialRecommNum=specialRecommList.get(0).getRecommNum();//推荐的专题数量
//		  if(specialRecommNum!=null&&specialRecommNum.intValue()>0) {
//			  
////			  List<CmsSpecial> spcialList= cmsSpecialService.loadNotTopLeveSpecialList(Short.valueOf("1"));
//			  //只有顶级专题才会被推荐
//			  List<CmsSpecial> spcialList= cmsSpecialService.loadFirstLeveSpecialList(Short.valueOf("1"));
//			  if(spcialList!=null&&!spcialList.isEmpty()) {
//				  List<CmsSpecial> recommSpList=spcialList.stream().sorted((v1,v2)->v2.getRecommPriority()-v1.getRecommPriority()).collect(Collectors.toList());
//				  if(recommSpList!=null&&recommSpList.size()>specialRecommNum) {
//					  recommSpList=recommSpList.subList(0, specialRecommNum);
//				  }
//				  for(CmsSpecial sp : recommSpList) {
//					  HCmsSpecialListVo cmsSpecialVo = cmsSpecialService.loadCmsSpecialListVoById(new HCmsQueryDto().setSpecialId(sp.getSpecialId()).setMemberId(queryDto.getMemberId())
//							  							.setPageNo(1).setPageSize(1));
//					  if(cmsSpecialVo!=null) {
//						  Map<String,Object> cmsSpecialVoItem=Maps.newHashMap();
//						  cmsSpecialVoItem.put("type", "2");
//						  cmsSpecialVoItem.put("contents",cmsSpecialVo);
//						  dataListForSpecial.add(cmsSpecialVoItem);
//						  
//						  thisTimeRecommendHis.add(new ESRecommendHis( memberId, imei,2,sp.getSpecialId(),3));
//						  contentIdsIndexHaveUsed.add(sp.getSpecialId());
//						}
//					  }
//					  
//				  }
//			  }
//	  }
//	  dataListGloble.addAll(dataListForSpecial);
//	  long e=System.currentTimeMillis();
//	  log.debug("-----after---dataListForSpecial----|t1: +"+(e-d)/1000+"||t2: "+(e-a)/1000+"");
////=================专题结束======================================================================================
//	  
////新鲜事=====================================》 1 栏目 2 专题 3 新鲜事=========================================
//	  
////	  List<Map<String,Object>> dataListForTopic=Lists.newArrayList();
////	  
////	  List<AppRecommConfigDto> topicRecommList = recommAllLists.stream().filter(val->val.getType().intValue()==3).collect(Collectors.toList());
////	  if(topicRecommList!=null&&topicRecommList.size()>0) {
////		  
////		  int  topicRecommNum=topicRecommList.get(0).getRecommNum();//新鲜事推荐数量
////		  List<SnsTopicVo>  recommTopicList = snsFeignClient.loadRecommTopicsFeginApi(new HBaseSnsQueryDto().setMemberId(queryDto.getMemberId()).setPageNo(1).setPageSize(topicRecommNum));
////		 
////		  for(SnsTopicVo vo: recommTopicList) {
////			  Map<String,Object> topicVoItem=Maps.newHashMap();
////			  topicVoItem.put("type", "3");
////			  topicVoItem.put("content", vo); 
////			  
////			  dataListForTopic.add(topicVoItem);
////			  
////			  thisTimeRecommendHis.add(new ESRecommendHis( memberId, imei,3,vo.getTopicId(),1));
////			  contentIdsIndexHaveUsed.add(vo.getTopicId());
////		  }
////	  }
////	  dataListGloble.addAll(dataListForTopic);
////	  long f=System.currentTimeMillis();
////	  log.debug("-----after---dataListForTopic----|t1: +"+(f-e)/1000+"||t2: "+(f-a)/1000+"");
////=============新鲜事推荐结束============================================================================	
//	  
//	  asyncHis(thisTimeRecommendHis);
//	  log.debug("-----after---asyncHis----||"+(System.currentTimeMillis()-a)/1000);
//	  Map<String,Object> lastOne=Maps.newHashMap();
//	  lastOne.put("indexIds",contentIdsIndexHaveUsed );
//	  dataListGloble.add(lastOne);
//	  return  dataListGloble;
//  }
//	@Async
//	private void asyncHis(List<ESRecommendHis> recommendHisList) {
//		elsFeignClient.createRecommHis(recommendHisList);
//	}
//	
////	  List<CmsSpecial> spcialList= cmsSpecialService.loadFirstLeveSpecialList(Short.valueOf("1"));
////	  if(spcialList!=null&&!spcialList.isEmpty()) {
////		  List<CmsSpecial> recommSpList=spcialList.stream().sorted((v1,v2)->v1.getRecommPriority()-v2.getRecommPriority()).collect(Collectors.toList());
////		  if(recommSpList!=null&&recommSpList.size()>specialRecommNum) {
////			  recommSpList=recommSpList.subList(0, specialRecommNum);
////		  }
////		  //按照专题列表加载即可
////		  for(CmsSpecial sp : recommSpList) {
////			  
////			  List<HCmsSpecialListVo> cmsSpecialVoList = cmsSpecialService.loadCmsSpecialListVoByParentId(
////					  new HCmsQueryDto().setSpecialId(sp.getSpecialId()).setMemberId(queryDto.getMemberId())
////					  .setPageNo(1).setPageSize(1));
////			  
////			  
////			  if(cmsSpecialVoList!=null&&!cmsSpecialVoList.isEmpty()) {
////				for(HCmsSpecialListVo specialListVo:cmsSpecialVoList ) {
////					  Map<String,Object> cmsSpecialVoItem=Maps.newHashMap();
////					  cmsSpecialVoItem.put("type", "2");
////					  cmsSpecialVoItem.put("contents",specialListVo);
////					  dataListForSpecial.add(cmsSpecialVoItem);
////					  thisTimeRecommendHis.add(new ESRecommendHis( memberId, imei,2,sp.getSpecialId(),0));
////					  indexIds.add(sp.getSpecialId());
////				}
////			  }
////		  }
////	  }
//	//自定义--移动端暂时没有
////	  List<Long> customColumnIdList = appFeignClient.loadCustomColumnIds();
////	  List<Map<String,Object>> dataListForcustomColumn=Lists.newArrayList();//专题数据容器
////	  if(customColumnIdList!=null&&customColumnIdList.size()>0) {
////		  for(Long customColumnId : customColumnIdList) {
////			  List<AppRecommConfigDto> appRecommConfigDtoList= recommAllLists.stream().filter(val->val.getColumnId().equals(customColumnId)).collect(Collectors.toList());
////			  if(appRecommConfigDtoList==null||appRecommConfigDtoList.isEmpty()) {
////				  continue;
////			  }
////			  
////			  Integer recommNum = appRecommConfigDtoList.get(0).getRecommNum();
////			  recommNum=recommNum==null?0:recommNum.intValue();
////			  //暂时不是推荐的，是直接查询的
////			  List<CmsContentVo> cmsContentVoList = loadCmsContentByColumnId(
////					  new HCmsQueryDto()
////					  .setMemberId(cmsQueryDto.getMemberId())
////					  .setColumnId(customColumnId).setPageNo(1).setPageSize(recommNum));
////			  if(cmsContentVoList!=null&&cmsContentVoList.size()>0) {
////				  for(CmsContentVo vo: cmsContentVoList) {
////					  Map<String,Object> customContentVoItem=Maps.newHashMap();
////					  customContentVoItem.put("type", "4");
////					  customContentVoItem.put("content", vo); 
////					  dataListForcustomColumn.add(customContentVoItem);
////				  }
////			  }
////		  }
////	  }
////	  dataListGloble.addAll(dataListForcustomColumn);
//	
//	
//	
//	
////  @Override
////  public List<Map<String, Object>> homeCmsSns(CmsContentVo vo) {
////    //查询新闻(栏目推荐的)
////    List<Map<String, Object>> contentList = new ArrayList<>();
////    List<Map<String, Object>> contents = cmsContentMng.cmsContentByColums(vo);
////    List<Map<String, Object>> cons = new ArrayList<>();
////    if(contents!=null&&contents.size()>0) {
////      for(int k = 0;k<contents.size();k++) {
////        Map<String, Object> cn = new HashMap<>();
////        //爆料数
////        Integer count = snsFeignClient.queryTopicCountsByNewId(Long.valueOf(contents.get(k).get("contentId").toString()));
////        contents.get(k).put("topicCounts", count);
////        cn.put("type", "1");
////        cn.put("content", contents.get(k));
////        cons.add(cn);
////      }
////    }
////    contentList.addAll(cons);
//////    1 栏目 2 专题 3 新鲜事==>
//////    栏目的
////    List<Map<String, Object>> colums = cmsContentMng.cmsContentColum();
////    if(colums!=null&&colums.size()>0) {
////      for(int i = 0;i<colums.size();i++) {
////        List<Map<String, Object>> cons = new ArrayList<>();
////        List<Map<String, Object>> contents = cmsContentMng.cmsContentByColum(Integer.parseInt(colums.get(i).get("columId").toString()), Integer.parseInt(colums.get(i).get("recommNum").toString()));
////        if(contents!=null&&contents.size()>0) {
////          for(int k = 0;k<contents.size();k++) {
////            Map<String, Object> cn = new HashMap<>();
////            cn.put("type", "1");
////            cn.put("content", contents.get(k));
////            cons.add(cn);
////          }
////        }
////        contentList.addAll(cons);
////      }
////    }
////      
////    //查询新鲜事(推荐)
////    List<Map<String, Object>> topicNum = cmsCategoryMng.cmsTopicNum();
////  @Override
////  public List<Map<String, Object>> homeCmsSns(CmsContentVo vo) {
////    //查询新闻(栏目推荐的)
////    List<Map<String, Object>> contentList = new ArrayList<>();
////    List<Map<String, Object>> contents = cmsContentMng.cmsContentByColums(vo);
////    List<Map<String, Object>> cons = new ArrayList<>();
////    if(contents!=null&&contents.size()>0) {
////      for(int k = 0;k<contents.size();k++) {
////        Map<String, Object> cn = new HashMap<>();
////        //爆料数
////        Integer count = snsFeignClient.queryTopicCountsByNewId(Long.valueOf(contents.get(k).get("contentId").toString()));
////        contents.get(k).put("topicCounts", count);
////        cn.put("type", "1");
////        cn.put("content", contents.get(k));
////        cons.add(cn);
////      }
////    }
////    contentList.addAll(cons);
////    //1 栏目 2 专题 3 新鲜事==>
////    //栏目的
////    List<Map<String, Object>> colums = cmsContentMng.cmsContentColum();
////    if(colums!=null&&colums.size()>0) {
////      for(int i = 0;i<colums.size();i++) {
////        List<Map<String, Object>> cons = new ArrayList<>();
////        List<Map<String, Object>> contents = cmsContentMng.cmsContentByColum(Integer.parseInt(colums.get(i).get("columId").toString()), Integer.parseInt(colums.get(i).get("recommNum").toString()));
////        if(contents!=null&&contents.size()>0) {
////          for(int k = 0;k<contents.size();k++) {
////            Map<String, Object> cn = new HashMap<>();
////            cn.put("type", "1");
////            cn.put("content", contents.get(k));
////            cons.add(cn);
////          }
////        }
////        contentList.addAll(cons);
////      }
////    }
////      
////    //查询新鲜事(推荐)
////    List<Map<String, Object>> topicNum = cmsCategoryMng.cmsTopicNum();
//////    List<Map<String, Object>> topics = snsFeignClient.cmsTopics(vo.getPageNo(),vo.getMemberId());
//////    List<Map<String, Object>> tic = new ArrayList<>();
//////    if(topics!=null&&topics.size()>0) {
//////      for(int i =0;i<topics.size();i++) {
//////        Map<String, Object> to = new HashMap<>();
//////        to.put("type", "3");
//////        to.put("topic", topics.get(i));
//////        tic.add(to);
//////      }
//////    }
////////    contentList.addAll(tic);
//////    //查询专题(推荐)
//////    List<Map<String, Object>> cmsCategorys = cmsCategoryMng.cmsCategoryNum();
//////    if(cmsCategorys!=null&&cmsCategorys.size()>0) {
//////      for(int i = 0;i<cmsCategorys.size();i++) {
//////        List<Map<String, Object>> categorys = cmsCategoryMng.cmsCategorys(Integer.parseInt(cmsCategorys.get(i).get("recommNum").toString()));
//////        //栏目下的新闻查询
//////        if(categorys!=null&&categorys.size()>0) {
//////          for(int k = 0;k<categorys.size();k++) {
//////            List<Map<String, Object>> catContents = cmsCategoryMng.cmsContentByCatId(Integer.parseInt(categorys.get(k).get("categoryId").toString()), 3);
//////            if(catContents!=null&&catContents.size()>0) {
//////                for(int z=0;z<catContents.size();z++) {
//////                    Integer count = snsFeignClient.queryTopicCountsByNewId(Long.valueOf(catContents.get(z).get("contentId").toString()));
//////                    catContents.get(z).put("topicCounts", count);
//////                }
//////            }
//////            categorys.get(i).put("contents", catContents);
//////            
//////            categorys.get(i).put("type", "2");
//////          }
//////        }
//////        contentList.addAll(categorys);
//////      }
//////    }
////////    查询自定义栏目
////////    List<Map<String, Object>> defColumn = cmsColumnMng.cmsDefColumn();
////////    if(defColumn!=null&&defColumn.size()>0) {
////////      for(int i = 0;i<defColumn.size();i++) {
////////          List<Map<String, Object>> cts = cmsContentMng.cmsContentByColum(Integer.parseInt(defColumn.get(i).get("columId").toString()), vo.getPageNo());
////////          if(cts!=null&&cts.size()>0) {
////////              for(int k = 0;k<cts.size();k++) {
////////                  Integer count = snsFeignClient.topicCountByContentId(Long.valueOf(cts.get(k).get("contentId").toString()));
////////                  cts.get(k).put("topicCounts", count);
////////              }
////////          }
////////          defColumn.get(i).put("type", "4");
////////          defColumn.get(i).put("contents", cts);
////////      }  
////////    }
////////    contentList.addAll(defColumn);
//////    return contentList;
//////  }
//////    contentList.addAll(tic);
////    //查询专题(推荐)
////    List<Map<String, Object>> cmsCategorys = cmsCategoryMng.cmsCategoryNum();
////    if(cmsCategorys!=null&&cmsCategorys.size()>0) {
////      for(int i = 0;i<cmsCategorys.size();i++) {
////        List<Map<String, Object>> categorys = cmsCategoryMng.cmsCategorys(Integer.parseInt(cmsCategorys.get(i).get("recommNum").toString()));
////        //栏目下的新闻查询
////        if(categorys!=null&&categorys.size()>0) {
////          for(int k = 0;k<categorys.size();k++) {
////            List<Map<String, Object>> catContents = cmsCategoryMng.cmsContentByCatId(Integer.parseInt(categorys.get(k).get("categoryId").toString()), 3);
////            if(catContents!=null&&catContents.size()>0) {
////                for(int z=0;z<catContents.size();z++) {
////                    Integer count = snsFeignClient.queryTopicCountsByNewId(Long.valueOf(catContents.get(z).get("contentId").toString()));
////                    catContents.get(z).put("topicCounts", count);
////                }
////            }
////            categorys.get(i).put("contents", catContents);
////            categorys.get(i).put("type", "2");
////          }
////        }
////        contentList.addAll(categorys);
////      }
////    }
//////    查询自定义栏目
////    List<Map<String, Object>> defColumn = cmsColumnMng.cmsDefColumn();
////    if(defColumn!=null&&defColumn.size()>0) {
////      for(int i = 0;i<defColumn.size();i++) {
////          List<Map<String, Object>> cts = cmsContentMng.cmsContentByColum(Integer.parseInt(defColumn.get(i).get("columId").toString()), vo.getPageNo());
////          if(cts!=null&&cts.size()>0) {
////              for(int k = 0;k<cts.size();k++) {
////                  Integer count = snsFeignClient.topicCountByContentId(Long.valueOf(cts.get(k).get("contentId").toString()));
////                  cts.get(k).put("topicCounts", count);
////              }
////          }
////          defColumn.get(i).put("type", "4");
////          defColumn.get(i).put("contents", cts);
////      }  
////    }
////    contentList.addAll(defColumn);
////    return contentList;
////  }
//
//	
//	
//	
////	@Override
////	public Pagination listContent(CmsContentDto cmsContentDto) {
////		if (cmsContentDto == null) {
////			cmsContentDto = new CmsContentDto();
////		}
////		Finder hql = Finder.create("from ");
////		hql.append(CmsContent.class.getName());
////		hql.append(" where 1=1");
////		if (StringUtils.isNotEmpty(cmsContentDto.getContent())) {
////			hql.append(" and content like :content").setParam("content", "%" + cmsContentDto.getContent() + "%");
////		}
////		hql.append(" order by releaseTime desc");
////		Pagination p = cmsContentMng.find(hql, cmsContentDto.getPageNo(), cmsContentDto.getPageSize());
////		if (p.getList() != null) {
////			List<CmsContentDto> cmsContentDtos = BeanMapper.mapList(p.getList(), CmsContentDto.class);
////			p.setList(cmsContentDtos);
////		}
////		return p;
////	}
//
////	@Override
////	public List<CmsContentVo> queryPromoteNewList(QueryContentVo vo) {
////		List<CmsContentVo> list = buildQueryPromoteNewsByPage(vo);
////		if (null == list || list.size() == 0) {
////			return null;
////		}
//////		List<CmsContentVo> voList = new ArrayList<>();
//////		for (CmsContentVo cmsContentVo : list) {
//////			boolean flag = cmsMemberBehaviorService.validateNewKeywordsExist(vo, cmsContentVo);
//////			if (!flag) {
//////				buildCmsContentVoInList(vo, cmsContentVo);
//////				voList.add(cmsContentVo);
//////			}
//////		}
////		return list;
////	}
//
//	
////	private List<CmsContentVo> buildQueryPromoteNewsByPage(QueryContentVo vo) {
////		AppBehaviorDto appBehaviorDto=new AppBehaviorDto().setMemberId(vo.getUserId()).setBevValueStr("5,6,7");
////		List<Long> contentIds=null;//appFeignClient.loadContentidsInMuiltType(appBehaviorDto);
////		List<CmsContent> cmsContentList = cmsContentMng.queryPromoteNewsByPage(contentIds, vo);
////		if (null == cmsContentList || cmsContentList.size() == 0) {
////			return null;
////		}
////		List<CmsContentVo> list = BeanMapper.mapList(cmsContentList, CmsContentVo.class);
////		return list;
////	}
//
////	private List<Long> getUniterestedNewContentIds(QueryContentVo vo) {
////		//用户行为类别0 详情查看， 1点赞 ，2 收藏，3评论，4爆料，5 不感兴趣，6内容太水，7看过了
////		List<Long> contentIds=appFeignClient.loadContentidsInMuiltType(vo.getUserId(), "8,10,12");
////		
////		
////		List<CmsMemberBehavior> cmsMemberBehaviorList = cmsMemberBehaviorService
////				.queryNewUninterestedListByUser(vo.getUserId(), vo.getImei());
////		List<Long> contentIds = new ArrayList<>();
////		if (null == cmsMemberBehaviorList || cmsMemberBehaviorList.size() == 0) {
////
////		} else {
////			for (CmsMemberBehavior cmsMemberBehavior : cmsMemberBehaviorList) {
////				contentIds.add(cmsMemberBehavior.getContentId());
////			}
////		}
////		return contentIds;
////	}
//	
//	
////	private void putSnsPostListAndPostCounts(QueryContentVo vo, CmsContentDataVo cmsContentDataVo) {
////	SnsPostVo snsPostVo = new SnsPostVo();
////	snsPostVo.setTopicId(cmsContentDataVo.getContentId());
////	snsPostVo.setImei(vo.getImei());
////	snsPostVo.setMemberId(vo.getUserId());
////	List<SnsPostVo> snsPostVoList = snsFeignClient.newsPostList(snsPostVo);
////	if (snsPostVoList!=null&&snsPostVoList.size()>0) {
////		for (SnsPostVo svo : snsPostVoList) {
////			if(svo.getMemberId()!=null&&vo.getUserId()!=null) {
////                UCenterFollow concern = userFeignClient.isConcern(svo.getMemberId(), vo.getUserId());
////				if (concern != null && concern.getConcernType() != null) {
////					svo.setConcernType(concern.getConcernType());
////				}
////			}
////		}
////	} 
////	cmsContentDataVo.setSnsPostVoList(snsPostVoList);
////	Integer newsPostCounts = (null == snsPostVoList || snsPostVoList.size() == 0) ? 0 : snsPostVoList.size();
////	cmsContentDataVo.setNewsPostCounts(newsPostCounts);
////}
//
//
////private void putSnsTopicListAndTopicCounts(HCmsQueryDto cmsQueryDto, CmsContentDataVo cmsContentDataVo) {
////	List<SnsTopicVo> snsTopicList = new ArrayList<SnsTopicVo>();
////	
////	if(cmsContentDataVo.getContentId()!=null) {
////		HBaseSnsQueryDto snsQueryDto=HBaseSnsQueryDto.newForNewsQuery(cmsQueryDto.getContentId(), cmsQueryDto.getUserId());
////		snsTopicList = snsFeignClient.newsTpoicList(snsQueryDto);
////		
////		if(snsTopicList!=null&&snsTopicList.size()>0) {
////			for (SnsTopicVo topicVo : snsTopicList) {
////				if(topicVo.getMemberId()!=null&&cmsQueryDto.getUserId()!=null) {
////                    HUCenterFollowDto concern = userFeignClient.isConcern(topicVo.getMemberId(), cmsQueryDto.getUserId());
////					if (concern != null && concern.getConcernType() != null) {
////						topicVo.setConcernType(concern.getConcernType());
////					}
////				}
////			}
////		}
////	}
////	cmsContentDataVo.setSnsTopicList(snsTopicList);
////	Integer topicCounts = (null == snsTopicList || snsTopicList.size() == 0) ? 0 : snsTopicList.size();
////	cmsContentDataVo.setTopicCounts(topicCounts);
////}
////新闻爆料
////private void putSnsTopicCountsForContent(QueryContentVo vo, CmsContentVo cmsContentVo) {
////	if(cmsContentVo.getContentId()!=null) {
////		List<SnsTopicVo> snsTopicList = snsFeignClient.newTopics(cmsContentVo.getContentId(), vo.getUserId(), vo.getImei());
////		Integer topicCounts = (null == snsTopicList || snsTopicList.size() == 0) ? 0 : snsTopicList.size();
////		cmsContentVo.setTopicCounts(topicCounts);
////	}else {
////		cmsContentVo.setTopicCounts(0);
////	}
////	
////}
//
//
//
////private void putThumbFileInfoList(CmsContentData cmsContentData, CmsContentDataVo cmsContentDataVo) {
////	String ids = cmsContentData.getThumbIds();
////	if (StringUtils.isNotEmpty(ids)) {
////		List<FileInfo> fileInfoList = fileInfoFeignClient.queryFileInfoListByIds(ids);
////		cmsContentDataVo.setFileInfoList(fileInfoList);
////	} else {
////		cmsContentDataVo.setFileInfoList(null);
////	}
////}
//
//
////================================private============e==============================================	
//
////private List<Long> getElasticSearchNewIds(SearchNewsVo vo) {
////	saveSearchHistory(vo, 1);
////	ElasticSearchPage<New> elasticSearchPage = elsFeignClient.news(vo);
////	List<New> newList = elasticSearchPage.getRetList();
////	if (null == newList || newList.size() == 0) {
////		return null;
////	}
////	List<Long> idList = new ArrayList<>();
////	for (New n : newList) {
////		idList.add(Long.parseLong(n.getId()));
////	}
////	return idList;
////}
/////**
//// * 收藏、点赞标识
//// */
////private void putTimeStrAndSomeFlag(QueryContentVo vo,CmsContentDataVo cmsContentDataVo) {
////	
////	boolean collectionFlag = cmsMemberBehaviorService.queryNewCollectionFlag(vo.getContentId(),
////			vo.getUserId(), vo.getImei());
////	cmsContentDataVo.setCollectionFlag(collectionFlag);
////	boolean followFlag = cmsMemberBehaviorService.queryNewFollowFlag(vo.getContentId(), vo.getUserId(), vo.getImei());
////	cmsContentDataVo.setFollowFlag(followFlag);
////}
////private void putAbountNewsList(CmsContent cmsContent, CmsContentDataVo cmsContentDataVo) {
////	String aboutNewIds = cmsContent.getAboutNewIds();
////	if (StringUtils.isNotEmpty(aboutNewIds)) {
////		Finder f = Finder.create("from CmsContent where 1=1");
////		f.append(" and auditStatus=1");
////		f.append(" and FIND_IN_SET(contentId,:contentIds)>0");
////		f.setParam("contentIds", aboutNewIds);
////		List<CmsContent> abountNewList = (List<CmsContent>) cmsContentMng.find(f);
////		cmsContentDataVo.setCmsContentList(abountNewList);
////	} else {
////		cmsContentDataVo.setCmsContentList(null);
////	}
////}
////private void putFileInfoListForContent(CmsContentVo cmsContentVo) {
////	String ids = cmsContentVo.getThumbIds();
////	if (StringUtils.isNotEmpty(ids)) {
////		List<FileInfo> fileInfoList = fileInfoFeignClient.queryFileInfoListByIds(ids);
////		cmsContentVo.setFileInfoList(fileInfoList);
////	} else {
////		cmsContentVo.setFileInfoList(null);
////	}
////}
////private void putNewPostFollowCounts(CmsContentDataVo cmsContentDataVo) {
////	Integer count = snsFeignClient.queryNewPostFollowCount(cmsContentDataVo.getContentId());
////	cmsContentDataVo.setNewPostFollowCounts(count);
////}
////private void putNewFollowCounts(CmsContentData cmsContentData, CmsContentDataVo cmsContentDataVo) {
////	Integer newPostFollowCounts = cmsMemberBehaviorService.queryNewFollowCounts(cmsContentData.getContentId());
////	cmsContentDataVo.setNewsFollowCounts(newPostFollowCounts);
////}
//
//
//
//
//
//
////private void putSnsPostCounts(QueryContentVo vo, CmsContentVo cmsContentVo) {
////	SnsPostVo snsPostVo = new SnsPostVo();
////	snsPostVo.setTopicId(vo.getContentId());
////	List<SnsPostVo> snsPostVoList = snsFeignClient.newsPostList(snsPostVo);
////	Integer newsPostCounts = (null == snsPostVoList || snsPostVoList.size() == 0) ? 0 : snsPostVoList.size();
////	cmsContentVo.setNewsPostCounts(newsPostCounts);
////}
//
////private CmsContentDataVo buildCmsContentDataVo(CmsContentData cmsContentData) {
////	CmsContentDataVo cmsContentDataVo = new CmsContentDataVo();
////	try {
////		BeanUtils.copyProperties(cmsContentDataVo, cmsContentData);
////	} catch (InvocationTargetException e) {
////		e.printStackTrace();
////	} catch (IllegalAccessException e) {
////		e.printStackTrace();
////	}
////	return cmsContentDataVo;
////}
//
////private CmsContentVo buildCmsContentVo(CmsContent cmsContent) {
////	CmsContentVo cmsContentVo = new CmsContentVo();
////	try {
////		BeanUtils.copyProperties(cmsContentVo, cmsContent);
////	} catch (InvocationTargetException e) {
////		e.printStackTrace();
////	} catch (IllegalAccessException e) {
////		e.printStackTrace();
////	}
////	return cmsContentVo;
////}
//
////private CmsContentData buildQueryCmsContentData(QueryContentVo vo) {
////	Finder hql = Finder.create("from CmsContentData where 1=1");
////	hql.append(" and contentId=:contentId");
////	hql.setParam("contentId", vo.getContentId());
////	List<CmsContentData> cmsContentDataList = (List<CmsContentData>) cmsContentDataMng.find(hql);
////	if (null == cmsContentDataList || cmsContentDataList.size() == 0) {
////		return null;
////	}
////	CmsContentData cmsContentData = cmsContentDataList.get(0);
////	return cmsContentData;
////}
//
////private CmsContent buildQueryCmsContent(QueryContentVo vo) {
////	Finder finder = Finder.create("from CmsContent where 1=1");
////	finder.append(" and auditStatus=1");
////	finder.append(" and contentId=:contentId");
////	finder.setParam("contentId", vo.getContentId());
////	List<CmsContent> cmsContentList = (List<CmsContent>) cmsContentMng.find(finder);
////	if (null == cmsContentList || cmsContentList.size() == 0) {
////		return null;
////	}
////	return cmsContentList.get(0);
////}
//
////@Override
////public List<CmsContentVo> queryOtherNews(QueryContentVo vo) {
////	// 所有栏目类别id集合
//////	List<Integer> categoryIds = buildCmsColumnCategoryList(vo);
//////	//新闻过滤
//////	List<Long> contentIds = getUniterestedNewContentIds(vo);
//////	//查询新闻列表
//////	List<CmsContentVo> list = buildOtherNewList(vo, categoryIds, contentIds);
//////	if (null == list || list.size() == 0) {
//////		return null;
//////	}
//////	List<CmsContentVo> voList = new ArrayList<>();
//////	for (CmsContentVo cmsContentVo : list) {
//////		boolean flag = cmsMemberBehaviorService.validateNewKeywordsExist(vo, cmsContentVo);
//////		if (!flag) {
//////			putFileInfoListForContent(cmsContentVo);
//////			putSnsTopicCountsForContent(vo, cmsContentVo);
//////			voList.add(cmsContentVo);
//////		}
//////	}
////	
////	//查询不感兴趣的新闻
////	String noInterests = cmsContentMng.noInterests(vo);
////	//查询新闻
////	if(noInterests!=null) {
////	  vo.setNoInters(noInterests);
////	}
////	List<CmsContentVo> list = cmsContentMng.cmsContentByColumId(vo);
////	//查询爆料数
////	for(CmsContentVo cms : list) {
////      Integer count = snsFeignClient.queryTopicCountsByNewId(cms.getContentId());
////      cms.setTopicCounts(count);
////    }
////	return list;
////}
//
////private List<CmsContentVo> buildOtherNewList(QueryContentVo vo, List<Long> categoryIds, List<Long> contentIds) {
////	List<CmsContent> cmsContentList = buildCmsContentPageListByCategory(vo, categoryIds, contentIds);
////	List<CmsContentVo> list = BeanMapper.mapList(cmsContentList, CmsContentVo.class);
////	return list;
////}
//
////private List<CmsContent> buildCmsContentPageListByCategory(QueryContentVo vo, List<Long> categoryIds, List<Long> contentIds) {
////	List<CmsContent> list  = cmsContentMng.queryCmsContentPageList(vo, categoryIds, contentIds);
////	return list;
////}
//
////private List<Long> buildCmsColumnCategoryList(QueryContentVo vo) {
////	List<CmsColumnCategory> cmsColumnCategoryList = cmsColumnCategoryDao.queryCmsColumnCategoryList(vo);
////	if (null == cmsColumnCategoryList || cmsColumnCategoryList.size() == 0) {
////		return null;
////	}
////	List<Long> categoryIds = new ArrayList<>();
////	for (CmsColumnCategory cmsUserColumn : cmsColumnCategoryList) {
////		categoryIds.add(cmsUserColumn.getCategoryId());
////	}
////	return categoryIds;
////}
//
////@Override
////public CmsMemberBehaviorVo newFollow(CmsMemberBehaviorVo vo) {
////	return cmsMemberBehaviorService.newFollow(vo);
////}
//
////@Override
////public void newUninterested(CmsMemberBehaviorVo vo) {
////	cmsMemberBehaviorService.newUninterested(vo);
////}
//
////@Override
////public SnsTopicVo saveNewTopic(SnsTopicVo vo) {
////	return snsFeignClient.saveNewTopic(vo);
////}
//
////@Override
////public List<SnsTopicVo> newTopicList(Long newId, Long userId, String imei) {
////	List<SnsTopicVo> snsTopicList = new LinkedList<SnsTopicVo>();
////	if(newId!=null) {
////		snsTopicList = snsFeignClient.newTopics(newId, userId, imei);
////		if(snsTopicList!=null&&snsTopicList.size()>0) {
////			for (SnsTopicVo snsTopic : snsTopicList) {
////				if(snsTopic.getMemberId()!=null&&userId!=null) {
////                    UCenterFollow concern = userFeignClient.isConcern(snsTopic.getMemberId(), userId);
////					if (concern != null && concern.getConcernType() != null) {
////						snsTopic.setConcernType(concern.getConcernType());
////					}
////				}
////				snsTopic.setCreateTimeStr(TimeUtils.simpleTimeConverter(snsTopic.getCreateTime()));
////			}
////		}
////	}
////	return snsTopicList;
////}
//
////@Override
////public CmsMemberBehaviorVo newCollection(CmsMemberBehaviorVo vo) {
////	return cmsMemberBehaviorService.newCollection(vo);
////}
//
////@Override
////public boolean newPostFollow(ThumbUpVo vo) {
////	return snsFeignClient.newPostFollow(vo);
////}
//
////@Override
////public boolean newTopicFollow(ThumbUpVo vo) {
////	return snsFeignClient.newTopicFollow(vo);
////}
//
////@Override
////public List<SnsPostVo> newPostList(SnsPostVo vo) {
////	List<SnsPostVo> snsPostVoList = snsFeignClient.newsPostList(vo);
////	if (null == snsPostVoList || snsPostVoList.size() == 0) {
////		return null;
////	}
////	for (SnsPostVo snsPostVo : snsPostVoList) {
////		vo.setPostId(snsPostVo.getPostId());
////		snsPostVo.setUp(snsFeignClient.getUpFlag(vo) == true?"y":"n");
////		if(snsPostVo.getMemberId()!=null&&vo.getMemberId()!=null) {
////            UCenterFollow concern = userFeignClient.isConcern(snsPostVo.getMemberId(), vo.getMemberId());
////			if (concern != null && concern.getConcernType() != null) {
////				snsPostVo.setConcernType(concern.getConcernType());
////			}
////		}
////	}
////	return snsPostVoList;
////}
//
////@Override
////public SnsTopicVo newTopicDetail(SnsTopicVo vo) {
////	SnsTopicVo snsTopicVo = snsFeignClient.newTopicDetail(vo);
////	if(snsTopicVo.getMemberId()!=null&&vo.getMemberId()!=null) {
////        UCenterFollow concern = userFeignClient.isConcern(snsTopicVo.getMemberId(), vo.getMemberId());
////		if (concern != null && concern.getConcernType() != null) {
////			snsTopicVo.setConcernType(concern.getConcernType());
////		}
////	}
////	return snsFeignClient.newTopicDetail(vo);
////}
//
////@Override
////public SnsPostVo newPostDetail(SnsPostVo vo) {
////	SnsPostVo snsPostVo = snsFeignClient.newPostDetail(vo);
////	if (snsPostVo == null) {
////		return null;
////	}
////	List<SnsPostVo> snsPostVoList = snsPostVo.getSnsPostVoList();
////	if (null == snsPostVoList || snsPostVoList.size() == 0){
////		snsPostVo.setSnsPostVoList(null);
////	} else {
////		for (SnsPostVo v : snsPostVoList) {
////			SnsPostVo vos = new SnsPostVo();
////			vos.setPostId(v.getPostId());
////			vos.setImei(vo.getImei());
////			vos.setMemberId(vo.getMemberId());
////			v.setUp(snsFeignClient.getUpFlag(vos) == true ?"y":"n");
////			if(v.getMemberId()!=null&&vo.getMemberId()!=null) {
////                UCenterFollow concern = userFeignClient.isConcern(v.getMemberId(), vo.getMemberId());
////				if (concern != null && concern.getConcernType() != null) {
////					v.setConcernType(concern.getConcernType());
////				}
////			}
////		}
////		snsPostVo.setSnsPostVoList(snsPostVoList);
////	}
////	snsPostVo.setUp(snsFeignClient.getUpFlag(vo) == true ?"y":"n");
////	return snsPostVo;
////}
//
////@Override
////public SnsPostVo newPostOnReview(SnsPostVo vo) {
////	return snsFeignClient.newPostOnReview(vo);
////}
//
//
//
////@Override
////public List<CmsContentDataVo> searchNews(SearchNewsVo vo) {
////	
////	List<Long> idList = null;//getElasticSearchNewIds(vo);
////	if (null == idList || idList.size() == 0) {
////		return null;
////	}
////	vo.setIds(idList);
////	List<CmsContent> cmsContentList = cmsContentMng.queryNewsByEls(vo);
////	if (null == cmsContentList || cmsContentList.size() == 0) {
////		return null;
////	}
////	List<CmsContentDataVo> cmsContentDataVoList = new ArrayList<>();
////	for (CmsContent cmsContent : cmsContentList) {
////		QueryContentVo queryContentVo = new QueryContentVo();
////		queryContentVo.setContentId(cmsContent.getContentId());
////		queryContentVo.setUserId(vo.getUserId());
////		queryContentVo.setImei(vo.getImei());
////		CmsContentDataVo cmsContentDataVo = null;//newDetail(queryContentVo);
////		cmsContentDataVo.setContenttypeId(cmsContent.getContentType().getContentTypeId());
////		if (cmsContentDataVo == null) {
////			continue;
////		}
////		String content = cmsContentDataVo.getContent();
////		String title = cmsContentDataVo.getTitle();
////		if (StringUtils.isNotEmpty(content) && content.contains(vo.getStr())) {
////			content.replaceAll(content, "<span color='red'>" + content + "</span>");
////			cmsContentDataVo.setContent(content);
////		}
////		if (StringUtils.isNotEmpty(title) && title.contains(vo.getStr())) {
////			title.replaceAll(title, "<span color='red'>" + title + "</span>");
////			cmsContentDataVo.setTitle(title);
////		}
////		cmsContentDataVoList.add(cmsContentDataVo);
////	}
////	return cmsContentDataVoList;
////}
//
////@Override
////public List<SnsTopicVo> searchTopics(SearchNewsVo vo) {
////	List<Long> idList = getElasticSearchTopicIds(vo);
////	if (null == idList || idList.size() == 0) {
////		return null;
////	}
////	vo.setIds(idList);
////	List<SnsTopicVo> snsTopicVoList = snsFeignClient.newTopicList(vo);
////	if (null == snsTopicVoList || snsTopicVoList.size() == 0) {
////		return null;
////	}
////	for (SnsTopicVo vos : snsTopicVoList) {
////		SnsTopicVo snsTopicVo = new SnsTopicVo();
////		snsTopicVo.setTopicId(vos.getTopicId());
////		SnsTopicVo topicVo = newTopicDetail(snsTopicVo);
////		if (topicVo == null) {
////			continue;
////		}
////		String content = topicVo.getContent();
////		String title = topicVo.getTitle();
////		if (StringUtils.isNotEmpty(content) && content.contains(vo.getStr())) {
////			String str = content.replaceAll(content, "<span color='red'>" + content + "</span>");
////			topicVo.setContent(str);
////		}
////		if (StringUtils.isNotEmpty(title) && title.contains(vo.getStr())) {
////			String str = title.replaceAll(title, "<span color='red'>" + title + "</span>");
////			topicVo.setTitle(str);
////		}
////		snsTopicVoList.add(topicVo);
////	}
////	return snsTopicVoList;
////}
//
////@Override
////public List<CmsContentDataVo> searchVideos(SearchNewsVo vo) {
////	List<Long> idList = getElasticSearchVideoIds(vo);
////	vo.setIds(idList);
////	List<CmsContent> cmsContentList = cmsContentMng.queryNewsByEls(vo);
////	if (null == cmsContentList || cmsContentList.size() == 0) {
////		return null;
////	}
////	List<CmsContentDataVo> cmsContentDataVoList = new ArrayList<>();
////	for (CmsContent cmsContent : cmsContentList) {
////		QueryContentVo queryContentVo = new QueryContentVo();
////		queryContentVo.setContentId(cmsContent.getContentId());
////		queryContentVo.setUserId(vo.getUserId());
////		queryContentVo.setImei(vo.getImei());
////		CmsContentDataVo cmsContentDataVo = newDetail(queryContentVo);
////		if (cmsContentDataVo == null) {
////			continue;
////		}
////		String content = cmsContentDataVo.getContent();
////		String title = cmsContentDataVo.getTitle();
////		if (StringUtils.isNotEmpty(content) && content.contains(vo.getStr())) {
////			content.replaceAll(content, "<span color='red'>" + content + "</span>");
////			cmsContentDataVo.setContent(content);
////		}
////		if (StringUtils.isNotEmpty(title) && title.contains(vo.getStr())) {
////			title.replaceAll(title, "<span color='red'>" + title + "</span>");
////			cmsContentDataVo.setTitle(title);
////		}
////		cmsContentDataVoList.add(cmsContentDataVo);
////	}
////	return cmsContentDataVoList;
////}
//
////@Override
////public boolean removePost(SnsPostVo vo) {
////	return snsFeignClient.removePost(vo);
////}
//
////@Override
////public boolean removeTopic(SnsTopicVo vo) {
////	return snsFeignClient.removeTopic(vo);
////}
//
////@Override
////public List<UCenterMemberVo> searchMembers(SearchNewsVo vo) {
////	saveSearchHistory(vo, 5);
////	ElasticSearchPage<Member> elasticSearchPage = elsFeignClient.members(vo);
////	List<Member> newList = elasticSearchPage.getRetList();
////	if (null == newList || newList.size() == 0) {
////		return null;
////	}
////	List<Long> idList = new ArrayList<>();
////	for (Member member : newList) {
////		idList.add(Long.parseLong(member.getId()));
////	}
////	vo.setIds(idList);
////	List<UCenterMemberVo> UCenterMemberVoList = userFeignClient.searchMembers(vo);
////	return UCenterMemberVoList;
////}
//
//
////private void saveSearchHistory(SearchNewsVo vo, Integer type) {
////	SearchHistoryVo searchHistoryVo = new SearchHistoryVo();
////	if (StringUtils.isBlank(vo.getStr())) {
////		return;
////	}
////	searchHistoryVo.setSearchContent(vo.getStr());
////	searchHistoryVo.setImei(vo.getImei());
////	searchHistoryVo.setMemberId(vo.getUserId());
////	searchHistoryVo.setType(type);
////	appFeignClient.addSearchHistory(searchHistoryVo);
////}
//
////private List<Long> getElasticSearchVideoIds(SearchNewsVo vo) {
////	saveSearchHistory(vo, 3);
////	ElasticSearchPage<New> elasticSearchPage = elsFeignClient.searchVideos(vo);
////	List<New> newList = elasticSearchPage.getRetList();
////	if (null == newList || newList.size() == 0) {
////		return null;
////	}
////	List<Long> idList = new ArrayList<>();
////	for (New n : newList) {
////		idList.add(Long.parseLong(n.getId()));
////	}
////	return idList;
////}
//
////private List<Long> getElasticSearchTopicIds(SearchNewsVo vo) {
////	if (vo.getTopicType() == 0) {
////		saveSearchHistory(vo, 4);
////	}
////	if (vo.getTopicType() == 1) {
////		saveSearchHistory(vo, 2);
////	}
////	ElasticSearchPage<New> elasticSearchPage = elsFeignClient.searchTopics(vo);
////	List<New> newList = elasticSearchPage.getRetList();
////	if (null == newList || newList.size() == 0) {
////		return null;
////	}
////	List<Long> idList = new ArrayList<>();
////	for (New n : newList) {
////		idList.add(Long.parseLong(n.getId()));
////	}
////	return idList;
////}
//
////@Override
////public List<CmsContentVo> cmsContentCategory(CmsContentVo vo) {
////
////return null;
////}
//
////@Override
////public List<CmsContentVo> topCmsContentList() {
////List<CmsContentVo> list = cmsContentMng.topCmsContentList();
//////处理爆料数
////for(CmsContentVo cms : list) {
////  Integer count = snsFeignClient.queryTopicCountsByNewId(cms.getContentId());
////  cms.setTopicCounts(count);
////}
////return list;
////}
//	
//	
//	
//	
//	
////  @Override
////  public List<CmsContentVo> contentsBycategoryId(CmsContentVo vo) {
////    List<CmsContentVo> list = cmsContentMng.contentsBycategoryId(vo);
////  //处理爆料数
////    for(CmsContentVo cms : list) {
////      Integer count = snsFeignClient.queryTopicCountsByNewId(cms.getContentId());
////      cms.setTopicCounts(count);
////    }
////    return list;
////  }
//
//
//
////	private void putTimeStrAndSomeFlag(QueryContentVo vo,CmsContentDataVo cmsContentDataVo) {
////	
////	boolean collectionFlag = cmsMemberBehaviorService.queryNewCollectionFlag(vo.getContentId(),
////			vo.getUserId(), vo.getImei());
////	cmsContentDataVo.setCollectionFlag(collectionFlag);
////	boolean followFlag = cmsMemberBehaviorService.queryNewFollowFlag(vo.getContentId(), vo.getUserId(), vo.getImei());
////	cmsContentDataVo.setFollowFlag(followFlag);
////}
//
////    @Override
////    public List<NearTopicVo> cmsNearPeoples(NearTopicVo vo) {
////        
////        return snsFeignClient.cmsNearPeoples(vo.getLongitude(),vo.getLatitude(),vo.getUmemberId());
////    }
//
////    @Override
////    public List<CmsContentVo> nearContents(CmsContentVo vo) {
////        
////        return cmsContentMng.nearContents(vo);
////    }
//}
