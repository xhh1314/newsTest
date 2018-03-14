package com.hww.ucenter.common.vo;

import com.hww.base.common.vo.AbsBaseVo;

import javax.validation.constraints.NotNull;
public class UserMessageVo extends AbsBaseVo{

    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long messageId;
    private String content;
    private Long sendMemberId;
    private Long commonId;
    private String isRead;
    @NotNull(message="用户不能为空")
    private Long memberId;
    private Long resourceId;
    
    private Integer bevType;
    private Integer targetType;
    
    private Integer subjectType;
    
    private Integer subjectContentId;
    
    public Long getMessageId() {
        return messageId;
    }
    public UserMessageVo setMessageId(Long messageId) {
        this.messageId = messageId;
		return this;
    }
    public String getContent() {
        return content;
    }
    public UserMessageVo setContent(String content) {
        this.content = content;
		return this;
    }
    public Long getSendMemberId() {
        return sendMemberId;
    }
    public UserMessageVo setSendMemberId(Long sendMemberId) {
        this.sendMemberId = sendMemberId;
		return this;
    }
    public Long getCommonId() {
        return commonId;
    }
    public UserMessageVo setCommonId(Long commonId) {
        this.commonId = commonId;
		return this;
    }
    
    public String getIsRead() {
      return isRead;
    }
    public UserMessageVo setIsRead(String isRead) {
      this.isRead = isRead;
		return this;
    }
    public Long getMemberId() {
        return memberId;
    }
    public UserMessageVo setMemberId(Long memberId) {
        this.memberId = memberId;
		return this;
    }
    public Long getResourceId() {
        return resourceId;
    }
    public UserMessageVo setResourceId(Long resourceId) {
        this.resourceId = resourceId;
		return this;
    }

	public Integer getBevType() {
		return bevType;
	}
	public UserMessageVo setBevType(Integer bevType) {
		this.bevType = bevType;
		return this;
	}
	public Integer getTargetType() {
		return targetType;
	}
	public UserMessageVo setTargetType(Integer targetType) {
		this.targetType = targetType;
		return this;
	}
	public Integer getSubjectType() {
		return subjectType;
	}
	public UserMessageVo setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
		return this;
	}
	public Integer getSubjectContentId() {
		return subjectContentId;
	}
	public UserMessageVo setSubjectContentId(Integer subjectContentId) {
		this.subjectContentId = subjectContentId;
		return this;
	}
    
    
}
