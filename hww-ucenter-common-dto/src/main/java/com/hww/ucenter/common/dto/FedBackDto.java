package com.hww.ucenter.common.dto;

import com.hww.base.common.dto.AbsBaseDto;

public class FedBackDto extends AbsBaseDto<Integer>{

    private Integer fbId;
    private String phone;
    private String content;
    private String image;
    private String video;
    private Integer memberId;
    
    public Integer getFbId() {
        return fbId;
    }
    public void setFbId(Integer fbId) {
        this.fbId = fbId;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getVideo() {
        return video;
    }
    public void setVideo(String video) {
        this.video = video;
    }
    public Integer getMemberId() {
        return memberId;
    }
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
    
    
}
