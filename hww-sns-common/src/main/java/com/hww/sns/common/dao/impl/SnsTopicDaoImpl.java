package com.hww.sns.common.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.hww.base.common.util.Finder;
import com.hww.sns.common.dao.SnsTopicDao;
import com.hww.sns.common.dto.HBaseSnsQueryDto;
import com.hww.sns.common.entity.SnsTopic;

@Service("snsTopicDao")
public class SnsTopicDaoImpl extends LocalDataBaseDaoImpl<Long, SnsTopic> implements SnsTopicDao {


    @Override
    public List<SnsTopic> listTopicByAuthorMemberId(HBaseSnsQueryDto snsQueryDto) {
        if(snsQueryDto==null||snsQueryDto.getAuthorMemberId()==null) {
            return Lists.newArrayList();
        }
        if(snsQueryDto.getAuthorMemberId()==null) {
            return Lists.newArrayList();
        }
        Finder f = Finder.create("from SnsTopic bean");
        f.append(" where bean.memberId=:memberId");
        f.append(" and bean.forumId=0 ");//排除评论forumId=2的数据
        f.append(" and status = 1 ");
        f.append(" and (bean.auditStatus=1 or bean.auditStatus=2) ");//审核通过的
        //不是当前用户不能看匿名，不公开，审核未通过的新鲜事
        if(!snsQueryDto.getAuthorMemberId().equals(snsQueryDto.getMemberId())) {
            f.append(" and bean.anonymous=2 ");//不匿名发的
            f.append(" and  bean.publi=1 ");//公开的
            f.append(" and bean.showStatus=1 ");
        }
        //爆料标签topicType=1
        if(snsQueryDto.getTopicType()!=null) {
            f.append(" and bean.topicType=:topicType ");
            f.setParam("topicType", snsQueryDto.getTopicType());
        }

        f.append(" order by bean.createTime desc");
        f.setParam("memberId", snsQueryDto.getAuthorMemberId());

        if(snsQueryDto.getPageNo()!=null&&snsQueryDto.getPageSize()!=null) {
            f.setFirstResult((snsQueryDto.getPageNo()-1)*snsQueryDto.getPageSize());
            f.setMaxResults(snsQueryDto.getPageSize());
        }
        List<SnsTopic> snsTopics = (List<SnsTopic>) find(f);
        return  snsTopics;
    }

    @Override
    public List<Long> listTopicIdByMemberId(HBaseSnsQueryDto snsQueryDto) {
        Finder finder=Finder.create("select topicId from SnsTopic where memberId= :memberId and status=1 and showStatus=1 order by createTime desc");
        finder.setParam("memberId",snsQueryDto.getMemberId());
        if(snsQueryDto.getPageNo()!=null && snsQueryDto.getPageSize()!=null){
            finder.setFirstResult((snsQueryDto.getPageNo()-1)*snsQueryDto.getPageSize());
            finder.setMaxResults(snsQueryDto.getPageSize());
        }
        return (List<Long>)find(finder);
    }

    @Override
    public List<SnsTopic> listSnsTopicByIds(List<Long> topicIds) {
        String strIds=topicIds.stream().map(val->val.toString()).collect(Collectors.joining(","));
        Finder finder=Finder.create("from SnsTopic where status=1 and topicId in (:ids)");
        finder.setParam("ids",strIds);
        return (List<SnsTopic>) find(finder);
    }
    @Override
    public List<SnsTopic> loadConcernUserTopics(HBaseSnsQueryDto snsQueryDto, List<Long> concernUserIds) {
        Finder f = Finder.create("from SnsTopic bean");
        f.append(" where bean.memberId in (:concernUserIds)");
        f.append(" and bean.showStatus = 1");
        f.append(" and bean.forumId=0 ");
        f.append(" and status = 1 ");
        f.append(" and bean.anonymous=2 "); // 不是当前用户不能看匿名 是否匿名1匿名2不匿名
        f.append(" and bean.publi=1 ");// 公开的

        if (snsQueryDto.getTopicType() != null) {
            f.append(" and bean.topicType=:topicType ");
            f.setParam("topicType", snsQueryDto.getTopicType());
        }

        f.append(" order by bean.createTime desc");
        f.setParamList("concernUserIds", concernUserIds);

        if (snsQueryDto.getPageNo() != null && snsQueryDto.getPageSize() != null) {
            f.setFirstResult((snsQueryDto.getPageNo() - 1) * snsQueryDto.getPageSize());
            f.setMaxResults(snsQueryDto.getPageSize());
        }
        List<SnsTopic> list = (List<SnsTopic>) find(f);
        return list == null ? Lists.newArrayList() : list;
    }

    @Override
    public List<SnsTopic> listTopicByMemeberIds(List<Long> snsTopicId) {
        Finder finder=Finder.create("select * from sns_topic a inner join ");
        finder.append("(select s.topic_id from sns_topic s where member_id in (:memberIds) and status=1 and show_status=1) temp ");
        finder.append("  on a.topic_id=temp.topic_id");
        finder.setParamList("memberIds",snsTopicId);
        return (List<SnsTopic>) findJoin(finder,SnsTopic.class);
    }
}
