package com.hww.ucenter.common.dto;

import com.hww.base.common.dto.AbsBaseDto;

public class HUCenterMessageDto extends  AbsBaseDto<Long>  {

    private Double latitude;
    private Double longitude;
    private String content;
    private String address;
      
    private Long sendMemberId;
    //1新鲜事 2评论
    private Integer type;
    private Long memberId;
    
    private Long resourceId;
    private Long commonId;

    public HUCenterMessageDto(Long memberId,Long sendMemberId,
    		Long resourceId, Long commonId,String content,Integer type, 
    		Double latitude, Double longitude,  String address) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.content = content;
		this.address = address;
		this.sendMemberId = sendMemberId;
		this.type = type;
		this.memberId = memberId;
		this.resourceId = resourceId;
		this.commonId = commonId;
	}
    


	public Double getLatitude() {
        return latitude;
    }


    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }


    public Double getLongitude() {
        return longitude;
    }


    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public Long getSendMemberId() {
        return sendMemberId;
    }


    public void setSendMemberId(Long sendMemberId) {
        this.sendMemberId = sendMemberId;
    }

    public Long getCommonId() {
        return commonId;
    }


    public void setCommonId(Long commonId) {
        this.commonId = commonId;
    }

    public Integer getType() {
        return type;
    }


    public void setType(Integer type) {
        this.type = type;
    }


    public Long getMemberId() {
        return memberId;
    }


    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getResourceId() {
        return resourceId;
    }


    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

}
