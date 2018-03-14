package com.hww.ucenter.webservice.controller;

import com.hww.ucenter.common.dto.UserMessageDto;
import com.hww.ucenter.common.dto.UserMessageQueryDto;
import com.hww.ucenter.common.util.R;
import com.hww.ucenter.webservice.data.HMessageDetailData;
import com.hww.ucenter.webservice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/ucenter/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @RequestMapping(value = "list.do", method = { RequestMethod.POST})
    @ResponseBody
    public R userMessages(UserMessageQueryDto  dto) {
        if (dto.getMemberId() == null) {
            return R.error(1, "用户不能为空");
        }
        try {
            List<HMessageDetailData> list = messageService.userMessages(dto);
            return R.ok().put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(1, "查询失败");
        }
    }

    
    
    @RequestMapping(value = "saveMsgFeginApi.do", method = {RequestMethod.POST})
    public UserMessageDto saveMsgFeginApi(@RequestBody UserMessageDto userMessageDto) {
        return messageService.messageSave(userMessageDto);
    }
    
    @RequestMapping(value = "deleteBySubjectIdFeginApi.do", method = {RequestMethod.POST})
    public R deleteBySubjectIdFeginApi(@RequestParam("subjectId")Long subjectId) {
    	messageService.deleteBySubjectId(subjectId, Short.valueOf("0"));
    	return R.ok();
    }
    
    @RequestMapping(value = "deleteByResourceIdFeginApi.do", method = {RequestMethod.POST})
    public R deleteByResourceIdFeginApi(@RequestParam("resourceId")Long resourceId) {
    	messageService.deleteByResourceId(resourceId, Short.valueOf("0"));
    	return R.ok();
    }
    
    @RequestMapping(value = "deleteByCommonIdFeginApi.do", method = {RequestMethod.POST})
    public R deleteByCommonIdFeginApi(@RequestParam("commonId")Long commonId) {
    	messageService.deleteByCommonId(commonId, Short.valueOf("0"));
    	return R.ok();
    }
    

}
