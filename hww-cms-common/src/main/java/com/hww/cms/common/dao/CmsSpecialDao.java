package com.hww.cms.common.dao;

import java.util.List;

import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.cms.common.entity.CmsSpecial;
import com.hww.cms.common.vo.CmsSpecialVo;


public interface CmsSpecialDao extends IBaseEntityDao<Long, CmsSpecial> {
//	 List<CmsSpecial> findList(CmsSpecialVo vo);

	List<CmsSpecial> getSpecialByJson(Long parentId);


//	/**
//	 * @author XiaoBG
//	 * 多条件查询专题实体类信息
//	 * @param: CmsSpecial
//	 * @return: List<CmsSpecial>
//	 * @date:18-1-12
//	 */
//	List<CmsSpecial> getCmsSpecialByMultiCondition(CmsSpecial cmsSpecial);

//	List<CmsSpecial> selectAllSpecialList(Short status);
	
	List<CmsSpecial> selectByParentId(Long parentId);
}
