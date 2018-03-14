package com.hww.sns.webservice.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.hww.app.api.AppFeignClient;
import com.hww.app.common.dto.AppBehaviorDto;
import com.hww.base.util.BeanUtils;
import com.hww.base.util.Constants;
import com.hww.base.util.R;
import com.hww.cms.common.dto.HCmsQueryDto;
import com.hww.cms.common.vo.CmsContentDataVo;
import com.hww.els.api.ElsFeignClient;
import com.hww.els.common.entity.ESContent;
import com.hww.file.api.FileFeignClient;
import com.hww.framework.common.exception.HServiceLogicException;
import com.hww.sns.common.dto.HBaseSnsQueryDto;
import com.hww.sns.common.dto.HSnsTopicCreateDto;
import com.hww.sns.common.dto.SnsTopicDto;
import com.hww.sns.common.entity.SnsForum;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.entity.SnsTopicEsSyncFail;
import com.hww.sns.common.manager.SnsForumMng;
import com.hww.sns.common.manager.SnsPostMng;
import com.hww.sns.common.manager.SnsTopicEsSyncFailMng;
import com.hww.sns.common.manager.SnsTopicMng;
import com.hww.sns.common.util.EsContentCovertUtil;
import com.hww.sns.common.util.LocationUtils;
import com.hww.sns.common.vo.SnsPostVo;
import com.hww.sns.common.vo.SnsTopicVo;
import com.hww.sns.webservice.consts.SnsConsts;
import com.hww.sns.webservice.service.SnsCacheProxyService;
import com.hww.sns.webservice.service.SnsPostService;
import com.hww.sns.webservice.service.SnsTopicService;
import com.hww.ucenter.api.UcenterFeignClient;
import com.hww.ucenter.common.dto.HUCenterFollowDto;
import com.hww.ucenter.common.dto.UCenterMemberDto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("snsTopicService")
@org.springframework.transaction.annotation.Transactional
public class SnsTopicServiceImpl implements SnsTopicService {
	private static final Log log = LogFactory.getLog(SnsTopicServiceImpl.class);
  @Autowired
  SnsTopicMng snsTopicMng;
  @Autowired
  SnsPostMng snsPostMng;
  @Autowired
  UcenterFeignClient userFeignClient;
//  @Autowired
//  ThumbUpMng thumbUpMng;
  @Autowired
  FileFeignClient fileFeignClient;


  @Autowired
  AppFeignClient appFeignClient;
  @Autowired SnsForumMng snsForumMng;
  @Autowired ElsFeignClient elsFeignClient;
  @Autowired SnsPostService snsPostService;
  
  @Autowired SnsTopicEsSyncFailMng snsTopicEsSyncFailMng;
  @Autowired SnsCacheProxyService snsCacheProxyService;
  @Async
  private void sendXqBev(AppBehaviorDto behaviorDto) {
	  try {
		  R res=appFeignClient.createBehavior(behaviorDto);
	  }catch (Exception e) {
	}
  }
  
