package com.hww.sns.webservice.service;

import com.hww.sns.common.dto.HBaseSnsQueryDto;
import com.hww.sns.common.dto.HSnsTopicCreateDto;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.vo.*;

import java.util.List;
import java.util.Map;

public interface SnsTopicService {

    SnsTopic publishTopic(HSnsTopicCreateDto snsTopicDto);
    
    void publishTopicToES(SnsTopic snsTopic);
    
    void deleteTopic(Long memberId,Long topicId);
    
	void deleteFromES(Long topicId);
	
	
    SnsTopicVo detail(HBaseSnsQueryDto paramDto);
    
    SnsTopicVo loadSnsTopicDetail(Long topicId);
    

    List<SnsTopicVo> loadMyTopicList(HBaseSnsQueryDto snsQueryDto);

    Map<String,List<SnsTopicVo>> loadMemberTopicList(HBaseSnsQueryDto snsQueryDto);
    
    
    List<SnsTopicVo> loadTopicListByIds(List<Long> topicIds,HBaseSnsQueryDto snsQueryDto);

    /**
     * 根据id列示出所有topic,不管showStatus的状态，这个方法是实现:附近的人可以看见自己发的未审核通过的状态
     * @param topicIds
     * @param snsQueryDto
     * @return
     */
    List<SnsTopicVo> loadTopicListByIdsShowAllStatus(List<Long> topicIds,HBaseSnsQueryDto snsQueryDto);
    
    Integer loadUserTopicCount(Long memberId,Integer topicType);
    
    Long  loadTopicIdByNewsId(Long newsId);

	//爆料数
    Integer queryTopicCountsByNewId(Long newId);
    
    List<SnsTopicVo> loadTopicListByNewsId(HBaseSnsQueryDto paramDto);
    
    

	List<SnsTopicVo> loadConcernUserTopics(HBaseSnsQueryDto snsQueryDto);

	List<SnsTopicVo> loadRecommTopics(HBaseSnsQueryDto snsQueryDto);
    
//  SnsTopicVo detail(DetailTopicVo snsTopicVo);
	
//  List<SnsTopicVo> loadNearTopics(HBaseSnsQueryDto snsQueryDto);
	
//    List<ConcernTopicVo> concernTopics(ConcernTopicVo concernTopicVo);

//    SnsTopic upNumTopic(UpTopicDto dto);


//    Integer concernUserCount(ConcernTopicVo vo);

//    Integer concernMyCount(ConcernTopicVo vo);

    
//    void topicUp(HSnsTopicUpParamDto topicUpParamDto);
    
//    void cancelUpTopic(HSnsTopicUpParamDto topicUpParamDto);




//    List<NearTopicVo> nearPeoples(NearTopicVo vo);



//    SnsTopicVo saveNewTopic(SnsTopicVo snsTopicVo);



//    SnsTopic newTopicReply(SnsTopicVo vo);

//    List<SnsTopic> newTopicReplyList(Long newId, Long topicId);

//    String concernUser(Long memberId);

//    SnsTopicVo newTopicDetail(SnsTopicVo vo);

//    List<MyTopicVo> userTopicsDetail(UserSnsTopicVo myTopicVo);

//    UCenterMember userDetail(ConcernTopicVo myTopicVo);
    
//    SnsTopic update(SnsTopic snsTopic);
    
//    MyTopicVo upTopic(UserSnsTopicVo vo);

//    List<UCenterSign> mySign(MySignDto dto);

//    UCenterFollow isConcern(Long memberId, Long umemberId);

//    SnsTopic topicDetail(Long topicId);

//    boolean removeTopic(SnsTopicVo vo);

//    List<SnsTopicVo> newTopicList(SearchNewsVo vo);
    
//    Integer topicCountByContentId(Long contentId);
    
//    List<Map<String,Object>> cmsTopics(Integer num,Long memberId);
    

//    List<CmsContentVo> nearContents(CmsContentVo vo);


	


}
