package com.hww.sns.webservice.controller;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hww.app.api.AppFeignClient;
import com.hww.app.common.dto.AppBehaviorDto;
import com.hww.base.util.R;
import com.hww.framework.common.exception.HServiceLogicException;
import com.hww.sns.common.dto.HBaseSnsQueryDto;
import com.hww.sns.common.dto.HSnsPostCreateDto;
import com.hww.sns.common.dto.HSnsPostUpParamDto;
import com.hww.sns.common.manager.SnsPostMng;
import com.hww.sns.common.manager.SnsTopicMng;
import com.hww.sns.common.util.ValidatorUtils;
import com.hww.sns.common.vo.SnsPostVo;
import com.hww.sns.webservice.service.SnsPostService;
import com.hww.sns.webservice.service.SnsTopicService;

@RestController
@RequestMapping("/sns/post")
public class SnsPostController {

    private static final Log log = LogFactory.getLog(SnsPostController.class);
    
    @Autowired
    SnsPostService snsPostService;
    @Autowired
    SnsPostMng snsPostMng;
    @Autowired
    SnsTopicMng snsTopicMng;
    @Autowired
    SnsTopicService snsTopicService;
    @Autowired
    AppFeignClient appFeignClient;
    /**
     * 对新闻发表评论
     *
     */
    @RequestMapping(value = "publishNewsPost.do", method = RequestMethod.POST)
    public R publishNewsPost( HSnsPostCreateDto postCreateDto) {
        log.info("----------APP端新闻评论开始----------");
        if (postCreateDto == null) {
            return R.error(500, "参数为空");
        }
        if(postCreateDto.getMemberId()==null) {
        	return R.error(1, "请登录");
        }
        if(postCreateDto.getAnonymous()==null) {
        	return R.error(1, "请请选择是否匿名");
        }
        if(!(postCreateDto.getAnonymous().intValue()==1||postCreateDto.getAnonymous()==2)) {
        	return R.error(1, "是否匿名参数错误");
        }
        if(postCreateDto.getNewsId()==null) {
        	return R.error(1, "评论新闻参数不能为空");
        }
        if(!StringUtils.hasText(postCreateDto.getContent())) {
        	return R.error(1, "请请选择输入评论内容");
        }
        if(postCreateDto.getContent().length()>1000) {
        	return R.error(1, "内容超长");
        }
        //新闻评论
        postCreateDto.setTopicType(2);//评论类型(0:新鲜事评论1:爆料评论2:新闻评论)
    	postCreateDto.setType(2);
        SnsPostVo SnsPost = null;
        try {
            SnsPost = snsPostService.createNewsPost(postCreateDto);
        } catch (HServiceLogicException e) {
            return R.error(1,e.getMessage());
        } catch (Exception e){
            log.error("用户发表评论异常：{}",e);
            return R.error(1,"发表评论失败！");
        }
        log.info("----------APP端新闻评论结束----------");
        return R.ok().put("data", SnsPost);
    }
    
    /**
     * 对帖子发表评论
     *
     */
    @RequestMapping(value = "publishTopicPost.do", method = RequestMethod.POST)
    @ResponseBody
    public R publishTopicPost(HSnsPostCreateDto postCreateDto) {
        Map<String, String> map = ValidatorUtils.validateEntity(postCreateDto);
        if (!map.get("status").equals("200")) {
            return R.error(1, map.get("message"));
        }
        if (postCreateDto == null) {
            return R.error(500, "参数为空");
        }
        if(postCreateDto.getMemberId()==null) {
        	return R.error(1, "请登录");
        }
        if(postCreateDto.getAnonymous()==null) {
        	return R.error(1, "请请选择是否匿名");
        }
        if(!(postCreateDto.getAnonymous().intValue()==1||postCreateDto.getAnonymous()==2)) {
        	return R.error(1, "是否匿名参数错误");
        }
        if(postCreateDto.getTopicId()==null) {
        	return R.error(1, "评论新鲜事参数不能为空");
        }
        if(!StringUtils.hasText(postCreateDto.getContent())) {
        	return R.error(1, "请请选择输入评论内容");
        }
        if(postCreateDto.getContent().length()>1000) {
        	return R.error(1, "内容超长");
        }
        try {
        	//postCreateDto.setTopicType(0);//评论类型(0:新鲜事评论1:爆料评论2:新闻评论)
        	postCreateDto.setTopicType(2);//评论类型(0:新鲜事评论1:爆料评论2:新闻评论)
        	postCreateDto.setType(0);
        	SnsPostVo snsPost = snsPostService.createTopicPost(postCreateDto);
            return R.ok().put("data", snsPost);
        } catch (HServiceLogicException e) {
            return R.error(1,e.getMessage());
        }  catch (Exception e) {
            log.error("发布评论异常:{}",e);
            return R.error(1, "评论失败!");
        }

    }
    
    
    /**
     * 删除评论
     * @param snsDeleteDto
     * @return
     */
      @RequestMapping(value = "deletePost.do", method = RequestMethod.POST)
      public  R deletePost( HBaseSnsQueryDto snsDeleteDto) {
    	  try {
    		  snsPostService.deletePost(snsDeleteDto);
              return R.ok();
          }  catch (HServiceLogicException e) {
              return R.error(1,e.getMessage());
          } catch (Exception e) {
              e.printStackTrace();
              return R.error(1, "删除失败");
          }
      }
      
