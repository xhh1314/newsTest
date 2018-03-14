package com.hww.sns.webadmin.service;

import java.util.List;

import com.hww.base.common.page.Pagination;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.vo.SnsTopicVo;

public interface SnsTopicService {

	//发表主题
	void save(SnsTopicVo snsTopicVo);
	
	SnsTopicVo query(SnsTopicVo snsTopicVo);
	
	//带评论回复的主题查询
	SnsTopicVo queryWithPost(SnsTopicVo snsTopicVo);
	
	//查询用户历史主题
	Pagination list(SnsTopicVo searchVo,Integer pageNo, Integer pageSize);
	
	//查询新鲜事|爆料列表
	Pagination listNewsByType(SnsTopicVo searchVo,Integer pageNo, Integer pageSize);
;	
	void delete(Integer topicid,Integer userid);
	
	//新鲜事转爆料
	void convertfreshToBroke(List<Long> topicIds,Long newsId);
	List<SnsTopic> findByIDs(List<SnsTopicVo> asList);
	
	List<SnsTopic> findByForumId(Long ForumId);
	void updateSnsTopic(SnsTopicVo snsTopicVo);
	
	List<SnsTopic> alllist();
	
	
	SnsTopicVo findById(Long topicId);
	
	
	void TopicToNews(Long topicId,Long relatednewsId);
	
	void topicNotToHot(Long topicId);
}
