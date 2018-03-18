package com.hww.sns.common.manager;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.sns.common.dao.SnsPostDao;
import com.hww.sns.common.dto.HBaseSnsQueryDto;
import com.hww.sns.common.dto.SnsPostDto;
import com.hww.sns.common.entity.SnsPost;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.vo.PostListVo;
import com.hww.sns.common.vo.TopPostListVo;

import java.util.List;

public interface SnsPostMng
	extends
		IBaseEntityMng<Long, SnsPost, SnsPostDao>
{

//    List<SnsPost> myPosts(SnsTopic myTopicVo);

//    List<SnsPost> smallPostList(PostListVo vo);

    List<SnsPost> loadPostListByTopicId(HBaseSnsQueryDto queryDto);
    
     List<SnsPost> loadChildPostList(HBaseSnsQueryDto queryDto);

     SnsPost loadAuthorAndParentById(Long postId);
     
	 void deletePost(Long postId);
	 void deletePostByTopicId(Long topicId);

	Integer loadCountForPost(Long postId,Integer showStatus);

	Integer loadCountForTopic(Long topicId,Integer showStatus);

	Integer loadCountForNews(Long newsId,Integer showStatus);

	SnsPostDto saveSnsPost(SnsPost post);
	
//    List<SnsPost> smPostList(PostListVo vo);
}
