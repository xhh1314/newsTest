package com.hww.ucenter.common.dto;

import java.io.Serializable;

public class UserMessageDto implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long messageId;
    private String content;
    private Long sendMemberId;
    private Long commonId;
    private String isRead;
    private Long memberId;
    private Long resourceId;
    private Integer bevType;
    private Integer targetType;
    
    
    private Integer subjectType;
    
    private Long subjectContentId;
    
    public static final class TargetType{
    	//0新鲜事，1爆料 ，2评论，3新闻 （不会出现）
		public static final int b0_xxs=0;
		public static final int b1_bl=1; 
		public static final int b2_pl=2; 
		public static final int b3_xw=3; 
	}
	public static final class BevType{
		//操作类型 0 查看, 1点赞 ，2 收藏，3评论
		public static final int b0_xq=0;
		public static final int b1_dz=1; 
		public static final int b2_sc=2; 
		public static final int b3_pl=3; 

	}
	
	public static final class SubjectType{
		// 0新鲜事，1爆料 ，2 新闻
		public static final int b0_xxs=0;
		public static final int b1_bl=1; 
		public static final int b2_xw=2; 

	}
    
    public Long getMessageId() {
        return messageId;
    }
    public UserMessageDto setMessageId(Long messageId) {
        this.messageId = messageId;
		return this;
    }
    public String getContent() {
        return content;
    }
    public UserMessageDto setContent(String content) {
        this.content = content;
		return this;
    }
    public Long getSendMemberId() {
        return sendMemberId;
    }
    public UserMessageDto setSendMemberId(Long sendMemberId) {
        this.sendMemberId = sendMemberId;
		return this;
    }
//    public Integer getType() {
//        return type;
//    }
//    public UserMessageDto setType(Integer type) {
//        this.type = type;
//		return this;
//    }
    
    public String getIsRead() {
      return isRead;
    }
    public UserMessageDto setIsRead(String isRead) {
      this.isRead = isRead;
		return this;
    }
    public Long getMemberId() {
        return memberId;
    }
    public UserMessageDto setMemberId(Long memberId) {
        this.memberId = memberId;
		return this;
    }
    public Long getResourceId() {
        return resourceId;
    }
    public UserMessageDto setResourceId(Long resourceId) {
        this.resourceId = resourceId;
		return this;
    }
	public Integer getBevType() {
		return bevType;
	}
	public UserMessageDto setBevType(Integer bevType) {
		this.bevType = bevType;
		return this;
	}
	public Integer getTargetType() {
		return targetType;
	}
	public UserMessageDto setTargetType(Integer targetType) {
		this.targetType = targetType;
		return this;
	}
	public Integer getSubjectType() {
		return subjectType;
	}
	public UserMessageDto setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
		return this;
	}
	public Long getSubjectContentId() {
		return subjectContentId;
	}
	public UserMessageDto setSubjectContentId(Long subjectContentId) {
		this.subjectContentId = subjectContentId;
		return this;
	}
	public Long getCommonId() {
		return commonId;
		
	}
	public UserMessageDto setCommonId(Long commonId) {
		this.commonId = commonId;
		return this;
	}
    
    
}
