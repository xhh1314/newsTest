package com.hww.sns.common.dao;

import java.util.List;
import java.util.Map;
import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.sns.common.dto.HBaseSnsQueryDto;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.vo.ConcernTopicVo;

public interface SnsTopicDao
	extends
		IBaseEntityDao<Long, SnsTopic>
{
    List<SnsTopic> listTopicByAuthorMemberId(HBaseSnsQueryDto snsQueryDto);

    /**
     * 根据memberId查询topic，按照时间倒序排列
     * @param snsQueryDto
     * @return
     */
    List<Long> listTopicIdByMemberId(HBaseSnsQueryDto snsQueryDto);

    List<SnsTopic> listSnsTopicByIds(List<Long> topicIds);


    List<SnsTopic> loadConcernUserTopics(HBaseSnsQueryDto snsQueryDto, List<Long> concernUserIds);

    /**
     * 返回topicId和createTime,主要是为了redis服务，此查询建立在聚族索引基础上，特意为该查询建立了一条索引
     * @param snsTopicIdMiss
     * @return
     */
    List<SnsTopic> listTopicIdAndCreateTimeByMemeberId(List<Long> snsTopicIdMiss);

//    public List<ConcernTopicVo> concernTopics(ConcernTopicVo concernTopicVo,String concerns);
    
//     List<SnsTopic> selectConcernUserTopics(HBaseSnsQueryDto snsQueryDto,List<Long> concernUserIds);
    
//     Integer selctUserTopicCount(Long memberId,Integer topicType);
    
//    public List<SnsTopic> loadNearTopics(HBaseSnsQueryDto snsQueryDto);
    
//    public List<NearTopicVo> nearPeoples(NearTopicVo vo);
//    /**
//     * 根据新闻id获取topicid，主要用于查询新闻评论
//     * @param newsId
//     * @return
//     */
//    Long  loadTopicIdByNewsId(Long newsId);

	
    
//    List<Map<String,Object>> cmsTopics(Integer num);
    
//    SnsTopic upTopic(SnsTopic snsTopic);
}
