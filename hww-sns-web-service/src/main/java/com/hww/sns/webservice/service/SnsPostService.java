package com.hww.sns.webservice.service;

import java.util.List;

import com.hww.sns.common.dto.HBaseSnsQueryDto;
import com.hww.sns.common.dto.HSnsPostCreateDto;
import com.hww.sns.common.dto.HSnsPostUpParamDto;
import com.hww.sns.common.entity.SnsPost;
import com.hww.sns.common.vo.PostListVo;
import com.hww.sns.common.vo.SnsPostVo;

public interface SnsPostService {

	SnsPostVo createTopicPost(HSnsPostCreateDto postCreateDto);
    
     SnsPostVo createNewsPost(HSnsPostCreateDto postCreateDto);
    
//      SnsPost postDetail(Long postId);
      
     SnsPostVo loadSnsPostDetail(HBaseSnsQueryDto snsqueryDto);
     
     SnsPostVo loadSnsPostDetail(Long postId);
     
     
//     void postUp(HSnsPostUpParamDto postUpParamDto);
    
//     void cancelPostUp(HSnsPostUpParamDto postUpParamDto);
    
     List<SnsPostVo> loadPostListByTopicId(HBaseSnsQueryDto queryDto);
     
     List<SnsPostVo> loadPostListByNewsId(HBaseSnsQueryDto queryDto);
     
     List<SnsPostVo> loadChildPostList(HBaseSnsQueryDto queryDto);
     
      void deletePost( HBaseSnsQueryDto snsDeleteDto);

	Integer loadCountForNews(Long newsId,Integer showStatus);

	Integer loadCountForTopic(Long topicId,Integer showStatus);

	Integer loadCountForPost(Long postId,Integer showStatus);
     
    
//     void delete(ThumbUp tu);
    
    
//     List<PostListVo> smallPostList(PostListVo vo);
    
    
    
//     ThumbUp isUpPost(ConcernTopicVo vo);
//    
//     void delete(ThumbUp tu);
    
    
    
//     List<PostListVo> smPostList(PostListVo vo);

//    List<SnsPostVo> newsPostList(SnsPostVo vo);

   

    
  

//    boolean removePost(SnsPostVo vo);
    
 
        
//    SnsPost update(SnsPost snsPost);

//    Integer queryNewPostFollowCount(Long newId);
    

//	List<SnsPostVo> loadPostList(HSnsPostListQueryParamDto paramDto );

	
}
