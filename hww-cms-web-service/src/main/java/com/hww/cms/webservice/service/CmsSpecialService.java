package com.hww.cms.webservice.service;

import java.util.List;

import com.hww.cms.common.dto.HCmsQueryDto;
import com.hww.cms.common.entity.CmsSpecial;
import com.hww.cms.common.vo.HCmsSpecialListVo;
import com.hww.cms.common.vo.HCmsSpecialVo;


public interface CmsSpecialService {

	
//	List<CmsSpecial> loadAllSpecialList(Short status);
	
	CmsSpecial loadSpecialView(Long specialId);
	
	List<Long> loadCmsCateIdsBySpecialId(Long specialId);

	List<CmsSpecial> loadByParentId(Long parentId);


	HCmsSpecialVo loadCmsSpecialVoById(HCmsQueryDto queryDto);
	
//	List<HCmsSpecialListVo> loadCmsSpecialListVoByParentId(HCmsQueryDto queryDto);

	HCmsSpecialListVo loadCmsSpecialListVoById(HCmsQueryDto queryDto);

	
	List<CmsSpecial> loadFirstLeveSpecialList(Short status);

	List<CmsSpecial> loadNotTopLeveSpecialList(Short status);
	
	/**
	 * 加载专题详细信息
	 * @param speciaId
	 * @author lz
	 */
	HCmsSpecialVo loadSpecialDetailForShare(HCmsQueryDto queryDto);
	 
}
