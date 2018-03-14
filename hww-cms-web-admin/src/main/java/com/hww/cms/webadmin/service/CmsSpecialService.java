package com.hww.cms.webadmin.service;

import java.util.List;

import com.hww.base.common.page.Pagination;
import com.hww.cms.common.entity.CmsSpecial;
import com.hww.cms.common.vo.CmsCategoryVo;
import com.hww.cms.common.vo.CmsSpecialVo;


public interface CmsSpecialService {

	void saveOrUpdate(CmsSpecialVo vo);
	
	void saveOrUpdateChild(CmsCategoryVo vo);
	
	Pagination listSpecial(CmsSpecialVo vo, int pageNo, int pageSize);

	CmsSpecialVo getSpecialView(Long specialId);
	
	void deleteSpecial(Long specialId);
	
	void batchDeleteSpecial(Long[] specialIds);
//	List<CmsSpecial> findList(CmsSpecialVo vo);
	List<CmsSpecial> getSpecialByJson(Long parentId);
	
	/**
	 * @author XiaoBG
	 * 管理员：添加专题信息
	 * @param：CmsSpecialVo
	 * @return null
	 * @data：18-1-12
	 */
	boolean saveSpecialType(CmsSpecialVo vo);
	
	/**
	 * @author XiaoBG
	 * 管理员：修改专题信息
	 * @param：CmsSpecialVo
	 * @return null
	 * @data：18-1-12
	 */
	boolean updateSpecialType(CmsSpecialVo vo);
	
	/**
	 * @author XiaoBG
	 * 管理员：删除专题信息
	 * @param：CmsSpecialVo
	 * @return null
	 * @data：18-1-12
	 */
	boolean deleteSpecialType(CmsSpecialVo vo);	
	
	List<CmsSpecial> getChildList(CmsSpecialVo vo);
}
