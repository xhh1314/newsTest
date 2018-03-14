package com.hww.sns.common.dto;


import java.io.Serializable;


public class HSnsTopicUpParamDto  implements Serializable

{
	    private Long topicId;
	    private String imei;
	    private Long memberId;// 用户id
	    private Double longitude;// 经度
	    private Double latitude;// 纬度
	    
	    public HSnsTopicUpParamDto() {
			super();
		}
	    
		public HSnsTopicUpParamDto(Long topicId, Long memberId, Double longitude, Double latitude) {
			super();
			this.topicId = topicId;
			this.memberId = memberId;
			this.longitude = longitude;
			this.latitude = latitude;
		}
		public Long getTopicId() {
			return topicId;
		}
		public void setTopicId(Long topicId) {
			this.topicId = topicId;
		}
		public Long getMemberId() {
			return memberId;
		}
		public void setMemberId(Long memberId) {
			this.memberId = memberId;
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

		public String getImei() {
			return imei;
		}

		public void setImei(String imei) {
			this.imei = imei;
		}
	   
	
}