    /**
     * 详情
     */
    @RequestMapping(value = "postDetail.do", method = RequestMethod.POST)
    public R postDetail( HBaseSnsQueryDto snsqueryDto) {
    	 try {
    		 SnsPostVo snsPostVo = snsPostService.loadSnsPostDetail(snsqueryDto);
             return R.ok().put("data", snsPostVo);
         } catch (HServiceLogicException e) {
             return R.error(1,e.getMessage());
         }  catch (Exception e) {
             e.printStackTrace();
             return R.error(1, "获取数据失败");
         }
    }

  
    
    /**
     * 评论点赞
     *
     */
    @RequestMapping(value = "upPost.do", method = RequestMethod.POST)
    @ResponseBody
    public R upPost(HSnsPostUpParamDto postUpParamDto) {

        Map<String, String> map = ValidatorUtils.validateEntity(postUpParamDto);
        if (!map.get("status").equals("200")) {
            return R.error(1, map.get("message"));
        }
        try {
            String isExists = appFeignClient.behaviorExist(
            		new AppBehaviorDto(postUpParamDto.getMemberId(), postUpParamDto.getPostId(), AppBehaviorDto.BevType.b1_dz)
            		.setPlateType(AppBehaviorDto.PlatType.b2_post));
            if("y".equals(isExists)) {
            	return R.error("已经点过赞，请不要重复操作");
            }
      	  	AppBehaviorDto behaviorDto =new AppBehaviorDto(postUpParamDto.getMemberId(), postUpParamDto.getPostId(),  AppBehaviorDto.BevType.b1_dz, AppBehaviorDto.BevValue.b1_ok,  AppBehaviorDto.PlatType.b2_post);
      	   return appFeignClient.createBehavior(behaviorDto);
          
        } catch (HServiceLogicException e) {
            return R.error(1,e.getMessage());
        }  catch (Exception e) {
            e.printStackTrace();
            return R.error(1, "点赞失败");
        }

    }


    /**
     * 取消点赞
     */
    @RequestMapping(value = "cancelUpPost.do", method = RequestMethod.POST)
    @ResponseBody
    public R cancelUpPost(HSnsPostUpParamDto postUpParamDto) {

        Map<String, String> map = ValidatorUtils.validateEntity(postUpParamDto);
        if (!map.get("status").equals("200")) {
            return R.error(1, map.get("message"));
        }
        try {
        	AppBehaviorDto behaviorDto =new AppBehaviorDto(  postUpParamDto.getMemberId(), postUpParamDto.getPostId(),   AppBehaviorDto.BevType.b1_dz, AppBehaviorDto.BevValue.b0_cancel,  AppBehaviorDto.PlatType.b2_post);
        	return  appFeignClient.createBehavior(behaviorDto);
         } catch (HServiceLogicException e) {
                return R.error(1,e.getMessage());
         } catch (Exception e) {

            e.printStackTrace();
            return R.error(1, "取消点赞失败");
        }

    }
    
    
    @RequestMapping(value = "loadNewsPostList.do", method = RequestMethod.POST)
    public  R loadNewsPostList( HBaseSnsQueryDto snsqueryDto) {
    	
    	  Map<String, String> map = ValidatorUtils.validateEntity(snsqueryDto);
          if (!map.get("status").equals("200")) {
              return R.error(1, map.get("message"));
          }
          try {
        	  List<SnsPostVo> topicPostList = snsPostService.loadPostListByNewsId(snsqueryDto);
        	  
        	  return R.ok().put("data", topicPostList);
        	  
           } catch (HServiceLogicException e) {
                  return R.error(1,e.getMessage());
           } catch (Exception e) {
              e.printStackTrace();
              return R.error(1, "获取失败");
          }
    }
    
