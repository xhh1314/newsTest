/*package com.hww.sns.webadmin.service;

import java.util.List;

import com.hww.base.common.page.Pagination;
import com.hww.sns.common.vo.SnsCategoryVo;

public interface SnsCategoryService{

	//查询所有分类
	Pagination list(SnsCategoryVo searchVo, Integer pageNo, Integer pageSize);
	//加载分类树
	List<SnsCategoryVo> listSnsCategorys(SnsCategoryVo vo);
	
	SnsCategoryVo findById(Long categoryId);
	
	void saveCategory(SnsCategoryVo snsPostVo);
	
	void deleteCategory(SnsCategoryVo snsPostVo);

	void batchDeleteCategory(List<SnsCategoryVo> vos);
	
	//分类转json
	String getCategoryTreeJson(List<SnsCategoryVo> vos);
}
*/