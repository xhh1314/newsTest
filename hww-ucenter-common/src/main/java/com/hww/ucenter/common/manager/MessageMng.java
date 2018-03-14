package com.hww.ucenter.common.manager;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.ucenter.common.dao.MessageDao;
import com.hww.ucenter.common.dto.UserMessageQueryDto;
import com.hww.ucenter.common.entity.UCenterMessage;
import java.util.List;

public interface MessageMng extends IBaseEntityMng<Long, UCenterMessage, MessageDao> {

    Integer loadUserMessageCount(UserMessageQueryDto  dto);
    
    List<UCenterMessage> loadUserMessages(UserMessageQueryDto  dto);
    
    
    void setUserMessagesIsRead(Long  msgid,String isRead);
    
    void setUserMessagesStatus(Long  msgid,Short status);

	void setUserMessagesIsRead(List<Long> messageIds, String isRead);


	void deleteBySubjectId(Long subjectId, Short status);

	void deleteByResourceId(Long resourceId, Short status);

	void deleteByCommonId(Long commonId, Short status);
}
