package com.hww.ucenter.common.dao;

import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.ucenter.common.dto.UserMessageQueryDto;
import com.hww.ucenter.common.entity.UCenterMessage;
import com.hww.ucenter.common.vo.MessageVo;

import java.util.List;

public interface MessageDao extends IBaseEntityDao<Long, UCenterMessage> {

    Integer loadUserMessageCount(UserMessageQueryDto  dto);
    
//    List<MessageVo> loadUserMessages(UserMessageQueryDto  dto);

	List<UCenterMessage> loadUCenterMessageList(UserMessageQueryDto dto);
}
