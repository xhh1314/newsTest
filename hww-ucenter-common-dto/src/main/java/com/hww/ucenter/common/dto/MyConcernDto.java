package com.hww.ucenter.common.dto;

import javax.validation.constraints.NotNull;
import com.hww.base.common.dto.AbsBaseDto;

public class MyConcernDto extends AbsBaseDto<Integer>{

    private Integer concernId;
    @NotNull(message = "用户不能为空")
    private Long memberId;
    private Integer tomemberId;
    private Integer umemberId;
    private Double longitude;//经度
    private Double latitude;//纬度
    
    public Integer getConcernId() {
        return concernId;
    }
    public void setConcernId(Integer concernId) {
        this.concernId = concernId;
    }
    public Long getMemberId() {
        return memberId;
    }
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
    public Integer getTomemberId() {
        return tomemberId;
    }
    public void setTomemberId(Integer tomemberId) {
        this.tomemberId = tomemberId;
    }
    public Integer getUmemberId() {
        return umemberId;
    }
    public void setUmemberId(Integer umemberId) {
        this.umemberId = umemberId;
    }
    public Double getLongitude() {
      return longitude;
    }
    public void setLongitude(Double longitude) {
      this.longitude = longitude;
    }
    public Double getLatitude() {
      return latitude;
    }
    public void setLatitude(Double latitude) {
      this.latitude = latitude;
    }
    
    
}