    @RequestMapping(value = "loadTopicPostList.do", method = RequestMethod.POST)
    public  R loadTopicPostList( HBaseSnsQueryDto snsqueryDto) {
    	
    	  Map<String, String> map = ValidatorUtils.validateEntity(snsqueryDto);
          if (!map.get("status").equals("200")) {
              return R.error(1, map.get("message"));
          }
          try {
        	  List<SnsPostVo> topicPostList = snsPostService.loadPostListByTopicId(snsqueryDto);
        	  
        	  return R.ok().put("data", topicPostList);
        	  
           } catch (HServiceLogicException e) {
                  return R.error(1,e.getMessage());
           } catch (Exception e) {
              e.printStackTrace();
              return R.error(1, "获取失败");
          }
    }
    
    @RequestMapping(value = "loadChildPostList.do", method = RequestMethod.POST)
    public R loadChildPostList(HBaseSnsQueryDto queryDto) {
    	 
    	 
    	  Map<String, String> map = ValidatorUtils.validateEntity(queryDto);
          if (!map.get("status").equals("200")) {
              return R.error(1, map.get("message"));
          }
          try {
        	  List<SnsPostVo>  childPostList = snsPostService.loadChildPostList(queryDto);
        	  
        	  return R.ok().put("data", childPostList);
        	  
           } catch (HServiceLogicException e) {
                  return R.error(1,e.getMessage());
           } catch (Exception e) {
              e.printStackTrace();
              return R.error(1, "获取失败");
          }
    }
    
    
//==================FeginApi================start==================================================    
    /**
     * 新闻评论列表--FeginApi
     *
     */
    @RequestMapping(value = "newsPostListFeginApi.do", method = RequestMethod.POST)
    public  List<SnsPostVo> newsPostListFeginApi(@RequestBody HBaseSnsQueryDto snsqueryDto) {
         List<SnsPostVo> topicPostList = snsPostService.loadPostListByNewsId(snsqueryDto);
         return topicPostList;
    }
   
    @RequestMapping(value = "loadPostByIdFeginApi.do", method = RequestMethod.POST)
    public  SnsPostVo loadPostByIdFeginApi(@RequestParam("postId")Long postId) {
    	SnsPostVo snsPostVo = snsPostService.loadSnsPostDetail(postId);
         return snsPostVo;
    }
    
    
    @RequestMapping(value = "loadCountForNewsFeginApi.do", method = RequestMethod.POST)
    public Integer loadCountForNewsFeginApi(@RequestParam("newsId")Long newsId) {
    	Integer count=snsPostService.loadCountForNews(newsId,1);
    	return count==null?0:count;
    }
    @RequestMapping(value = "loadCountForTopicFeginApi.do", method = RequestMethod.POST)
    public Integer loadCountForTopicFeginApi(@RequestParam("topicId")Long topicId) {
    	Integer count=snsPostService.loadCountForTopic(topicId,1);
    	return count==null?0:count;
    }
    
    @RequestMapping(value = "loadCountForPostFeginApi.do", method = RequestMethod.POST)
    public Integer loadCountForPostFeginApi(@RequestParam("postId")Long postId) {
    	Integer count=snsPostService.loadCountForPost(postId,1);
    	return count==null?0:count;
    }
    /**
     * 
     * 子评论列表(分页)
     *
     */
    @RequestMapping(value = "childPostListFeginApi.do", method = RequestMethod.POST)
    public List<SnsPostVo> childPostListFeginApi(HBaseSnsQueryDto queryDto) {
    	 List<SnsPostVo>  childPostList = snsPostService.loadChildPostList(queryDto);
    	 return childPostList;
    }
//==================FeginApi================end==================================================   
//  @RequestMapping(value = "loadNewsPostList.do", method = RequestMethod.POST)
//  public  R loadNewsPostList(@RequestBody HBaseSnsQueryDto snsqueryDto) {
//  	
//  	  Map<String, String> map = ValidatorUtils.validateEntity(snsqueryDto);
//        if (!map.get("status").equals("200")) {
//            return R.error(1, map.get("message"));
//        }
//        
//        try {
//      	  List<SnsPostVo> topicPostList = snsPostService.loadPostListByNewsId(snsqueryDto);
//      	  
//      	  return R.ok().put("data", topicPostList);
//      	  
//         } catch (HServiceLogicException e) {
//                return R.error(1,e.getMessage());
//         } catch (Exception e) {
//            e.printStackTrace();
//            return R.error(1, "获取失败");
//        }
//        
//  }

}
