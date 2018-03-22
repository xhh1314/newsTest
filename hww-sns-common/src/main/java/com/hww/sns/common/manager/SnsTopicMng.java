package com.hww.sns.common.manager;

import java.util.List;
import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.sns.common.dao.SnsTopicDao;
import com.hww.sns.common.dto.HBaseSnsQueryDto;
import com.hww.sns.common.dto.SnsTopicDto;
import com.hww.sns.common.entity.SnsTopic;

public interface SnsTopicMng
	extends
		IBaseEntityMng<Long, SnsTopic, SnsTopicDao>
{

	
     List<SnsTopic> loadTopicListByAuthorMemberId(HBaseSnsQueryDto snsQueryDto);
    
     List<SnsTopic> loadConcernUserTopics(HBaseSnsQueryDto snsQueryDto, List<Long> concernUserIds);
    
     Integer loadUserTopicCount(Long memberId,Integer topicType);
    
    
    Integer queryTopicCountsByNewId(Long newId);

    //对应新闻的id--查找topic--post
    Long  loadTopicIdForQueryPostByNewsId(Long newsId);
      
    List<SnsTopic> loadTopicListByNewsId(HBaseSnsQueryDto snsQueryDto);
    
    
    List<SnsTopic> loadTopicListByIds(List<Long> topicIds, int limit);
    
    List<SnsTopic> loadTopicListByIds(List<Long> topicIds);

    /**
     * 根据id查询toptic,查询出新提交和已审核状态的的数据
     * @param topicIds
     * @return
     */
    List<SnsTopic> loadTopicListByIdsShowAllStatus(List<Long> topicIds);
    
    Long loadMemberAuthorById(Long topicId);

	void deleteTopic(Long topicId);

	List<SnsTopic> loadTopicListForRecomm(int limit);

	SnsTopicDto saveTopic(SnsTopicDto Dto);

    void updateTopicUpNum(Long topicId, Integer upNum);
    void updateTopicCommentNum(Long topicId,Integer upnum,Integer commentNum);


//    Integer topicCountByContentId(Long contentId);
    
    
//    public List<NearTopicVo> nearPeoples(NearTopicVo vo);

//  public List<SnsTopic> loadNearTopics(HBaseSnsQueryDto snsQueryDto);

    

//    SnsTopic newTopicReply(SnsTopicVo vo);

//    List<SnsTopic> newTopicReplyList(Long newId, Long topicId);

//    List<SnsTopicVo> newTopicList(SearchNewsVo vo);
    
    
//    List<Map<String,Object>> cmsTopics(Integer num);
    
//    SnsTopic upTopic(SnsTopic snsTopic);
    

	

	
}
