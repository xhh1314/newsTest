package com.hww.ucenter.webservice.service;

import com.hww.ucenter.common.dto.UserMessageDto;
import com.hww.ucenter.common.dto.UserMessageQueryDto;
import com.hww.ucenter.common.entity.UCenterMessage;
import com.hww.ucenter.common.vo.MessageVo;
import com.hww.ucenter.webservice.data.HMessageDetailData;

import java.util.List;

public interface MessageService {

	Integer loadUserMessageCount(UserMessageQueryDto  dto);

	List<HMessageDetailData> userMessages(UserMessageQueryDto  dto);

	UserMessageDto messageSave(UserMessageDto userMessageDto);
	
	    void setUserMessagesIsRead(Long  msgid,String isRead);
	    
	    void setUserMessagesStatus(Long  msgid,Short status);


		void deleteBySubjectId(Long subjectId, Short status);

		void deleteByResourceId(Long resourceId, Short status);

		void deleteByCommonId(Long commonId, Short status);
}
