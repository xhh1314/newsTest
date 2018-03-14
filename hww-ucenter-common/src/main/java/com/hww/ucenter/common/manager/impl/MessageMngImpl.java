package com.hww.ucenter.common.manager.impl;

import com.google.common.collect.Lists;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.util.Finder;
import com.hww.ucenter.common.dao.MessageDao;
import com.hww.ucenter.common.dto.MyConcernDto;
import com.hww.ucenter.common.dto.UserMessageDto;
import com.hww.ucenter.common.dto.UserMessageQueryDto;
import com.hww.ucenter.common.entity.UCenterMessage;
import com.hww.ucenter.common.manager.MessageMng;
import com.hww.ucenter.common.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageMngImpl extends BaseEntityMngImpl<Long, UCenterMessage, MessageDao>
        implements MessageMng {

    private MessageDao messageDao;

    public MessageDao getMessageDao() {
        return messageDao;
    }

    @Autowired
    public void setMessageDao(MessageDao messageDao) {
        super.setEntityDao(messageDao);
        this.messageDao = messageDao;
    }

    @Override
    public Integer loadUserMessageCount(UserMessageQueryDto  dto) {

        return messageDao.loadUserMessageCount(dto);
    }

    @Override
    public List<UCenterMessage> loadUserMessages(UserMessageQueryDto  dto) {

    	List<UCenterMessage> list= messageDao.loadUCenterMessageList(dto);
    	
        return list==null?Lists.newArrayList():list;
    }

	@Override
	public void setUserMessagesIsRead(Long messageId, String isRead) {
	    Finder f = Finder.create(" update UCenterMessage s ");
        f.append(" set");
        f.append(" s.isRead = :isRead ");
        f.append(" where s.messageId = :messageId ");

        f.setParam("isRead", isRead);
        f.setParam("messageId", messageId);
        messageDao.update(f);
		
	}
	@Override
	public void setUserMessagesIsRead(List<Long> messageIds, String isRead) {
	    Finder f = Finder.create(" update UCenterMessage s ");
        f.append(" set");
        f.append(" s.isRead = :isRead ");
        f.append(" where s.messageId in (:messageIds) ");
        f.setParam("isRead", isRead);
        f.setParamList("messageIds", messageIds);
        messageDao.update(f);
		
	}

	@Override
	public void setUserMessagesStatus(Long messageId, Short status) {
	    Finder f = Finder.create(" update UCenterMessage s ");
        f.append(" set");
        f.append(" s.status = :status");
        f.append(" where s.messageId = :messageId ");
        f.setParam("status", status);
        f.setParam("messageId", messageId);
        messageDao.update(f);
		
	}
	
	
	@Override
	public void deleteBySubjectId(Long subjectId, Short status) {
	    Finder f = Finder.create(" update UCenterMessage s ");
        f.append(" set");
        f.append(" s.status = :status");
        f.append(" where s.subjectContentId =:subjectId ");
        f.setParam("status", status);
        f.setParam("subjectId", subjectId);
        messageDao.update(f);
	}
	
	@Override
	public void deleteByResourceId(Long resourceId, Short status) {
	    Finder f = Finder.create(" update UCenterMessage s ");
        f.append(" set");
        f.append(" s.status = :status");
        f.append(" where s.resourceId =:resourceId ");
        f.setParam("status", status);
        f.setParam("resourceId", resourceId);
        messageDao.update(f);
	}
	@Override
	public void deleteByCommonId(Long commonId, Short status) {
	    Finder f = Finder.create(" update UCenterMessage s ");
        f.append(" set");
        f.append(" s.status = :status");
        f.append(" where s.commonId =:commonId ");
        f.setParam("status", status);
        f.setParam("commonId", commonId);
        messageDao.update(f);
	}
}
