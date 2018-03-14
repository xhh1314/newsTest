package com.hww.sns.webadmin.service;

import java.util.List;

import com.hww.base.common.page.Pagination;
import com.hww.sns.common.vo.SnsSenstiveVo;

public interface SnsSenstiveService{

	//查询所有(敏感词|过滤词)
	Pagination list(SnsSenstiveVo searchVo, Integer pageNo, Integer pageSize);
	
	//保存或者更新
	void saveSenstive(SnsSenstiveVo snsSenstiveVo);

	void deleteSenstiveword(SnsSenstiveVo vo);

	void batchDeleteSenstiveword(List<SnsSenstiveVo> asList);

	SnsSenstiveVo findById(Long senstiveId);
	
	//敏感词/过滤词标记
	String parseContent(String originContent);
	
	/**
	 * 根据内容和分类查询敏感词或过滤词是否存在
	 * @param vo
	 * @return
	 */
	Integer getWordCount(SnsSenstiveVo vo);
}
