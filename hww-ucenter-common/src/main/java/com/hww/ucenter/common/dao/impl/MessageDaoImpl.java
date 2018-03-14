package com.hww.ucenter.common.dao.impl;

import com.hww.base.common.util.Finder;
import com.hww.ucenter.common.dao.MessageDao;
import com.hww.ucenter.common.dto.MyConcernDto;
import com.hww.ucenter.common.dto.UserMessageDto;
import com.hww.ucenter.common.dto.UserMessageQueryDto;
import com.hww.ucenter.common.entity.UCenterMessage;
import com.hww.ucenter.common.vo.MessageVo;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
@Repository("messageDao")
public class MessageDaoImpl extends LocalEntityDaoImpl<Long, UCenterMessage> implements MessageDao {

    @Override
    public Integer loadUserMessageCount(UserMessageQueryDto  dto) {
        
        Finder f = Finder.create("SELECT count(1) as cu");
        f.append(" FROM ucenter_message");
        f.append(" where member_id = :memberId");
        f.append(" and status=1 ");
        f.setParam("memberId", dto.getMemberId());
        if(StringUtils.hasText(dto.getIsRead())) {
        	 f.append(" and is_read =:isRead ");
        	 f.setParam("isRead", dto.getIsRead());
        }
        List<Map<String, Object>> list = (List<Map<String, Object>>) findJoin(f, Map.class);
        return Integer.parseInt(list.get(0).get("cu").toString());
    }

//    @Override
//    public List<MessageVo> loadUserMessages(UserMessageQueryDto dto) {
//        
//        Finder f = Finder.create("select send_member_id as sendMemberId,message_id as messageId,resource_id as resourceId,common_id as commonId,type,is_read as isRead,"
//        		+ "bev_type as bevType,target_type as targetType ,news_id as newsId,topic_id topicId , "
//        		+ " create_time as createTime,member_id as memberId,content,longitude,latitude,address "
//        		+ " from ucenter_message bean");
//        f.append(" where bean.member_id = :memberId");
//        if(StringUtils.hasText(dto.getIsRead())) {
//        	f.append(" and bean.is_read =:isRead ");
//       	 	f.setParam("isRead", dto.getIsRead());
//       }
//        
//        f.append(" order by bean.create_time desc");
//        f.append(" limit :pageStart,:pageSize");
//        f.setParam("memberId", dto.getMemberId());
//        f.setParam("pageStart", (dto.getPageNo()-1)*dto.getPageSize());
//        f.setParam("pageSize", dto.getPageSize());
//        return (List<MessageVo>) findJoin(f, MessageVo.class);
//    }
    
    @Override
    public List<UCenterMessage> loadUCenterMessageList(UserMessageQueryDto dto) {
        

		Finder f = Finder.create("from UCenterMessage bean where 1=1");
		f.append(" and bean.memberId = :memberId ");
		f.setParam("memberId", dto.getMemberId());
		
		if(StringUtils.hasText(dto.getIsRead())) {
        	f.append(" and bean.isRead =:isRead ");
       	 	f.setParam("isRead", dto.getIsRead());
       }
		f.append("  and status=1  ");
		f.append("  order by bean.createTime desc ");
		if(dto.getPageNo()!=null&&dto.getPageSize()!=null) {
			f.setMaxResults(dto.getPageSize());
			f.setFirstResult((dto.getPageNo()-1)*dto.getPageSize());
		}
        return (List<UCenterMessage>) find(f);
    }

}
