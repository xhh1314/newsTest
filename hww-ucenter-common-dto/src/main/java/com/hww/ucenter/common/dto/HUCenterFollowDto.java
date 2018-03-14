package com.hww.ucenter.common.dto;

import com.hww.base.common.dto.AbsBaseDto;

public class HUCenterFollowDto extends  AbsBaseDto<Long> {

    private Long followId;
    private Long memberId;
    private Long tomemberId;
    /**
     * 关注类型，1关注对方 2互相关注
     */
    private Integer concernType; 
    public Long getFollowId() {
        return followId;
    }

    public void setFollowId(Long followId) {
        this.followId = followId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getId() { 
        return followId;
    }

    public Integer getConcernType() {
        return concernType;
    }

    public void setConcernType(Integer concernType) {
        this.concernType = concernType;
    }

    public Long getTomemberId() {
        return tomemberId;
    }

    public void setTomemberId(Long tomemberId) {
        this.tomemberId = tomemberId;
    }
    
    
}
