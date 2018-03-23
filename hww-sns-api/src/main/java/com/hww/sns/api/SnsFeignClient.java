package com.hww.sns.api;

import com.hww.base.util.R;
import com.hww.sns.common.dto.HBaseSnsQueryDto;
import com.hww.sns.common.dto.HBaseSnsQueryFeginApiDto;
import com.hww.sns.common.entity.SnsPost;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.vo.SnsPostVo;
import com.hww.sns.common.vo.SnsTopicVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "hww-sns-web-service-consumer")
public interface SnsFeignClient {

	@RequestMapping(value = "/sns/post/newsPostListFeginApi.do")
	List<SnsPostVo> newsPostList(@RequestBody HBaseSnsQueryDto snsqueryDto);

	@RequestMapping(value = "/sns/topic/topicCountsByNewIdFeginApi.do/{newId}")
	Integer queryTopicCountsByNewId(@PathVariable("newId") Long newId);

	@RequestMapping(value = "/sns/topic/newTopicsFeginApi.do")
	List<SnsTopicVo> newsTpoicList(@RequestBody HBaseSnsQueryDto snsqueryDto);

	@RequestMapping(value = "/sns/topic/loadRecommTopicsFeginApi.do")
	List<SnsTopicVo> loadRecommTopicsFeginApi(@RequestBody HBaseSnsQueryDto snsqueryDto);

	@RequestMapping(value = "/sns/topic/loadTopicByIdsFeginApi.do")
	List<SnsTopicVo> loadTopicByIdsFeginApi(@RequestBody HBaseSnsQueryFeginApiDto queryFeginApiDto);

	/**
	 * post for news
	 * @param newsId
	 * @return
	 */

	@RequestMapping(value = "/sns/post/loadCountForNewsFeginApi.do", method = RequestMethod.POST)
	public Integer loadCountForNewsFeginApi(@RequestParam("newsId") Long newsId);

	@RequestMapping(value = "/sns/topic/topicCountsByNewIdFeginApi.do/{newId}", method = RequestMethod.POST)
	public Integer topicCountsByNewIdFeginApi(@PathVariable("newId") Long newId);

	// from -ucenter----------------------------
	@RequestMapping(value = "/sns/topic/loadUserTopicCountFeginApi.do/{memberId}/{topicType}", method = RequestMethod.POST)
	Integer findCountById(@PathVariable("memberId") Long memberId, @PathVariable("topicType") Integer topicType);

	@RequestMapping(value = "/sns/post/loadPostByIdFeginApi.do", method = RequestMethod.POST)
	SnsPostVo postDetail(@RequestParam("postId") Long postId);

	@RequestMapping(value = "/sns/topic/loadTopicByIdFeginApi.do", method = RequestMethod.POST)
	SnsTopicVo topicDetail(@RequestParam("topicId") Long topicId);

	@RequestMapping(value = "/sns/topic/myTopicsFeginApi.do/{memberId}", method = RequestMethod.POST)
	List<SnsTopicVo> myTopics(@PathVariable("memberId") Long memberId);
	// from -ucenter----------------------------

	@RequestMapping(value = "/sns/post/getSnsPostFromDatabase.do", method = RequestMethod.GET)
	SnsPost getSnsPostFromDatabase(@RequestParam("postId") Long postId);

	@RequestMapping(value = "/sns/topic/getSnsTopicFromDataBase.do",method = RequestMethod.GET)
	public SnsTopic getSnsTopicFromDataBase(@RequestParam("topicId") Long topicId);

	@RequestMapping(value = "/sns/topic/updateTopicCommentAndLikeNumber.do")
	R updateTopicCommentAndLikeNumber(@RequestParam("topicId") Long topicId, @RequestParam("upNum") Integer upNum,
			@RequestParam("commentNum") Integer commentNum);

	@RequestMapping(value = "/sns/post/updateLikesAndCommentNum.do", method = RequestMethod.POST)
	R updateLikesAndCommentNum(@RequestParam("postId") Long postId, @RequestParam("upNum") Integer upNum,
			@RequestParam("commentNum") Integer commentNum);


}