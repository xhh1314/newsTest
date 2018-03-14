package com.hww.sns.common.dto;


import java.io.Serializable;

import com.hww.base.common.dto.AbsBaseDto;



public class HSnsPostUpParamDto  implements Serializable 

{
	    private Long postId;
	    private String imei;
	    private Long memberId;// 用户id
	    private Double longitude;// 经度
	    private Double latitude;// 纬度
	    
	    public HSnsPostUpParamDto() {
			super();
		}
	    
		public HSnsPostUpParamDto(Long postId, Long memberId, Double longitude, Double latitude) {
			super();
			this.postId = postId;
			this.memberId = memberId;
			this.longitude = longitude;
			this.latitude = latitude;
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

		public Long getPostId() {
			return postId;
		}

		public void setPostId(Long postId) {
			this.postId = postId;
		}
	   
	
}