  //发表爆料或者新鲜事
  @org.springframework.transaction.annotation.Transactional
  @Override
  public SnsTopic publishTopic(HSnsTopicCreateDto snsTopicVo) {
	  if(snsTopicVo.getMemberId()==null) {
			 throw new HServiceLogicException("用户未登录");
		 }
	  UCenterMemberDto currentUser = userFeignClient.findById(snsTopicVo.getMemberId());
	  if(currentUser==null||(currentUser.getSnsDisabled()!=null&&1==currentUser.getSnsDisabled())) {
		  throw new HServiceLogicException("用户被禁言");
	  }
	  SnsTopicDto snsTopic = new SnsTopicDto();
	 try {
		BeanUtils.copyProperties(snsTopic, snsTopicVo);
	} catch (Exception e) {
		e.printStackTrace();
	}
	 Integer topicType= snsTopicVo.getTopicType();
	 
	 if(!SnsConsts.topicTypeList.contains(topicType)) {
		 throw new HServiceLogicException("类型错误");
	 }
	 snsTopic.setTopicType(topicType);
	 
	 SnsForum  snsForum =snsForumMng.find(Long.valueOf(0));
//	 snsTopic.setForumId(Long.valueOf(topicType));
	 snsTopic.setForumId(Long.valueOf(0));//2018-0201改为发表的全是新鲜事
	 
	 // autditFlow 0 先发后审 ,1 先审后发 ,,show_status 0不可见 1可见
	 Integer autditFlow=snsForum.getAuditFlow();
	 if(0==autditFlow.intValue()) {//设置可见状态
		 snsTopic.setShowStatus(1);
	 }else {
		 snsTopic.setShowStatus(0);
	 }
	 snsTopic.setStatus(Short.valueOf("1"));//0 删除 1 正常
	 snsTopic.setAuditStatus(2);// auditstatus 0审核未通过 1 审核通过 2 新提交
	 snsTopic.setAuditFlow(snsForum.getAuditFlow());
	 snsTopic.setSiteId(Constants.siteId);
	 snsTopic.setCreateTime(new Timestamp(System.currentTimeMillis()));
	 snsTopic.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
	 SnsTopicDto t2 =	 snsTopicMng.saveTopic(snsTopic);
	 SnsTopic snsTopic1=new SnsTopic();
	  org.springframework.beans.BeanUtils.copyProperties(t2,snsTopic1);
	 
// 此处不再创建爆料行为--------------------------------20180130----------------
		 //类型(新鲜事:0  爆料:1),默认为0
		 //如果是爆料。。。。
		 if(1==topicType.intValue()) {
			 // 0 详情查看， 1点赞 ，2 收藏，3评论，4爆料，5 感兴趣，6内容太水，7看过了
			 //0  新闻  1 帖子/新鲜事  2 评论
//			 AppBehaviorDto behaviorDto =new AppBehaviorDto(  snsTopicVo.getMemberId(), 
//					 snsTopicVo.getRelatednewsId(),  AppBehaviorDto.BevType.b4_bl, 1,  AppBehaviorDto.PlatType.b0_news);
//	         appFeignClient.createBehavior(behaviorDto);
		 }
		  if (snsTopic.getFileId() != null) {
		      String url = fileFeignClient.getUrlByFileId(snsTopic.getFileId().toString());
		      snsTopic.setUrl(url);
		  }
		 try {
			 if(t2!=null) {
				 publishTopicToES( snsTopic1) ;
			 }
		 }catch (Exception e) {
		}
    return snsTopic1;
  }
  //@Async
  @Override
  public void publishTopicToES(SnsTopic snsTopic) {
  	  try {
  		  ESContent esContent = EsContentCovertUtil.toESContent(snsTopic);
  		 R r= elsFeignClient.createContentFeginApi(esContent);
  		 if(!r.isOk()) {
  			 	SnsTopicEsSyncFail snsTopicEsSyncFail = new SnsTopicEsSyncFail();
					snsTopicEsSyncFail.setTopicId(snsTopic.getTopicId());
					snsTopicEsSyncFail.setCreateTime(new Timestamp(System.currentTimeMillis()));
					snsTopicEsSyncFail.setFailWhat(0);
					snsTopicEsSyncFail.setIsDealSuccess(0);
					snsTopicEsSyncFailMng.save(snsTopicEsSyncFail);
  		 }
  	     }catch (Exception e) {
  	    	 	SnsTopicEsSyncFail snsTopicEsSyncFail = new SnsTopicEsSyncFail();
					snsTopicEsSyncFail.setTopicId(snsTopic.getTopicId());
					snsTopicEsSyncFail.setCreateTime(new Timestamp(System.currentTimeMillis()));
					snsTopicEsSyncFail.setFailWhat(0);
					snsTopicEsSyncFail.setIsDealSuccess(0);
					snsTopicEsSyncFailMng.save(snsTopicEsSyncFail);
  		 }
  }
  
