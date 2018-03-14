package com.hww.ucenter.webservice.service.impl;

import com.google.common.collect.Lists;
import com.hww.base.util.BeanUtils;
import com.hww.base.util.TimeUtils;
import com.hww.cms.common.vo.CmsContentDataVo;
import com.hww.sns.api.SnsFeignClient;
import com.hww.sns.common.vo.SnsPostVo;
import com.hww.sns.common.vo.SnsTopicVo;
import com.hww.ucenter.common.dto.UserMessageDto;
import com.hww.ucenter.common.dto.UserMessageQueryDto;
import com.hww.ucenter.common.entity.UCenterMember;
import com.hww.ucenter.common.entity.UCenterMessage;
import com.hww.ucenter.common.manager.MessageMng;
import com.hww.ucenter.common.manager.UCenterMemberMng;
import com.hww.ucenter.webservice.data.HMessageDetailData;
import com.hww.ucenter.webservice.service.CacheProxyService;
import com.hww.ucenter.webservice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

	@Autowired CacheProxyService cacheProxyService;
    @Autowired
    MessageMng messageMng;

    @Autowired
    SnsFeignClient snsFeignClient;


    @Autowired
    UCenterMemberMng UCenterMemberMng;

    @Override
    public Integer loadUserMessageCount(UserMessageQueryDto  dto) {

        return messageMng.loadUserMessageCount(dto);
    }

    @Override
    public List<HMessageDetailData> userMessages(UserMessageQueryDto  dto) {

        List<UCenterMessage> messages = messageMng.loadUserMessages(dto);
        if(messages==null||messages.isEmpty()) {
        	return Lists.newArrayList();
        }
        List<HMessageDetailData> msgList=Lists.newArrayList();
        List<Long> msgIds=Lists.newArrayList();
        List<Long> senderIds = messages.stream().map(val->val.getSendMemberId()).collect(Collectors.toList());
        
        List<UCenterMember> senderList= UCenterMemberMng.loadUCenterMemberByIds(senderIds);
        
        for(UCenterMessage msg : messages) {
        	msgIds.add(msg.getMessageId());
        	HMessageDetailData data=new HMessageDetailData();
        	
        	Long subjectContentId=msg.getSubjectContentId();
        	Long currentUserId=dto.getMemberId();
        	
        	Long senderId=msg.getSendMemberId();
        	
        	Integer bevType=msg.getBevType().intValue();//操作类型 0 查看, 1点赞 ，2 收藏，3评论
        	Integer targetType= msg.getTargetType().intValue();////针对目标类型  0新鲜事，1爆料 ，2评论，3新闻 （不会出现）
        	Integer subjectType=msg.getSubjectType().intValue();//消息针对的主体类型： 0新鲜事，1爆料 ，2 新闻
        	
        	data.setMessageId(msg.getMessageId());
        	data.setBevType(bevType);
        	data.setTargetType(targetType);
        	data.setSubjectType(subjectType);
        	data.setIsRead(msg.getIsRead());
        	
        	UCenterMember memberMsgSender2=null;
        	try {
        		UCenterMember memberMsgSender= senderList.stream().filter(val->val.getMemberId().equals(senderId)).collect(Collectors.toList()).get(0);
        		memberMsgSender2=new UCenterMember();
				BeanUtils.copyProperties(memberMsgSender2, memberMsgSender);
				memberMsgSender2.setPassword("");
			} catch (Exception e) {
				e.printStackTrace();
			}
        	data.setMemberMsgSender(memberMsgSender2);
        	
        	if(3==bevType.intValue()) {
        		//对某人的新鲜事进行了评论
        		if(0==targetType.intValue()||1==targetType.intValue()) {//0新鲜事，1爆料
        			
//        			此时==》   subjectContentId==msg.getResourceId()
        			SnsTopicVo subjectSnsTopic = cacheProxyService.loadSnsTopicVoCacheFirst(subjectContentId);
        			if(subjectSnsTopic==null||subjectSnsTopic.getShowStatus()!=1) {
        				messageMng.setUserMessagesStatus(msg.getId(), Short.valueOf("0"));
        				continue;
        			}
        			data.setSubjectSnsTopic(subjectSnsTopic);
        			//新鲜事没被删除 加载评论数据
        			if(subjectSnsTopic!=null&&1==subjectSnsTopic.getShowStatus()) {//
        				SnsPostVo snsPostFromSender=cacheProxyService.loadSnsPostVoCacheFirst(msg.getCommonId());//
            			data.setSnsPostFromSender(snsPostFromSender);
            			
            			//评论被删除，删除消息
            			if(snsPostFromSender==null||snsPostFromSender.getShowStatus()!=1) {
            				messageMng.setUserMessagesStatus(msg.getId(), Short.valueOf("0"));
            				continue;
            			}
            			
        			}
        		}else if(2==targetType.intValue()){//评论的目标是个评论
        			SnsPostVo snsPostOrigin=cacheProxyService.loadSnsPostVoCacheFirst(msg.getResourceId());//
    				SnsPostVo snsPostFromSender=cacheProxyService.loadSnsPostVoCacheFirst(msg.getCommonId());//
    				data.setSnsPostFromSender(snsPostFromSender);
    				data.setSnsPostOrigin(snsPostOrigin);
    				
    				//评论被删除，删除消息
    				if(snsPostOrigin==null||(snsPostOrigin.getShowStatus()!=null&&snsPostOrigin.getShowStatus()!=1)) {
        				messageMng.setUserMessagesStatus(msg.getId(), Short.valueOf("0"));
        				continue;
        			}
    				//评论被删除，删除消息
    				if(snsPostFromSender==null||(snsPostFromSender.getShowStatus()!=null&&snsPostFromSender.getShowStatus()!=1)) {
        				messageMng.setUserMessagesStatus(msg.getId(), Short.valueOf("0"));
        				continue;
        			}
    				
    				//加载主体：新鲜事、爆料
        			if(0==subjectType.intValue()||1==subjectType.intValue()) {//主体是个新鲜事
        				SnsTopicVo subjectSnsTopic=cacheProxyService.loadSnsTopicVoCacheFirst(subjectContentId);
        				data.setSubjectSnsTopic(subjectSnsTopic);
        				//加载主体：新闻
        			}else if(2==subjectType.intValue()){//评论的主体是个新闻
        				CmsContentDataVo cmsContentDataVo=cacheProxyService.loadCmsContentDataVoCacheFirst(subjectContentId,currentUserId);
        				data.setSubjectCmsContent(cmsContentDataVo);
        			}
        		}
        	}else if(1==bevType.intValue()) {//如果是点赞
        		//对某人的新鲜事进行了点赞
        		if(0==targetType.intValue()||1==targetType.intValue()) {//0新鲜事，1爆料
        			
//        			此时==》   subjectContentId==msg.getResourceId()
        			SnsTopicVo subjectSnsTopic = cacheProxyService.loadSnsTopicVoCacheFirst(subjectContentId);
        			data.setSubjectSnsTopic(subjectSnsTopic);
        		}else if(2==targetType.intValue()){//点赞的目标是个评论
        			SnsPostVo snsPostOrigin=cacheProxyService.loadSnsPostVoCacheFirst(msg.getResourceId());//
    				data.setSnsPostOrigin(snsPostOrigin);
    				
    				//加载主体：新鲜事、爆料
        			if(0==subjectType.intValue()||1==subjectType.intValue()) {//主体是个新鲜事
        				SnsTopicVo subjectSnsTopic=cacheProxyService.loadSnsTopicVoCacheFirst(subjectContentId);
        				data.setSubjectSnsTopic(subjectSnsTopic);
        				//加载主体：新闻
        			}else if(2==subjectType.intValue()){//评论的主体是个新闻
        				CmsContentDataVo cmsContentDataVo=cacheProxyService.loadCmsContentDataVoCacheFirst(subjectContentId,currentUserId);
        				data.setSubjectCmsContent(cmsContentDataVo);
        			}
        		}
        	}
        	
        	msgList.add(data);
        }
        try {
        	setIsRead(  msgIds);
        }catch (Exception e) {
		}
        return msgList;

    }
   
     
    
    @Async
    public void setIsRead( List<Long> msgIds) {
    	if(msgIds!=null&&!msgIds.isEmpty()) {
    		messageMng.setUserMessagesIsRead(msgIds, "y");
    	}
    }
    @Override
    public UserMessageDto messageSave(UserMessageDto userMessageDto) {
//        Long setId = UCenterMessage.getId();
//        if (setId == null) {
//            SnowFlake flake = new SnowFlake(1, 1);
//            setId = flake.nextId();
//            UCenterMessage.setMessageId(setId);
//        }
        UCenterMessage msg=new UCenterMessage();
    	try {
			BeanUtils.copyProperties(msg, userMessageDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	msg.setStatus(Short.valueOf("1"));
    	msg.setIsRead("n");
    	msg.setCreateTime(TimeUtils.getDateToTimestamp());
    	msg.setLastModifyTime(TimeUtils.getDateToTimestamp());
    	messageMng.save(msg);
    	
    	userMessageDto.setMessageId(msg.getMemberId());
    	
        return userMessageDto;
    }

	@Override
	public void setUserMessagesIsRead(Long msgid, String isRead) {
		if(msgid!=null&&StringUtils.hasText(isRead)) {
			messageMng.setUserMessagesIsRead(msgid, isRead);
		}
		
	}

	@Override
	public void setUserMessagesStatus(Long msgid, Short status) {
		if(msgid!=null&&status!=null) {
			messageMng.setUserMessagesStatus(msgid, status);
		}
	}
	@Override
	public void deleteBySubjectId(Long subjectId, Short status) {
		if(subjectId!=null&&status!=null) {
			messageMng.deleteBySubjectId(subjectId, status);
		}
	}
	@Override
	public void deleteByResourceId(Long resourceId, Short status) {
		if(resourceId!=null&&status!=null) {
			messageMng.deleteBySubjectId(resourceId, status);
		}
	}
	@Override
	public void deleteByCommonId(Long commonId, Short status) {
		if(commonId!=null&&status!=null) {
			messageMng.deleteBySubjectId(commonId, status);
		}
	}
    
//  // 处理内容(是动态还是评论)
//  for (MessageVo vo : messages) {
//      try {
//          //===============回复人和回复内容============================
//          UCenterMember sendMember = UCenterMemberMng.find(vo.getSendMemberId());
//          SnsPostVo comment = snsFeignClient.postDetail(vo.getCommonId());//别人的动作
//
//          vo.setContent(comment.getContent());
//          vo.setSex(sendMember.getSex());
//          vo.setNickName(sendMember.getNickName());
//          vo.setAvatar(sendMember.getAvatar());
//
//          //===============================原帖======================
//          UCenterMember UCenterMember = UCenterMemberMng.find(dto.getMemberId());
//          if (vo.getType() == 2) {
//              // 评论(点赞/评论)
//              SnsPostVo sp = snsFeignClient.postDetail(vo.getResourceId());
//              MessageVo resource = new MessageVo();
//              resource.setContent(sp.getContent());//自己发的内容
//              resource.setNickName(UCenterMember.getNickName());//自己的昵称
//              resource.setAvatar(UCenterMember.getAvatar());//自己的头像
//              resource.setResourceId(vo.getResourceId());//自己的帖子id
//              resource.setType(vo.getType());
//              resource.setMemberId(vo.getMemberId());//自己的id
//              vo.setOriginal(resource);
//              vo.setUpNum(resource.getUpNum());//点赞数
//              vo.setCommentNum(resource.getCommentNum());//评论数
//          } else if (vo.getType() == 1) {
//              // 新鲜事
//              SnsTopicVo snsTopic = snsFeignClient.topicDetail(vo.getResourceId());
//              MessageVo resource = new MessageVo();
//              resource.setContent(snsTopic.getContent());
//              resource.setNickName(UCenterMember.getNickName());
//              resource.setAvatar(UCenterMember.getAvatar());
//              resource.setType(snsTopic.getTopicType());
//              resource.setMemberId(snsTopic.getMemberId());
//              if (snsTopic.getFileId() != null) {
//                  String url = fileFeignClient.getUrlByFileId(snsTopic.getFileId());
//                  resource.setUrl(url);
//              }
//              vo.setOriginal(resource);
//              vo.setUpNum(resource.getUpNum());
//              vo.setCommentNum(resource.getCommentNum());
//          }
//      } catch (Exception e) {
//          e.printStackTrace();
//          vo.setContent("此内容不存在,可能已被删除");
//          vo.setStatus((short) 0);
//      }
//      UCenterMessage UCenterMessage = messageMng.find(vo.getMessageId());
//      UCenterMessage.setIsRead("y");
//      messageMng.update(UCenterMessage);
//  }
//  return messages;
}
