package com.hww.cms.common.util;

import com.hww.cms.common.vo.CmsContentVo;
import com.hww.els.common.entity.ESContent;

public class EsContentCovertUtil {
	
	public static ESContent toEsContent(CmsContentVo cmsContentVo) {
		ESContent esContent=new ESContent();
		esContent.setId(cmsContentVo.getContentId());
		esContent.setPlateType(0);
		if(cmsContentVo.getTitle()!=null) {
			esContent.setTitle(cmsContentVo.getTitle());
		}
		if(cmsContentVo.getShortTitle()!=null) {
			esContent.setShortTitle(cmsContentVo.getShortTitle());
		}
		if(cmsContentVo.getSummary()!=null) {
			esContent.setSummary(cmsContentVo.getSummary());
		}
		if(cmsContentVo.getKeywordIds()!=null) {
			esContent.setKeywords(cmsContentVo.getKeywordIds());
		}
		if(cmsContentVo.getContent()!=null){
			esContent.setContent(cmsContentVo.getContent());
		}
		if(cmsContentVo.getAuditStatus()!=null) {
			esContent.setAuditStatus(cmsContentVo.getAuditStatus());
		}
		if(cmsContentVo.getLatitude()!=null) {
			esContent.setLatitude(cmsContentVo.getLatitude());
		}
		if(cmsContentVo.getLongitude()!=null) {
			esContent.setLongitude(cmsContentVo.getLongitude());
		}
		if(cmsContentVo.getLocation()!=null) {
			esContent.setAddress(cmsContentVo.getLocation());
		}
		if(cmsContentVo.getTop()!=null) {
			esContent.setTop(cmsContentVo.getTop().intValue());
		}
		if(cmsContentVo.getCreateTime()!=null) {
			esContent.setCreateTimeLong(cmsContentVo.getCreateTime().getTime());
		}
		if(cmsContentVo.getReleaseTime()!=null) {
			esContent.setReleaseTimeLong(cmsContentVo.getReleaseTime().getTime());
		}
		if(cmsContentVo.getContenttypeId()!=null) {
			esContent.setContentType(cmsContentVo.getContenttypeId().intValue());
		}
		if(cmsContentVo.getStatus()!=null){
			esContent.setStatus(cmsContentVo.getStatus().intValue());
		}
		return esContent;
		
	}
}