  @Override
  public SnsTopicVo loadSnsTopicDetail(Long topicId) {

	    SnsTopicVo sp = new SnsTopicVo();
	    
	    // 查询新鲜事的详细内容
	    SnsTopic topic = snsTopicMng.find(topicId);
	    if(topic==null) {
	    	return null;
	    }
	
	    try {
	    	BeanUtils.copyProperties(sp, topic);
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	    UCenterMemberDto memberTopicAuthor = userFeignClient.findById(topic.getMemberId());
	    
	    if(topic.getAnonymous()!=null&&1==topic.getAnonymous().intValue()) {
	    	sp.setNickName("匿名海客");
  		}else {
  			if(memberTopicAuthor!=null) {//是否匿名1匿名2不匿名
  				sp.setNickName(memberTopicAuthor.getNickName());
  		    	sp.setSex(memberTopicAuthor.getSex());
  		    	sp.setAvatar(memberTopicAuthor.getAvatar());
  		 	 }
  		}
	    
//	    if(memberTopicAuthor!=null) {
//	    	 sp.setNickName(memberTopicAuthor.getNickName());
//	         sp.setSex(memberTopicAuthor.getSex());
//	         sp.setAvatar(memberTopicAuthor.getAvatar());
//	    }
	   
	    if (topic.getFileId() != null) {
	        String url = fileFeignClient.getUrlByFileId(topic.getFileId().toString());
	        sp.setUrl(url);
	    }
	  
	    sp.setContent(topic.getContent());
	    sp.setCreateTime(topic.getCreateTime());
	    sp.setAddress(topic.getAddress());
	    sp.setTopicId(topic.getTopicId());
	    sp.setMemberId(topic.getMemberId());
	    sp.setTopicType(topic.getTopicType());
	    sp.setTopicId(topic.getTopicId());
	    sp.setContent(topic.getContent());
	    sp.setCreateTime(topic.getCreateTime());
	    sp.setAddress(topic.getAddress());
	    sp.setMemberId(topic.getMemberId());
	    sp.setAuhtorMemberId(""+topic.getMemberId());
	    sp.setTopicType(topic.getTopicType());
	    sp.setAnonymous(topic.getAnonymous());
	    sp.setAuditFlow(topic.getAuditFlow());
	    sp.setAuditStatus(topic.getAuditStatus());
	    sp.setPubli(topic.getPubli());
	    sp.setForumId(topic.getForumId());
		  
	    sp.setTitle(topic.getTitle());
	    sp.setOutLink(topic.getOutLink());
	    sp.setLongitude(topic.getLongitude());
	    sp.setLatitude(topic.getLatitude());
		Map<String,Integer> behaviorCountMap=appFeignClient.behaviorCount(topic.getId(),AppBehaviorDto.PlatType.b1_topic);
		sp.setUpNum(behaviorCountMap.get("1"));
		//sp.setCommentNum(behaviorCountMap.get("3"));
		Integer topicCount= snsPostMng.loadCountForTopic(topic.getTopicId(),1);
		sp.setCommentNum(topicCount==null?0:topicCount);
		
		if(sp.getTopicType()!=null&&sp.getTopicType().intValue()==1&&sp.getRelatednewsId()!=null) {
			  HCmsQueryDto cmsQueryDto=new HCmsQueryDto().setMemberId(null).setContentId(sp.getRelatednewsId()).setPageNo(null).setPageSize(null);
					  CmsContentDataVo cmsContentDataVo=snsCacheProxyService.loadNewsNoPostAndTopicFeginApi(cmsQueryDto);
			  if(cmsContentDataVo!=null) {
				  Map<String, Object> cmsContentData=Maps.newHashMap();
				  cmsContentData.put("cmsContentData", cmsContentDataVo);
				  sp.setMoreData(cmsContentData);
			  }
		  }
	return sp;
  }
  @Override
  public SnsTopicVo detail(HBaseSnsQueryDto paramDto) {

    SnsTopicVo sp = new SnsTopicVo();
    // 查询新鲜事的详细内容
    SnsTopic topic = snsTopicMng.find(paramDto.getTopicId());
    if(topic==null) {
    	return null;
    }
    try {
    	BeanUtils.copyProperties(sp, topic);
      } catch (Exception e) {
        e.printStackTrace();
      }
    UCenterMemberDto memberTopicAuthor = userFeignClient.findById(topic.getMemberId());
    
    if(topic.getAnonymous()!=null&&1==topic.getAnonymous().intValue()) {
    	sp.setNickName("匿名海客");
		}else {
			if(memberTopicAuthor!=null) {//是否匿名1匿名2不匿名
				sp.setNickName(memberTopicAuthor.getNickName());
		    	sp.setSex(memberTopicAuthor.getSex());
		    	sp.setAvatar(memberTopicAuthor.getAvatar());
		 	 }
		}
    
//    if(memberTopicAuthor!=null) {
//    	 sp.setNickName(memberTopicAuthor.getNickName());
//         sp.setSex(memberTopicAuthor.getSex());
//         sp.setAvatar(memberTopicAuthor.getAvatar());
//       
//    }
    
    try {
    	 double distance =
    	            LocationUtils.getDistance(Double.valueOf(paramDto.getLatitude()), Double.valueOf(paramDto.getLongitude()),
    	                    Double.valueOf(topic.getLatitude()), Double.valueOf(topic.getLongitude()));
    	 sp.setDistance(distance);
    }catch (Exception e) {
    	 sp.setDistance(null);
	}
   
    if (topic.getFileId() != null) {
        String url = fileFeignClient.getUrlByFileId(topic.getFileId().toString());
        sp.setUrl(url);
    }
  
    sp.setContent(topic.getContent());
    sp.setCreateTime(topic.getCreateTime());
    sp.setAddress(topic.getAddress());
    sp.setTopicId(topic.getTopicId());
    sp.setMemberId(topic.getMemberId());
    sp.setTopicType(topic.getTopicType());
    sp.setTopicId(topic.getTopicId());
    sp.setContent(topic.getContent());
    sp.setCreateTime(topic.getCreateTime());
    sp.setAddress(topic.getAddress());
    sp.setMemberId(topic.getMemberId());
    sp.setAuhtorMemberId(""+topic.getMemberId());
    sp.setTopicType(topic.getTopicType());
    sp.setAnonymous(topic.getAnonymous());
    sp.setAuditFlow(topic.getAuditFlow());
    sp.setAuditStatus(topic.getAuditStatus());
    sp.setPubli(topic.getPubli());
    sp.setForumId(topic.getForumId());
    sp.setImei(paramDto.getImei());
	  
    sp.setTitle(topic.getTitle());
    sp.setOutLink(topic.getOutLink());
    sp.setLongitude(topic.getLongitude());
    sp.setLatitude(topic.getLatitude());
    sp.setRelatednewsId(topic.getRelatednewsId());
    //如果是爆料加载新闻主体
    if(sp.getTopicType()!=null&&sp.getTopicType().intValue()==1&&sp.getRelatednewsId()!=null) {
		  HCmsQueryDto cmsQueryDto=new HCmsQueryDto().setMemberId(paramDto.getMemberId()).setContentId(sp.getRelatednewsId()).setPageNo(null).setPageSize(null);
				  CmsContentDataVo cmsContentDataVo=snsCacheProxyService.loadNewsNoPostAndTopicFeginApi(cmsQueryDto);
		  if(cmsContentDataVo!=null) {
			  Map<String, Object> cmsContentData=Maps.newHashMap();
			  cmsContentData.put("cmsContentData", cmsContentDataVo);
			  sp.setMoreData(cmsContentData);
		  }
	  }
    
	//是否收藏,点赞
	//用户行为类别用户行为类别0 详情查看， 1点赞 ，2 收藏，3评论，4爆料，5 不感兴趣，6内容太水，7看过了
    if(paramDto.getMemberId()!=null) {
        String upFlag = appFeignClient.behaviorExist(
        		new AppBehaviorDto(paramDto.getMemberId(), paramDto.getTopicId(), AppBehaviorDto.BevType.b1_dz)
        		.setPlateType(AppBehaviorDto.PlatType.b1_topic));
    	sp.setUp(upFlag);
    }else {
    //	sp.setUp("n");
    }
	 //计算用户关注状态
    if(paramDto.getMemberId()!=null&&paramDto.getMemberId()!=null&&!paramDto.getMemberId().equals(topic.getMemberId())) {
    	HUCenterFollowDto concern = userFeignClient.isConcern(paramDto.getMemberId(),topic.getMemberId() );
    	sp.setConcernType(concern.getConcernType()==null?0:concern.getConcernType());
    }else {
    	sp.setConcernType(0);
    }
    
	//点赞评论等数量
	////用户行为类别用户行为类别0 详情查看， 1点赞 ，2 收藏，3评论，4爆料，5 不感兴趣，6内容太水，7看过了
	Map<String,Integer> behaviorCountMap=appFeignClient.behaviorCount(paramDto.getTopicId(),AppBehaviorDto.PlatType.b1_topic);
	sp.setUpNum(behaviorCountMap.get("1"));
	//sp.setCommentNum(behaviorCountMap.get("3"));
	Integer topicCount= snsPostMng.loadCountForTopic(topic.getTopicId(),1);
	sp.setCommentNum(topicCount==null?0:topicCount);

	HBaseSnsQueryDto snsQueryDto=new HBaseSnsQueryDto(paramDto.getTopicId(),paramDto.getMemberId(),paramDto.getLongitude(),paramDto.getLatitude());
	snsQueryDto.setPageNo(paramDto.getPageNo());
	snsQueryDto.setPageSize(paramDto.getPageSize());
	List<SnsPostVo> snsPostVoList=snsPostService.loadPostListByTopicId(snsQueryDto);

	 sp.setSp(snsPostVoList);
	 
	    //不是自己的帖子--添加查看的用户行为
	    if(paramDto.getMemberId()!=null&&!paramDto.getMemberId().equals(memberTopicAuthor.getMemberId())) {
	   	 //用户行为类别0 详情查看， 1点赞 ，2 收藏，3评论，4爆料，5 不感兴趣，6内容太水，7看过了
	    	AppBehaviorDto behaviorDto =new AppBehaviorDto(paramDto.getMemberId(), paramDto.getTopicId(),  AppBehaviorDto.BevType.b0_xq, AppBehaviorDto.BevValue.b1_ok,  AppBehaviorDto.PlatType.b1_topic);
	    	try {
	    		sendXqBev(behaviorDto);//异步发送请求
	    	}catch (Exception e) {
			}
		 }
    return sp;
  }
  
  @Override
  public void deleteTopic(Long memberId,Long topicId) {
	  
      Long authorId= snsTopicMng.loadMemberAuthorById(topicId);
      if(authorId==null) {
    	  throw new HServiceLogicException("未查询到数据");
      }
      if(!memberId.equals(authorId)) {
          throw new HServiceLogicException("只能删除自己的动态");
      }
      snsTopicMng.deleteTopic(topicId);
      
	 
    //同步到搜索引擎 
	try {
		 deleteFromES( topicId);
	 }catch (Exception e) {}
	  
  }
    @Async
    @Override
    public void deleteFromES(Long topicId) {
    	  try {
    		  SnsTopic snsTopic= snsTopicMng.find(topicId);
    		  ESContent esContent = EsContentCovertUtil.toESContent(snsTopic);
    		  esContent.setStatus(0);
    		  esContent.setShowStatus(0);
    		 R r= elsFeignClient.updateContentFeginApi(esContent);
    		 if(!r.isOk()) {
    			 	SnsTopicEsSyncFail snsTopicEsSyncFail = new SnsTopicEsSyncFail();
					snsTopicEsSyncFail.setTopicId(topicId);
					snsTopicEsSyncFail.setCreateTime(new Timestamp(System.currentTimeMillis()));
					snsTopicEsSyncFail.setFailWhat(0);
					snsTopicEsSyncFail.setIsDealSuccess(0);
					snsTopicEsSyncFailMng.save(snsTopicEsSyncFail);
    		 }
    	     }catch (Exception e) {
    	    	 	SnsTopicEsSyncFail snsTopicEsSyncFail = new SnsTopicEsSyncFail();
					snsTopicEsSyncFail.setTopicId(topicId);
					snsTopicEsSyncFail.setCreateTime(new Timestamp(System.currentTimeMillis()));
					snsTopicEsSyncFail.setFailWhat(0);
					snsTopicEsSyncFail.setIsDealSuccess(0);
					snsTopicEsSyncFailMng.save(snsTopicEsSyncFail);
    		 }
    }


@Override
  public List<SnsTopicVo> loadMyTopicList(HBaseSnsQueryDto snsQueryDto){
	  List<SnsTopicVo> tps = new ArrayList<>();
	  if(snsQueryDto.getMemberId()==null) {
		  return tps;
	  }
	  UCenterMemberDto currentUser = userFeignClient.findById(snsQueryDto.getMemberId());
	  UCenterMemberDto topicAuthor =currentUser;// userFeignClient.findById(snsQueryDto.getAuthorMemberId());
	  
	  snsQueryDto.setAuthorMemberId(snsQueryDto.getMemberId());
	  List<SnsTopic> memberTopicList = snsTopicMng.loadTopicListByAuthorMemberId(snsQueryDto);
	  
	  if(memberTopicList!=null&&memberTopicList.size()>0) {
		  for (SnsTopic topic : memberTopicList) {
			   SnsTopicVo snsTopicVo = constructSnsTopicVoForList( topic, currentUser,topicAuthor ,snsQueryDto);
			   tps.add(snsTopicVo);
		   }
	  }
	  return tps;
  }
  @Override
  public Map<String,List<SnsTopicVo>> loadMemberTopicList(HBaseSnsQueryDto snsQueryDto) {
	  Map<String,List<SnsTopicVo>>  resMap=Maps.newHashMap();
	  List<SnsTopicVo> tps = new ArrayList<>();
	  if(snsQueryDto.getAuthorMemberId()==null) {
		  return resMap;
	  }
	  UCenterMemberDto currentUser =null;
	  if(snsQueryDto.getMemberId()!=null) {
		  currentUser = userFeignClient.findById(snsQueryDto.getMemberId());
	  }
	  UCenterMemberDto topicAuthor =null;
	  if(snsQueryDto.getAuthorMemberId().equals(snsQueryDto.getMemberId())) {
		  topicAuthor=currentUser;
	  }else {
		  topicAuthor = userFeignClient.findById(snsQueryDto.getAuthorMemberId());
	  }
	  
	  List<SnsTopic> memberTopicList = snsTopicMng.loadTopicListByAuthorMemberId(snsQueryDto);
	  
	  if(memberTopicList!=null&&memberTopicList.size()>0) {
		  for (SnsTopic topic : memberTopicList) {
			   SnsTopicVo snsTopicVo = constructSnsTopicVoForList( topic, currentUser,topicAuthor ,snsQueryDto);
			   tps.add(snsTopicVo);
		   }
		  resMap.put("memberTopics", tps);
	  }
	   List<Long> upTopicIds= appFeignClient.loadBehaviorContentIds(
			   new AppBehaviorDto().setMemberId(topicAuthor.getMemberId()).setBevType(AppBehaviorDto.BevType.b1_dz)
			   .setPlateType(AppBehaviorDto.PlatType.b1_topic));
	   if(upTopicIds==null||upTopicIds.isEmpty()) {
		   return resMap;
	   }
	   
	   List<SnsTopic> upTopicsList = snsTopicMng.loadTopicListByIds(upTopicIds,3);
	   if(upTopicsList==null||upTopicsList.isEmpty()) {
		   return resMap;
	   }
	   //目标用户点赞过的几个帖子
	   List<SnsTopicVo> upTopics = new ArrayList<>();
	   
//	   for (SnsTopic topic : upTopicsList) {
//		  
//		  // 注意角色转换------------------------
//		   UCenterMemberDto currentUser2= new UCenterMemberDto();
//		   try {
//			BeanUtils.copyProperties(currentUser2, topicAuthor);
//		   } catch (Exception e) {}
//		   //作者刚刚赞过的--作者是currentUser了
//		   UCenterMemberDto topicAuthor2 = userFeignClient.findById(topic.getMemberId());
//		   //赞过的currentUser===
//		   SnsTopicVo snsTopicVo = constructSnsTopicVoForList( topic, currentUser2,topicAuthor2 ,snsQueryDto);
//		   upTopics.add(snsTopicVo);
//	   }
	   //目标用户点赞过的帖子，----》此处应该是 登录用户  针对目标用户赞过的帖子的 点赞状态
	   for (SnsTopic topic : upTopicsList) {
			  
			  // 注意角色转换------------------------
//			   UCenterMemberDto currentUser2= new UCenterMemberDto();
//			   try {
//				BeanUtils.copyProperties(currentUser2, topicAuthor);
//			   } catch (Exception e) {}
			   //作者刚刚赞过的--作者是currentUser了
			   UCenterMemberDto topicAuthor2 = userFeignClient.findById(topic.getMemberId());
			   //赞过的currentUser===此处应该是 登录用户  针对目标用户赞过的帖子的 点赞状态
			   SnsTopicVo snsTopicVo = constructSnsTopicVoForList( topic, currentUser,topicAuthor2 ,snsQueryDto);
			   upTopics.add(snsTopicVo);
		   }
	   resMap.put("upTopics", upTopics);
       return resMap;

  }
  
  private  List<SnsTopic> createRandomList(List<SnsTopic> list, int n) {
      Set<Integer> container=Sets.newHashSet();
      
      List<SnsTopic> listNew = new ArrayList<SnsTopic>();
      if (list.size() <= n) {
          return list;
      } 
      int size=list.size();
      
          while (container.size() < n) {
              int random = (int) (Math.random() * size);
              if (!container.contains(random)) {
            	  container.add(random);
                  listNew.add(list.get(random));
              }
          }
          return listNew;
  }
  @Override
  public List<SnsTopicVo> loadRecommTopics(HBaseSnsQueryDto snsQueryDto) {
	  int limit=snsQueryDto.getPageSize()==null?3:snsQueryDto.getPageSize();
	  //多查5倍的数据
  	List<SnsTopic> snsTopicList=snsTopicMng.loadTopicListForRecomm(limit*5);
  	
  	List<SnsTopic> snsTopicListRomdom =Lists.newArrayList();
  	
  	if(snsTopicList!=null && snsTopicList.size() >= limit) {
  		//随机取出想要的数据
  		snsTopicListRomdom = createRandomList(snsTopicList,limit);
  	}
  	
  	
     UCenterMemberDto currentUser =null;
     if(snsQueryDto.getMemberId()!=null) {
     	currentUser = userFeignClient.findById(snsQueryDto.getMemberId());
     }
     List<SnsTopicVo> list = new ArrayList<SnsTopicVo>();
     
     for (SnsTopic topic : snsTopicListRomdom) {
     	UCenterMemberDto topicAuthor = userFeignClient.findById(topic.getMemberId());
 		   SnsTopicVo snsTopicVo = constructSnsTopicVoForList( topic, currentUser,topicAuthor ,snsQueryDto);
 		   list.add(snsTopicVo);
 	   }
     return list;
  }
  
  
  @Override
  public List<SnsTopicVo> loadTopicListByNewsId(HBaseSnsQueryDto paramDto) {
    List<SnsTopic> snsTopicList = snsTopicMng.loadTopicListByNewsId(paramDto);
    if (null == snsTopicList || snsTopicList.size() == 0) {
      return Lists.newArrayList();
    }
    UCenterMemberDto currentUser =null;
    if(paramDto.getMemberId()!=null) {
    	currentUser = userFeignClient.findById(paramDto.getMemberId());
    }
    List<SnsTopicVo> list = new ArrayList<SnsTopicVo>();
    
    for (SnsTopic topic : snsTopicList) {
    	UCenterMemberDto topicAuthor = userFeignClient.findById(topic.getMemberId());
		   SnsTopicVo snsTopicVo = constructSnsTopicVoForList( topic, currentUser,topicAuthor ,paramDto);
		   list.add(snsTopicVo);
	   }
    return list;
  }
  
  

  @Override
  public List<SnsTopicVo> loadTopicListByIds(List<Long> topicIds,HBaseSnsQueryDto snsQueryDto){
	  UCenterMemberDto currentUser=null;
	  if(snsQueryDto.getMemberId()!=null) {
		  currentUser = userFeignClient.findById(snsQueryDto.getMemberId());
	  }
	  List<SnsTopic> searchTopics = snsTopicMng.loadTopicListByIds(topicIds);
	  
	  if(searchTopics==null||searchTopics.isEmpty()) {
	    	return new ArrayList<SnsTopicVo>(0);
	    }

	    List<SnsTopicVo> list = new ArrayList<SnsTopicVo>();
	    for(SnsTopic topic : searchTopics) {
	    	//不是自己的
	    	if(currentUser!=null&&!currentUser.getMemberId().equals(topic.getMemberId())) {
	    		
	    	}
	    	UCenterMemberDto topicAuthor = userFeignClient.findById(topic.getMemberId());
	    	SnsTopicVo vo=constructSnsTopicVoForList( topic, currentUser, topicAuthor, snsQueryDto);
	    	list.add(vo);
	    }
	    return list;
  }
	@Override
	public List<SnsTopicVo> loadTopicListByIdsShowAllStatus(List<Long> topicIds,HBaseSnsQueryDto snsQueryDto){
		UCenterMemberDto currentUser=null;
		if(snsQueryDto.getMemberId()!=null) {
			currentUser = userFeignClient.findById(snsQueryDto.getMemberId());
		}
		List<SnsTopic> searchTopics = snsTopicMng.loadTopicListByIdsShowAllStatus(topicIds);

		if(searchTopics==null||searchTopics.isEmpty()) {
			return new ArrayList<SnsTopicVo>(0);
		}

		List<SnsTopicVo> list = new ArrayList<SnsTopicVo>();
		for(SnsTopic topic : searchTopics) {
			UCenterMemberDto topicAuthor = userFeignClient.findById(topic.getMemberId());
			SnsTopicVo vo=constructSnsTopicVoForList( topic, currentUser, topicAuthor, snsQueryDto);
			list.add(vo);
		}
		return list;
	}


  @Override
  public Integer loadUserTopicCount(Long memberId,Integer topicType) {
	  if(memberId==null) {
		  return 0;
	  }
    return snsTopicMng.loadUserTopicCount(memberId,topicType);
  }

  @Override
  public Integer queryTopicCountsByNewId(Long newId) {
    return snsTopicMng.queryTopicCountsByNewId(newId);
  }
  
  
  private SnsTopicVo constructSnsTopicVoForList(SnsTopic topic,UCenterMemberDto currentUser,UCenterMemberDto topicAuthor,HBaseSnsQueryDto snsQueryDto) {
	  
	  SnsTopicVo vo=new SnsTopicVo();
	  try {
	    	BeanUtils.copyProperties(vo, topic);
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	  try {
	    	 double distance =LocationUtils.getDistance(Double.valueOf(snsQueryDto.getLatitude()), Double.valueOf(snsQueryDto.getLongitude()),
	    	                    Double.valueOf(topic.getLatitude()), Double.valueOf(topic.getLongitude()));
	    	 vo.setDistance(distance);
	    }catch (Exception e) {
	    	vo.setDistance(null);
		}
	  
	  if (topic.getFileId() != null) {
	      String url = fileFeignClient.getUrlByFileId(topic.getFileId().toString());
	      vo.setUrl(url);
	  }
	  vo.setTopicId(topic.getTopicId());
	  vo.setContent(topic.getContent());
	  vo.setCreateTime(topic.getCreateTime());
	  vo.setAddress(topic.getAddress());
	  vo.setMemberId(topic.getMemberId());
	  vo.setAuhtorMemberId(""+topic.getMemberId());
	  vo.setTopicType(topic.getTopicType());
	  vo.setAnonymous(topic.getAnonymous());
	  vo.setAuditFlow(topic.getAuditFlow());
	  vo.setAuditStatus(topic.getAuditStatus());
	  vo.setPubli(topic.getPubli());
	  vo.setForumId(topic.getForumId());
	  vo.setImei(snsQueryDto.getImei());
	  
	  vo.setTitle(topic.getTitle());
	  vo.setOutLink(topic.getOutLink());
	  vo.setLongitude(topic.getLongitude());
	  vo.setLatitude(topic.getLatitude());
	  vo.setRelatednewsId(topic.getRelatednewsId());
//	  UCenterMember memberTopicAuthor = userFeignClient.findById(topic.getMemberId());
	  
	  Long currentUserIdx=currentUser==null?null:currentUser.getMemberId();
	  if(vo.getTopicType()!=null&&vo.getTopicType().intValue()==1&&vo.getRelatednewsId()!=null) {
		  HCmsQueryDto cmsQueryDto=new HCmsQueryDto().setMemberId(currentUserIdx).setContentId(vo.getRelatednewsId()).setPageNo(null).setPageSize(null);
				  CmsContentDataVo cmsContentDataVo=snsCacheProxyService.loadNewsNoPostAndTopicFeginApi(cmsQueryDto);
		  if(cmsContentDataVo!=null) {
			  Map<String, Object> cmsContentData=Maps.newHashMap();
			  cmsContentData.put("cmsContentData", cmsContentDataVo);
			  vo.setMoreData(cmsContentData);
		  }
	  }
 	 //--添加作者头像
	   if(topic.getAnonymous()!=null&&1==topic.getAnonymous().intValue()) {
  			vo.setNickName("匿名海客");
  		}else {
  			if(topicAuthor!=null) {//是否匿名1匿名2不匿名
  		    	vo.setNickName(topicAuthor.getNickName());
  		    	vo.setSex(topicAuthor.getSex());
  		    	vo.setAvatar(topicAuthor.getAvatar());
  		 	 }
  		}
	    
		//是否收藏,点赞-
		//用户行为类别用户行为类别0 详情查看， 1点赞 ，2 收藏，3评论，4爆料，5 不感兴趣，6内容太水，7看过了
	    //当前用户登录，并且不是自己的帖子才会有是否关注收藏点赞的判断
	    if(currentUser!=null&&currentUser.getMemberId()!=null) {
            String upFlag = appFeignClient.behaviorExist(
            		new AppBehaviorDto(currentUser.getMemberId(), topic.getTopicId(), AppBehaviorDto.BevType.b1_dz)
            		.setPlateType(AppBehaviorDto.PlatType.b1_topic));
            log.debug("-AAA--upFlag---"+upFlag);
	    	vo.setUp(upFlag);
	    }
	    
		 //计算用户关注状态---不是自己的帖子
	    if(currentUser!=null&&currentUser.getMemberId()!=null&&!currentUser.getMemberId().equals(topic.getMemberId())) {
	    	Long currentUserId=currentUser.getMemberId();
	    	Long topicAuthorId=topic.getMemberId();
	    	log.debug("currentUserId: "+currentUserId+"||topicAuthorId: "+topicAuthorId);
	    	HUCenterFollowDto concern = userFeignClient.isConcern(currentUserId,topicAuthorId);
	    	log.debug("-AAA--concern.getConcernType()---"+concern.getConcernType());
	    	vo.setConcernType(concern.getConcernType()==null?0:concern.getConcernType());
	    }else {
	    	vo.setConcernType(0);
	    }
	    
		//点赞评论等数量
		Map<String,Integer> behaviorCountMap=appFeignClient.behaviorCount(topic.getTopicId(),AppBehaviorDto.PlatType.b1_topic);
		vo.setUpNum(behaviorCountMap.get("1"));
		//vo.setCommentNum(behaviorCountMap.get("3"));
		
		vo.setCollectNum(behaviorCountMap.get("2"));
		
		
		Integer topicCount= snsPostMng.loadCountForTopic(topic.getTopicId(),1);
		vo.setCommentNum(topicCount==null?0:topicCount);
		return vo;
  }
  
  @Override
  public Long loadTopicIdByNewsId(Long newsId) {
  	
  	return snsTopicMng.loadTopicIdForQueryPostByNewsId(newsId);
  }

  @Override
  public List<SnsTopicVo> loadConcernUserTopics(HBaseSnsQueryDto snsQueryDto) {
	  List<SnsTopicVo> tps = new ArrayList<>();
	  if(snsQueryDto.getMemberId()==null) {
		  return Lists.newArrayList();
	  }
    // 查询用户自己的信息
	  UCenterMemberDto currentUser = userFeignClient.findById(snsQueryDto.getMemberId());

    // 先查询我关注的人
    String concerns = userFeignClient.concernUser(snsQueryDto.getMemberId());
    List<Long> concernUserIdList=Lists.newArrayList();
    
    if(StringUtils.hasText(concerns)) {
         concernUserIdList=Stream.of(concerns.split(",")).map(val->Long.valueOf(val)).collect(Collectors.toList());
    }
    if(concernUserIdList==null||concernUserIdList.isEmpty()) {
    	return Lists.newArrayList();
    }
   
    // 根据关注的人查询新鲜事列表
    List<SnsTopic> concernTopics = snsTopicMng.loadConcernUserTopics(snsQueryDto, concernUserIdList);
    if(concernTopics==null||concernTopics.isEmpty()) {
    	return  Lists.newArrayList();
    }
    for(SnsTopic snsTopic: concernTopics) {
    	UCenterMemberDto topicAuthor = userFeignClient.findById(snsTopic.getMemberId());
    	SnsTopicVo snsTopicVo =constructSnsTopicVoForList( snsTopic, currentUser, topicAuthor, snsQueryDto);
    	tps.add(snsTopicVo);
    }
    return tps;
  }
  

}
