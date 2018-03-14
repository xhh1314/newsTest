package com.hww.cms.common.manager;

import java.util.List;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.cms.common.dao.CmsSpecialDao;
import com.hww.cms.common.entity.CmsSpecial;
import com.hww.cms.common.vo.CmsSpecialVo;

public interface CmsSpecialMng extends IBaseEntityMng<Long, CmsSpecial, CmsSpecialDao> {
	
//	 List<CmsSpecial> loadAllSpecialList(Short status);
	
		List<CmsSpecial> loadByParentId(Long parentId);
		
		
		
//	List<CmsSpecial> findList(CmsSpecialVo vo);
	
	List<CmsSpecial> getSpecialByJson(Long parentId);
	
//	/**
//	 * @author XiaoBG
//	 * 多条件查询专题实体类信息
//	 * @param: CmsSpecial
//	 * @return: List<CmsSpecial>
//	 * @date:18-1-12
//	 */
//	List<CmsSpecial> getCmsSpecialByMultiCondition(CmsSpecial cmsSpecial);	
	
	/**
	 * 更改special状态 0：禁用 ，1：使用
	 * @param specialId id
	 * @param status 状态
	 * @return
	 */
	boolean updateSpecialStatus(Long specialId,Short status);

	List<CmsSpecial> loadFirstLeveSpecialList(Short status);

	List<CmsSpecial> loadNotTopLeveSpecialList(Short status);



	
}
