package com.hww.sns.common.util;

import com.hww.els.common.entity.ESContent;
import com.hww.sns.common.entity.SnsTopic;

public class EsContentCovertUtil {
	
	public static ESContent toESContent(SnsTopic snsTopic) {
		ESContent esContent=new ESContent();
		esContent.setId(snsTopic.getTopicId());
		esContent.setPlateType(1);

		if(snsTopic.getTitle()!=null) {
			esContent.setTitle(snsTopic.getTitle());
		}
		if(snsTopic.getContent()!=null) {
			esContent.setContent(snsTopic.getContent());
		}
		if(snsTopic.getRelatednewsId()!=null) {
			esContent.setRelatednewsId(snsTopic.getRelatednewsId());
		}
		if(snsTopic.getLatitude()!=null) {
			esContent.setLatitude(snsTopic.getLatitude());
		}
		if(snsTopic.getLongitude()!=null) {
			esContent.setLongitude(snsTopic.getLongitude());
		}
		if(snsTopic.getAddress()!=null) {
			esContent.setAddress(snsTopic.getAddress());
		}
		if(snsTopic.getCreateTime()!=null) {
			//esContent.setCreateTime(snsTopic.getCreateTime());
			esContent.setCreateTimeLong(snsTopic.getCreateTime().getTime());
			esContent.setReleaseTimeLong(snsTopic.getCreateTime().getTime());

		}
				
		if(snsTopic.getAnonymous()!=null) {
			esContent.setAnonymous(snsTopic.getAnonymous());
		}
		
		if(snsTopic.getPubli()!=null) {
			esContent.setPubli(snsTopic.getPubli());
		}
		
		if(snsTopic.getMemberId()!=null) {
			esContent.setMemberId(snsTopic.getMemberId());
		}
		
		if(snsTopic.getTopicType()!=null) {
			esContent.setTopicType(snsTopic.getTopicType());
		}
		
		if(snsTopic.getShowStatus()!=null) {
			esContent.setShowStatus(snsTopic.getShowStatus());
		}
		
		if(snsTopic.getStatus()!=null) {
			esContent.setStatus(snsTopic.getStatus().intValue());
		}
		if(snsTopic.getAuditStatus()!=null){
			esContent.setAuditStatus(snsTopic.getAuditStatus());
		}
		return esContent;
		
	}

}
